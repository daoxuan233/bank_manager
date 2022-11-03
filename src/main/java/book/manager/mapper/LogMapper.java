package book.manager.mapper;
;
import book.manager.entity.Log;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LogMapper {

    @Insert("insert into log(type,userId,createTime,info) value(#{type},#{userId},#{createTime},#{info})")
    void addLog(Log log);

    @Select("select count(type) from log")
    Integer queryLogAllNumberData();

    @Select("select * from log where userId=#{userId}")
    List<Log> getUserId(Integer userId);
}
