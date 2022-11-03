package book.manager.service;

import book.manager.entity.AuthUser;
import book.manager.entity.Card;
import book.manager.entity.Log;

import java.util.List;

public interface CardService {
    /**
     * 查询Card所有的数据
     * 实现id查询uname
     * @return List <Card>
     */
    List<Card> getCardAllData();

    /**
     * 查询Log中总的数据
     * @return Integer
     */
    Integer queryLogAllNumberData();

    /**
     * 根据ID删除Card中的数据
     * @return Integer
     */
    Integer deleteCard(Integer cid , Integer userId);

    /**
     * 查询Card中的所有数据，以Card对象的方式
     */
    Card getCardAllObject();

    /**
     * 添加Card
     */
    Integer addCard(Card card, Integer userId);

}
