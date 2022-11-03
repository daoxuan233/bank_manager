package book.manager.controller;

import book.manager.entity.AuthUser;
import book.manager.entity.Card;
import book.manager.mapper.CardMapper;
import book.manager.mapper.UserMapper;
import book.manager.service.AuthService;
import book.manager.service.CardService;
import book.manager.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CardController {

    @Resource
    AuthService authService;

    @Resource
    CardService cardService;

    @Resource
    UserService userService;

    @Resource
    CardMapper cardMapper;

    @RequestMapping(value = "/showCard" , produces = "text/html;charset=UTF-8")
    public String card(Model model , HttpSession session){
        model.addAttribute("data" ,userService.Data());
        model.addAttribute("money",userService.money());
        model.addAttribute("user", authService.findUser(session));
        model.addAttribute("cardList", cardService.getCardAllData());
        /*model.addAttribute("queryCard",cardService.getCardAllObject());*/
        model.addAttribute("log",cardService.queryLogAllNumberData());
        return "card/card";
    }
    @RequestMapping(value = "/addCard" , produces = "text/html;charset=UTF-8")
    public String addCard(Model model , HttpSession session){
        List<Card> card = cardMapper.allCard();
        model.addAttribute("allCard", card);
        return "card/addCard";
    }

}
