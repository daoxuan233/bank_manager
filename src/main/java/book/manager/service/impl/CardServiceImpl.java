package book.manager.service.impl;

import book.manager.entity.AuthUser;
import book.manager.entity.Card;
import book.manager.entity.Log;
import book.manager.mapper.CardMapper;
import book.manager.mapper.LogMapper;
import book.manager.mapper.UserMapper;
import book.manager.service.CardService;
import book.manager.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    @Resource
    LogMapper logMapper;

    @Resource
    CardMapper cardMapper;

    @Resource
    UserMapper userMapper;

    @Override
    public List<Card> getCardAllData() {
        List<Card> cards = cardMapper.allCard();
        for (Card card : cards){
            AuthUser authUser = userMapper.ByIdUser(card.getUid());
            card.setAuthUser(authUser);
        }
        /*System.out.println(cards);*/
        return cards;
    }

    @Override
    public Integer queryLogAllNumberData() {
        return logMapper.queryLogAllNumberData();
    }

    @Override
    public Integer deleteCard(Integer cid, Integer userId) {
        Integer deleteCard = cardMapper.deleteCard(cid);
        if (deleteCard > 0){
            Log log = new Log();
            log.setType(3);
            log.setUserId(userId);
            log.setInfo("用户"+userId+"删除编号为："+cid+"的银行卡");
            log.setCreateTime(new Date());
            logMapper.addLog(log);
        }
        return deleteCard;
    }

    @Override
    public Card getCardAllObject() {
        return cardMapper.getCardAllObject();
    }

    @Override
    public Integer addCard(Card card, Integer userId) {
        Integer addCard = cardMapper.addCard(card);
        if (addCard > 0){
            Log log = new Log();
            log.setType(1);
            log.setUserId(userId);
            log.setInfo("用户"+userId+"新开卡，" +
                    "卡号为："+card.getCid()+"类型为："+card.getType());
            log.setCreateTime(new Date());
            logMapper.addLog(log);
        }
        return addCard;
    }
}
