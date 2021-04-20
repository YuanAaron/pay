package cn.coderap.pay.controller;

import cn.coderap.pay.service.IPayService;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yw
 * 2021/4/19
 */
@Slf4j
@Controller
@RequestMapping("/pay")
public class PayController {

    @Resource
    private IPayService iPayService;

    //注意：这里不能发送get请求（得到的是支付页面的源码），只能跳转让浏览器访问才能拿到二维码
    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("amount") BigDecimal amount,
                               @RequestParam("payType") BestPayTypeEnum bestPayTypeEnum) {
        //从response可知，多次访问create方法微信返回的code_url是不同的
        PayResponse response = iPayService.create(orderId, amount,bestPayTypeEnum);

        Map<String,String> map = new HashMap<>();
        //支付方式不同，渲染就不同，WXPAY_NATIVE使用code_url，ALIPAY_PC使用body
        if (bestPayTypeEnum == BestPayTypeEnum.WXPAY_NATIVE) {
            map.put("codeUrl", response.getCodeUrl());
            return new ModelAndView("createForWxNative",map);
        }else if (bestPayTypeEnum == BestPayTypeEnum.ALIPAY_PC) {
            //得到的body是一个表单，且该表单是自动提交的，为了实现页面的跳转，将该body渲染到一个html中即可
            map.put("body", response.getBody());
            return new ModelAndView("createForAlipayPC",map);
        }
        throw new RuntimeException("暂不支持的支付类型");
    }

    @PostMapping("/notify")
    @ResponseBody
    public String asyncNotify(@RequestBody String notifyData) {
        return iPayService.asyncNotify(notifyData);
    }
}
