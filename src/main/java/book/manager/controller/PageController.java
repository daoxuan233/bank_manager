package book.manager.controller;

import book.manager.entity.AuthUser;
import book.manager.mapper.UserMapper;
import book.manager.service.AuthService;
import book.manager.service.UserService;
import book.manager.util.Email;
import lombok.extern.java.Log;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Random;

@Log
@Controller
public class PageController {
    @Resource
    UserMapper userMapper;
    @Resource
    UserService userService;
    @Resource
    AuthService service;

    @RequestMapping("/index")
    @PreAuthorize("hasAnyRole('user','admin')")   //判断是否为user角色，只有此角色才可以访问
    public String index(Model model, HttpSession session){
        model.addAttribute("allUserList", service.findUser(session));
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        User user = (User) authentication.getPrincipal();
        model.addAttribute("userList",user.getUsername());
        model.addAttribute("data" ,userService.Data());
        model.addAttribute("money",userService.money());
        log.info("进index了");
        return "index";
    }
    @RequestMapping("/login")
    public String login(){/*@RequestParam String username , @RequestParam String email , Model model , HttpSession session*/
        /*//生成六位数验证码
        String Captcha = String.valueOf(new Random().nextInt(899999) + 100000);
        System.out.println("验证码："+Captcha);
        *//*session.setAttribute("Captcha", Captcha);//存储验证码*//*
        Email message = new Email();
        message.sendMail(email,"Book Manage银行管理系统提醒，您的验证码为："+Captcha);
        // 获取用户的邮箱
        // 实例化用户对象
        AuthUser user = userService.getUserByEmail(email,username);// 根据页面获取到的邮箱找到该用户信息*/
        return "/login";
    }

    @RequestMapping(value = "/page-user",produces = "text/html;charset=UTF-8")
    @PreAuthorize("hasAnyRole('user','admin')")   //判断是否为user角色，只有此角色才可以访问
    public String user(Model model , HttpSession session){
        model.addAttribute("allUserList", service.findUser(session));
        List<AuthUser> userList = userMapper.AllUser();
        model.addAttribute("userList",userList);
        return "user/user";
    }
    @RequestMapping(value = "/adduser",produces = "text/html;charset=UTF-8")
    public String addUser(Model model , HttpSession session) {
        model.addAttribute("allUserList", service.findUser(session));
        List<AuthUser> authUsers = userMapper.AllUser();
        model.addAttribute("authUsers", authUsers);
        return "user/adduser";
    }
    @RequestMapping(value = "/goModifyUser" , produces = "text/html;charset=UTF-8")
    public String modifyUser(Model model , HttpSession session , @RequestParam("uid") Integer uid ){
        AuthUser allUserList = (AuthUser) session.getAttribute("allUserList");
        AuthUser goModify = userService.goModify(uid,allUserList.getUid());
        model.addAttribute("modify",goModify);
        return "user/modifyUser";
    }
    @RequestMapping(value = "LogOut",produces = "text/html;charset=UTF-8")
    public String logout(){
        return "exit";
    }
}
