package book.manager.controller.api;

import book.manager.entity.AuthUser;
import book.manager.entity.Card;
import book.manager.entity.Log;
import book.manager.mapper.CardMapper;
import book.manager.mapper.LogMapper;
import book.manager.service.CommonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("/api/common")
public class CommonApiController {
    @Resource
    CommonService commonService;
    @Resource
    CardMapper cardMapper;
    @Resource
    LogMapper logMapper;

    @RequestMapping(value = "/queryData")
    public void goQuery(@RequestParam("cid") String cid , HttpServletResponse resp) throws IOException {
        System.out.println("cid=="+cid);
        Card commonByCid = commonService.commonByCid(Integer.valueOf(cid));
        /*System.out.println(commonByCid);*/
        if (commonByCid == null){
            resp.getWriter().println("The card number is incorrect, there is no query result, please try again");//卡号有误，无查询结果，请重试！！！
        } else {
            resp.getWriter().println("The current balance : "+commonByCid.getAccount());//当前余额
        }
    }
    @RequestMapping(value = "/depositMoney")
    public String deposit(
            @RequestParam("money") Double account,
            @RequestParam("cid") String cid,
            HttpSession session,
            HttpServletResponse resp){
        AuthUser allUserList = (AuthUser) session.getAttribute("allUserList");
        Integer uid = allUserList.getUid();
        commonService.updateMoney(Integer.valueOf(cid),account,uid);
        return "redirect:/showCard";
    }
    @RequestMapping(value = "withdrawalsMoney") //取款
    public String withdrawals(@RequestParam("money") Double account,
                              @RequestParam("cid") String cid,
                            HttpSession session,
                            Model model){
        AuthUser allUserList = (AuthUser) session.getAttribute("allUserList");
        Integer uid = allUserList.getUid();
        Card cardByIdWithdrawals = cardMapper.getCardById(Integer.valueOf(cid));
        if (cardByIdWithdrawals.getAccount() < account){
            model.addAttribute("hello","余额不足，取款失败！！！");
            return "redirect:/showWithdrawals";
        }else {
            commonService.updateMoneyWithdrawals(Integer.valueOf(cid),account,uid,model);
            return "redirect:/showCard";
        }
        /*commonService.updateMoneyWithdrawals(Integer.valueOf(cid),account,uid,model);*/
    }
    @RequestMapping("getUnameByCid")
    public void getUnameByCid(@RequestParam("cid") String cid ,
                              HttpServletResponse resp,
                              HttpServletRequest req
    ){
        commonService.AsynchronousRefresh(Integer.valueOf(cid), resp , req);
    }
    @RequestMapping("formTransfer")
    public String formTransfer(
            @RequestParam("cid1") String cid1 ,
            @RequestParam("cid2") String cid2 ,
            @RequestParam("money") String money,
            Model model,
            HttpSession session
    ){
        AuthUser allUserList = (AuthUser) session.getAttribute("allUserList");
        Integer uid = allUserList.getUid();
        commonService.judgeCid(Integer.valueOf(cid1),
                Integer.valueOf(cid2),
                Double.valueOf(money),
                model,uid);

        return "redirect:/showCard";
    }
}
