package book.manager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Accessors(chain = true) //生成链式调用
@AllArgsConstructor //生成有参构造器
@NoArgsConstructor //生成无参构造器
public class AuthUser {
    /**
     * 用户id（账号）
     */
    private Integer uid;
    /**
     * 用户名称
     */
    private String uname;
    /**
     * 邮箱
     */
    private String umail;
    /**
     * 密码
     */
    private String password;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 类型：admin-管理员，user-普通成员
     */
    private String role;
    /**
     * 账号激活状态
     */
    private Integer activation;
    /**
     * 身份证号
     */
    private String idcard;
    /**
     * 开户时间
     */
    private Date ttime;
}
