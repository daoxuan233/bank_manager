package book.manager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true) //生成链式调用
public class Log {

    private Integer id;

    private Integer type;

    private Integer userId;

    private Date createTime;

    private String info;

    private String TypeInfo;

}
