package book.manager.controller;

import book.manager.entity.AuthUser;
import book.manager.entity.Log;
import book.manager.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LogController {
    @Resource
    LogService logService;

    @RequestMapping("showLogSystem")
    public String showLog(HttpSession session , Model model){
        AuthUser allUserList = (AuthUser) session.getAttribute("allUserList");
        Integer uid = allUserList.getUid();
        List<Log> logs = logService.showLogData(uid);
        model.addAttribute("showLogsData",logs);//显示log中的相关数据

        model.addAttribute("sessionID",uid); //session传前端

        AuthUser authUser = logService.showUserData(uid);
        model.addAttribute("showUserData",authUser); //显示user数据
        return "Log/showLog";
    }

}
