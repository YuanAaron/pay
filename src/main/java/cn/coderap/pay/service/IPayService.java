package cn.coderap.pay.service;

import cn.coderap.pay.pojo.PayInfo;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;

import java.math.BigDecimal;

public interface IPayService {
    /**
     * 创建/发起支付
     * @param orderId
     * @param amount
     */
    PayResponse create(String orderId, BigDecimal amount, BestPayTypeEnum bestPayTypeEnum);

    /**
     * 异步通知的处理
     * @param notifyData
     */
    String asyncNotify(String notifyData);

    /**
     * 通过订单号查询支付状态，支付完成页面跳转
     * @param orderId
     * @return
     */
    PayInfo queryPayStatusByOrderId(String orderId);
}
