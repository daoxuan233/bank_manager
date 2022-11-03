package book.manager.controller.api;

import book.manager.entity.AuthUser;
import book.manager.entity.Card;
import book.manager.mapper.UserMapper;
import book.manager.service.CardService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("/api/card")
public class CardApiController {
    @Resource
    CardService cardService;
    @Resource
    UserMapper userMapper;

    @RequestMapping(value = "/delete-card", method = RequestMethod.GET)
    public String deleteBook(@RequestParam("cid") int uid, HttpSession session){
        System.out.println("进delete……");
        AuthUser allUserList = (AuthUser) session.getAttribute("allUserList");
        cardService.deleteCard(uid,allUserList.getUid());
        return "redirect:/showCard";
    }

    @RequestMapping(value = "add-card",method = RequestMethod.POST)
    public String addCard(@RequestParam("type") String type,
                          @RequestParam("account") String account,
                          @RequestParam("uid") String uid,
                          HttpSession session) throws IOException {
        /*System.out.println(type);*/
        AuthUser allUserList = (AuthUser) session.getAttribute("allUserList");
        cardService.addCard(new Card()
                        .setType(type)
                        .setAccount(Double.valueOf(account))
                        .setUid(Integer.valueOf(uid)),
                allUserList.getUid()
        );
        return "redirect:/showCard";
    }
    @RequestMapping(value = "getOpener" )
    public void getOpener(@RequestParam("uid") String uid ,HttpServletResponse resp) throws IOException {
        System.out.println("uid:"+uid);
        AuthUser byIdUser = userMapper.ByIdUser(Integer.valueOf(uid));
        resp.getWriter().
                println(byIdUser == null ? "User does not exist, please try again!!!" :
                        byIdUser.getUname());//用户不存在，请重试！！！
    }
}
