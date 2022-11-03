package book.manager.mapper;

import book.manager.entity.AuthUser;
import book.manager.entity.Card;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user where uname = #{uname}")
    AuthUser getPasswordByUsername(String username);

    @Select("select count(uname) from user")
    Integer getUserData();

    /*
    @Select("select uid from user where uname=#{uname}")
    Integer ByNameGetId(String uname);
*/
    @Select("select * from user")
    List<AuthUser> AllUser();

    @Insert("insert into user(uname,umail,password,phone,idcard,role,activation,ttime) value(#{uname},#{umail},#{password},#{phone},#{idcard},#{role},0,#{ttime})")
    @Options(useGeneratedKeys = true, keyProperty = "uid", keyColumn = "uid")
    Integer addUser(AuthUser user);

    @Delete("delete from user where uid = #{uid}")
    Integer deleteUser(Integer uid);

    @Select("select * from user")
    AuthUser objectUser();

    @Select("select * from user where uid=#{uid}")
    AuthUser ByIdUser(Integer uid);

    @Update("UPDATE user SET uname=#{uname},umail=#{umail},password=#{password},phone=#{phone},idcard=#{idcard},role=#{role} WHERE uid=#{uid}")
    Integer updateByIdUser(AuthUser authUser);


    @Select("select * from user where umail=#{umail}")
    AuthUser getUserByEmail(String umail);

    @Select("select uid from user where uname=#{uname}}")
    Integer getUserByUid(String uname);

    @Select("select sum(account) from bank.card")
    String getMoneyData();

}
