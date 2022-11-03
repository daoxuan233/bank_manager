package book.manager.service.impl;

import book.manager.entity.AuthUser;
import book.manager.mapper.UserMapper;
import book.manager.service.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    UserMapper mapper;

    @Override
    public AuthUser findUser(HttpSession session) {
        AuthUser user = (AuthUser) session.getAttribute("allUserList");
        if(user == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            user = mapper.getPasswordByUsername(authentication.getName());
            session.setAttribute("allUserList", user);
        }
        return user;
    }
}
