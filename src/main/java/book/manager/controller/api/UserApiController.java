package book.manager.controller.api;

import book.manager.entity.AuthUser;
import book.manager.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/api/user")
public class UserApiController {

    @Resource
    UserService service;

    @RequestMapping(value = "add-user", method = RequestMethod.POST)
    public String addBook(@RequestParam("uname") String uname,
                          @RequestParam("umail") String umail,
                          @RequestParam("password") String password,
                          @RequestParam("phone") String phone,
                          @RequestParam("identity") String identity,
                          @RequestParam("role") String role,
                          HttpSession session){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(password);
        /*System.out.println(session.getAttribute("allUserList"));*/
        AuthUser allUserList = (AuthUser) session.getAttribute("allUserList");

        service.addUser(new AuthUser()
                .setUname(uname)
                .setUmail(umail)
                .setPassword(encode)
                .setPhone(phone)
                .setIdcard(identity)
                .setRole(role)
                .setTtime(new Date()),
                allUserList.getUid()
        );
        System.out.println(uname);
        return "redirect:/page-user";
    }

    @RequestMapping(value = "/delete-User", method = RequestMethod.GET)
    public String deleteBook(@RequestParam("uid") int uid,HttpSession session){
        AuthUser allUserList = (AuthUser) session.getAttribute("allUserList");
        service.deleteUser(uid,allUserList.getUid());
        return "redirect:/page-user";
    }

    @RequestMapping(value = "/modify-user" , method = RequestMethod.GET)
    public String modify(@RequestParam("uid") Integer uid,
                         @RequestParam("uname") String uname,
                         @RequestParam("umail") String umail,
                         @RequestParam("password") String password,
                         @RequestParam("phone") String phone,
                         @RequestParam("identity") String identity,
                         @RequestParam("role") String role,
                         HttpSession session){
        AuthUser allUserList = (AuthUser) session.getAttribute("allUserList");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(password);
        service.updateByIdUser(new AuthUser()
                .setUid(uid)
                .setUname(uname)
                .setUmail(umail)
                .setPassword(encode)
                .setPhone(phone)
                .setIdcard(identity)
                .setRole(role),
                allUserList.getUid()
        );
        return "redirect:/page-user";
    }
}
