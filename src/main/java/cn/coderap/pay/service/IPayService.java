package cn.coderap.pay.service;

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
}
