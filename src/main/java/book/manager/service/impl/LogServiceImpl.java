package book.manager.service.impl;

import book.manager.entity.AuthUser;
import book.manager.entity.Log;
import book.manager.mapper.LogMapper;
import book.manager.mapper.UserMapper;
import book.manager.service.LogService;
import book.manager.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    @Resource
    LogMapper logMapper;

    @Resource
    UserMapper userMapper;

    @Override
    public List<Log>  showLogData(Integer userId) {
        List<Log> LogData = logMapper.getUserId(userId);
        for (Log logData : LogData){
            switch (logData.getType()){
                case 1 : logData.setTypeInfo("执行修改操作"); break;
                case 2 : logData.setTypeInfo("执行查询操作"); break;
                case 3 : logData.setTypeInfo("执行删除操作"); break;
            }
        }
        return LogData;
    }

    @Override
    public AuthUser showUserData(Integer userId) {
        return userMapper.ByIdUser(userId);
    }
}
