package book.manager.service;

import book.manager.entity.AuthUser;
import book.manager.entity.Card;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface CommonService {
    Card commonByCid(Integer cid);

    /**
     * 存款 deposit
     */
    Integer updateMoney(Integer cid , Double money, Integer userId);
    /**
     * 异步刷新，执行Ajax
     */
    void AsynchronousRefresh(Integer cid , HttpServletResponse resp ,HttpServletRequest req);
    /**
     * 取款
     * updateMoneyWithdrawals
     */
    Integer updateMoneyWithdrawals(Integer cid , Double money , Integer userId , Model model);

    /**
     * 转账异步刷新
     *          - 卡号
     */
    Card SelectById(Integer cid);
    /**
     * 转账异步刷新
     *          - 账户
     */
    AuthUser SelectUserById(Integer uid);

    /**
     * 判断前端转账
     *          cid
     *
     *       判断前端转账
     *                卡号
     *                - cid1 转账人账户 [执行取款的逻辑接口]
     *                - cid2 接受人账户 [执行存款的逻辑接口]
     *
     */
    void judgeCid(Integer cid1, Integer cid2, Double money, Model model , Integer uid);

    void judgeCard(Card card);
}
