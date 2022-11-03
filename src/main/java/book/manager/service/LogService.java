package book.manager.service;

import book.manager.entity.AuthUser;
import book.manager.entity.Log;

import java.util.List;

public interface LogService {
    /**
     * 显示 log表中的信息
     */
    List<Log> showLogData(Integer userId);
    /**
     * 显示对应的 userId 信息
     */
    AuthUser showUserData(Integer userId);
}
