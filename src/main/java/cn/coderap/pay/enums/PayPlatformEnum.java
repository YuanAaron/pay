package cn.coderap.pay.enums;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayPlatformEnum {

    // 1-支付宝,2-微信
    ALIPAY(1),
    WX(2);

    private Integer code;

    //BestPayTypeEnum转PayPlatformEnum
    //PayPlatformEnums.values()返回PayPlatformEnum数组
    //payPlatformEnum.name()返回ALIPAY或WX(字符串)
    public static PayPlatformEnum getByBestPayTypeEnum(BestPayTypeEnum bestPayTypeEnum) {
        for (PayPlatformEnum payPlatformEnum : PayPlatformEnum.values()) {
            if (bestPayTypeEnum.getPlatform().name().equals(payPlatformEnum.name())) {
                return payPlatformEnum;
            }
        }
        throw new RuntimeException("错误的支付平台：" + bestPayTypeEnum.name());
    }
}
