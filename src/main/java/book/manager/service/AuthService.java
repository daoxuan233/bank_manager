package book.manager.service;

import book.manager.entity.AuthUser;

import javax.servlet.http.HttpSession;

public interface AuthService {

    AuthUser findUser(HttpSession session);
}
