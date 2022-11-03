package book.manager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true) //生成链式调用
@AllArgsConstructor //生成有参构造器
@NoArgsConstructor //生成无参构造器
public class Card {
    /**
     * 卡号
     */
    private Integer cid;
    /**
     * 卡 类型
     */
    private String type;
    /**
     * 卡 余额
     */
    private Double account;
    /**
     * 卡 持有者id
     */
    private Integer uid;
    /**
     * 放入User对象
     * 账户信息
     */
    private AuthUser authUser;

}
