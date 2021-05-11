package cn.coderap.pay.service.impl;
import cn.coderap.pay.PayApplicationTests;
import cn.coderap.pay.service.IPayService;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;

public class PayServiceTest extends PayApplicationTests {

    @Resource
    private IPayService payService;
    @Resource
    private AmqpTemplate amqpTemplate;

    @Test
    public void create() {
        //或者new BigDecimal("0.01"); 千万不要写成 new BigDecimal(0.01);
        payService.create("1234567891234567", BigDecimal.valueOf(0.01), BestPayTypeEnum.WXPAY_NATIVE);
    }

    @Test
    public void sendMQMsg() {
        amqpTemplate.convertAndSend("payNotify","hello");
    }
}