package book.manager.service.impl;

import book.manager.entity.AuthUser;
import book.manager.entity.Log;
import book.manager.mapper.LogMapper;
import book.manager.mapper.UserMapper;
import book.manager.service.AuthService;
import book.manager.service.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;
    @Resource
    LogMapper logMapper;

    @Override
    public Integer addUser(AuthUser user,Integer userId ) {
        Integer count = userMapper.addUser(user);
        if (count > 0 ){
            Log log = new Log();
            log.setType(1);
            log.setUserId(userId);
            log.setInfo("用户"+userId+"添加账号为："+user.getUname()+"/编号为："+user.getUid()+"的用户");
            log.setCreateTime(new Date());
            logMapper.addLog(log);
        }
        return count;
    }
    @Override
    public Integer deleteUser(Integer uid , Integer userId) {
        Integer count = userMapper.deleteUser(uid);
        if (count > 0 ){
            Log log = new Log();
            log.setType(3);
            log.setUserId(userId);
            log.setInfo("用户"+userId+"删除编号为："+uid+"的用户");
            log.setCreateTime(new Date());
            logMapper.addLog(log);
        }
        return count;
    }

    @Override
    public AuthUser goModify(Integer uid , Integer userId) {
        /*System.out.println(uid);
        AuthUser authUser = mapper.ByIdUser(uid);
        System.out.println(authUser);*/
        AuthUser authUser = userMapper.ByIdUser(uid);
        if (authUser != null){
            Log log = new Log();
            log.setType(2);
            log.setUserId(userId);
            log.setInfo("用户"+userId+"查询了编号为："+uid+"的用户");
            log.setCreateTime(new Date());
            logMapper.addLog(log);
        }
        return authUser;
    }

    @Override
    public Integer updateByIdUser(AuthUser authUser , Integer userId) {
        Integer count = userMapper.updateByIdUser(authUser);
        if (count > 0 ){
            Log log = new Log();
            log.setType(3);
            log.setUserId(userId);
            log.setInfo("用户"+userId+"修改账户原为："+authUser.getUname()+"编号为："+authUser.getUid()+"的用户");
            log.setCreateTime(new Date());
            logMapper.addLog(log);
        }
        return count;
    }

    @Override
    public Integer Data() {
        Integer userData = userMapper.getUserData();//总数
        if (userData > 0 ){
            Log log = new Log();
            log.setType(2);
            /*log.setUserId(userId);*/
            log.setInfo("用户查询汇总了user中所有数据");
            log.setCreateTime(new Date());
            logMapper.addLog(log);
        }
        return userData;
    }

    @Override
    public String money() {
        String moneyData = userMapper.getMoneyData();//bank总金额
        if (moneyData != null ){
            Log log = new Log();
            log.setType(2);
            /*log.setUserId(userId);*/
            log.setInfo("用户查询汇总了account中所有数据");
            log.setCreateTime(new Date());
            logMapper.addLog(log);
        }
        return moneyData;
    }

    @Override
    public AuthUser getUserByEmail(String uEmail , String uname) {
        Integer userId = userMapper.getUserByUid(uname);
        AuthUser userByEmail = userMapper.getUserByEmail(uEmail);
        if (userByEmail != null){
            Log log = new Log()
                    .setType(2)
                    .setUserId(userId)
                    .setInfo("用户：(编号)"+userId+"，"+"用户名："+uname+";进行了一次登录校验,"+"传入验证的邮箱为："+uEmail+".")
                    .setCreateTime(new Date());
            logMapper.addLog(log);
        }
        return userByEmail;
    }


}
