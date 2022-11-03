package book.manager.mapper;

import book.manager.entity.AuthUser;
import book.manager.entity.Card;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CardMapper {
    @Select("select * from card")
    List<Card> allCard();

    @Delete("delete from card where cid = #{cid}")
    Integer deleteCard(Integer cid);

    @Select("select * from card")
    Card getCardAllObject();

    @Insert("insert into card (type, account, uid) value(#{type},#{account},#{uid})")
    @Options(useGeneratedKeys = true, keyProperty = "cid", keyColumn = "cid")
    Integer addCard(Card card);

    @Select("select * from card where cid=#{cid}")
    Card getCardById(Integer cid);

    @Update("update card set account=#{account} where cid=#{cid}")
    Integer updateAccount(@Param("cid") String cid,@Param("account") Double account);
    /**
     * 通过cid查uid
     */
    @Select("select uid from card where cid=#{cid}")
    Integer getCidSelectUid(Integer cid);
}
