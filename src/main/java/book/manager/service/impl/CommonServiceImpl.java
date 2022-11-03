package book.manager.service.impl;

import book.manager.entity.AuthUser;
import book.manager.entity.Card;
import book.manager.entity.Log;
import book.manager.mapper.CardMapper;
import book.manager.mapper.LogMapper;
import book.manager.mapper.UserMapper;
import book.manager.service.CardService;
import book.manager.service.CommonService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {

    @Resource
    CardMapper cardMapper;

    @Resource
    LogMapper logMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    CardService cardService;

    @Override
    public Card commonByCid(Integer cid) {
        return cardMapper.getCardById(cid);
    }

    @Override
    public Integer updateMoney(Integer cid, Double money, Integer userId ) {
        Card cardById = cardMapper.getCardById(cid);
        Double newAccount = cardById.getAccount();
        cardById.setAccount(newAccount + money);
        Integer updateAccount = cardMapper.updateAccount(String.valueOf(cid), cardById.getAccount());
        if (updateAccount > 0){
            Log log = new Log();
            log.setType(1);
            log.setUserId(userId);
            log.setInfo("用户"+userId+"修改了："+cid+"的银行卡"+"增加了"+money+"元￥");
            log.setCreateTime(new Date());
            logMapper.addLog(log);
        }
        return updateAccount;
    }

    @SneakyThrows
    @Override
    public void AsynchronousRefresh(Integer cid, HttpServletResponse resp, HttpServletRequest req) {
        //cid为空直接返回未知
        if (cid == null){
            resp.getWriter().println("Unknown");//未知
        }
        Card selectById = SelectById(cid);
        //卡号不存在
        if (selectById == null){
            resp.getWriter().println("Unknown");//未知
        }else {
            //卡号存在
            AuthUser selectUserById = SelectUserById(selectById.getUid());
            resp.getWriter().println(selectUserById.getUname());
        }
    }

    @Override
    public Integer updateMoneyWithdrawals(Integer cid, Double money, Integer userId ,Model model) {
        Card cardByIdWithdrawals = cardMapper.getCardById(cid);
        Double newWithdrawalsAccount = cardByIdWithdrawals.getAccount();
        cardByIdWithdrawals.setAccount(newWithdrawalsAccount - money);
        Integer updateAccount = cardMapper.updateAccount(String.valueOf(cid), cardByIdWithdrawals.getAccount());
        if (updateAccount > 0){
            Log log = new Log();
            log.setType(1);
            log.setUserId(userId);
            log.setInfo("用户"+userId+"修改了："+cid+"的银行卡"+"减少了"+money+"元￥");
            log.setCreateTime(new Date());
            logMapper.addLog(log);
        }
        if (updateAccount > 0){
            model.addAttribute("hello","取款成功！！！");
        }else {
            model.addAttribute("hello","取款失败！！！");
        }
        return updateAccount;
    }

    @Override
    public Card SelectById(Integer cid) {
        return cardMapper.getCardById(cid);
    }

    @Override
    public AuthUser SelectUserById(Integer uid) {
        return userMapper.ByIdUser(uid);
    }

    /* 判断前端转账
        卡号
     * - cid1 转账人账户 [执行取款的逻辑接口]
     * - cid2 接受人账户 [执行存款的逻辑接口]
     */
    @Override
    public void judgeCid(Integer cid1, Integer cid2, Double money, Model model , Integer userId) {
        //判断参数是否有误
        if (cid1 == null || cid2 == null || model == null ){
            assert model != null;
            model.addAttribute("message","参数有误，请检查后操作！");
        }
        //判断卡号是否存在
        Card card1 = cardMapper.getCardById(cid1);
        Card card2 = cardMapper.getCardById(cid2);
        if (card1 == null || card2 == null){
            model.addAttribute("message","存在未知卡号，请检查参数后操作！");
        }
        // 判断余额
        assert card1 != null;
        if (card1.getAccount() < money){
            model.addAttribute("message","转账余额不足！");
        }
        //实际转账操作
        Integer cid2Money = updateMoneyWithdrawals(cid1, money, userId, model);//取款的方法
        Integer cid1Money = updateMoney(cid2, money, userId);//存款的方法


        // 通过cid查询uid
        Integer getUid1 = cardMapper.getCidSelectUid(cid1);
        Integer getUid2 = cardMapper.getCidSelectUid(cid2);
        // 通过uid查uname
        AuthUser authUser1 = userMapper.ByIdUser(getUid1);
        AuthUser authUser2 = userMapper.ByIdUser(getUid2);

        if (cid1Money > 0 && cid2Money > 0){
            Log log = new Log();
            log.setType(1);
            log.setUserId(userId);
            log.setInfo("客户"+"编号为："+getUid1+"对卡号："+cid1+"的客户名称为："+authUser1.getUname()+
                    "向客户编号为："+getUid2+"  卡号为："+cid2+"的客户名称为："+authUser2.getUname()+
                    "转账！转账金额为："+money+"元￥");
            log.setCreateTime(new Date());
            logMapper.addLog(log);
            model.addAttribute("message","转账成功！");
        } else {
            model.addAttribute("message","转账失败");
        }
    }

    @Override
    public void judgeCard(Card card) {

    }


}
