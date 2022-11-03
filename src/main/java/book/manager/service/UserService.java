package book.manager.service;

import book.manager.entity.AuthUser;

public interface UserService {
    Integer addUser(AuthUser user,Integer userId);

    Integer deleteUser(Integer uid , Integer userId);

    AuthUser goModify(Integer uid , Integer userId);

    Integer updateByIdUser(AuthUser authUser , Integer userId);

    Integer Data();

    String money();

    AuthUser getUserByEmail(String uEmail , String uname);

}
