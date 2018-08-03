package com.wjc.ccf.web;

import com.wjc.ccf.test.Message;
import com.wjc.ccf.util.SmsUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wjc
 */
@Controller
public class SmsController {

    @GetMapping(value = "/sms")
    public @ResponseBody String sendSms(String phone, String text){
        if(StringUtils.isNotBlank(phone) || StringUtils.isNotBlank(text)){
            return "短信发送失败";
        }
        SmsUtil.send(phone, text);
        return "短信发送成功";
    }
}
