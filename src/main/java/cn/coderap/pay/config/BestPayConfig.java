package cn.coderap.pay.config;

import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yw
 * 2021/4/19
 */
@Configuration
public class BestPayConfig {

    @Bean
    public BestPayService bestPayService() {
        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setAppId("wx00d52d21505f383f");
        wxPayConfig.setMchId("1226270402");
        wxPayConfig.setMchKey("shengwei520soucang96567key1987sw");
        //异步回调地址必须公网能够访问，且不能带参数
        wxPayConfig.setNotifyUrl("http://ikr6v4.natappfree.cc/pay/notify");

        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayConfig(wxPayConfig);
        return bestPayService;
    }
}
