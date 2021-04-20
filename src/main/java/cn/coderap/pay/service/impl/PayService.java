package cn.coderap.pay.service.impl;

import cn.coderap.pay.enums.PayPlatformEnum;
import cn.coderap.pay.mapper.PayInfoMapper;
import cn.coderap.pay.pojo.PayInfo;
import cn.coderap.pay.service.IPayService;
import com.lly835.bestpay.enums.BestPayPlatformEnum;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.enums.OrderStatusEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Created by yw
 * 2021/4/18
 */
@Slf4j
@Service
public class PayService implements IPayService {

    @Resource
    private BestPayService bestPayService;
    @Resource
    private PayInfoMapper payInfoMapper;

    @Transactional
    @Override
    public PayResponse create(String orderId, BigDecimal amount, BestPayTypeEnum bestPayTypeEnum) {
        //商户订单信息写入数据库
        PayInfo payInfo = new PayInfo(Long.valueOf(orderId),
                PayPlatformEnum.getByBestPayTypeEnum(bestPayTypeEnum).getCode(),
                OrderStatusEnum.NOTPAY.name(),
                amount);
        payInfoMapper.insertSelective(payInfo);

        PayRequest request = new PayRequest();
        request.setOrderName("8066134-支付sdk");
        request.setOrderId(orderId);
        request.setOrderAmount(amount.doubleValue());
        request.setPayTypeEnum(bestPayTypeEnum);

        PayResponse response = bestPayService.pay(request);
        log.info("response={}", response);
        return response;
    }

    @Override
    public String asyncNotify(String notifyData) {
        //1、验证签名(best-pay-sdk实现了)
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("payResponse={}", payResponse);
        //2、校验返回的订单金额是否与商户侧的订单金额一致（从数据库查订单）
        PayInfo payInfo = payInfoMapper.selectByOrderNo(Long.valueOf(payResponse.getOrderId()));
        if (payInfo == null) {
            throw new RuntimeException("通过orderNo查询到的结果是null");
        }
        //如果订单支付状态不是“支付成功”
        if (!payInfo.getPlatformStatus().equals(OrderStatusEnum.SUCCESS.name())) {
            if (payInfo.getPayAmount().compareTo(BigDecimal.valueOf(payResponse.getOrderAmount())) != 0) {
                throw new RuntimeException("异步通知中的金额和数据库里的不一致，orderNo=" + payResponse.getOrderId());
            }
            //3、修改订单的支付状态
            payInfo.setPlatformStatus(OrderStatusEnum.SUCCESS.name());
            payInfo.setPlatformNumber(payResponse.getOutTradeNo());
            payInfoMapper.updateByPrimaryKeySelective(payInfo);
        }

        //4、告诉微信不要再通知了
        if (payResponse.getPayPlatformEnum()== BestPayPlatformEnum.WX) {
            return "<xml>\n" +
                    "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                    "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                    "</xml>";
        }else if (payResponse.getPayPlatformEnum()== BestPayPlatformEnum.ALIPAY) {
            //4、告诉支付宝不要再通知了
            return "success";
        }
        throw new RuntimeException("异步通知中错误的支付平台");
    }
}
