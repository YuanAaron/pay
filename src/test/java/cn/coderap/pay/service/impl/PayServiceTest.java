package cn.coderap.pay.service.impl;
import cn.coderap.pay.PayApplicationTests;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;

public class PayServiceTest extends PayApplicationTests {

    @Resource
    private PayService payService;

    @Test
    public void create() {
        //或者new BigDecimal("0.01"); 千万不要写成 new BigDecimal(0.01);
        payService.create("1234567891234567", BigDecimal.valueOf(0.01));
    }
}