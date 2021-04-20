package cn.coderap.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by yw
 * 2021/4/20
 */
@Data
@ConfigurationProperties(prefix = "alipay")
@Component
public class AlipayAccountConfig {
    private String appId;
    private String privateKey;
    private String aliPayPublicKey;
    private String notifyUrl;
    private String returnUrl;
}
