package book.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {
    @RequestMapping(value = "/commonIndex" , produces = "text/html;charset=UTF-8")
    public String CommonIndex(Model model){

        return "Common/common";
    }
    @RequestMapping(value = "/query", produces = "text/html;charset=UTF-8")
    public String Query(){
        return "Common/query";
    }
    @RequestMapping(value = "/deposit")
    public String deposit(){
        return "Common/deposit";
    }
    @RequestMapping(value = "showWithdrawals",produces = "text/html;charset=UTF-8")
    public String Withdrawals(){
        return "Common/withdrawals";
    }
    @RequestMapping(value = "showTransfer",produces = "text/html;charset=UTF-8")
    public String transfer(){
        return "Common/transfer";
    }
}
