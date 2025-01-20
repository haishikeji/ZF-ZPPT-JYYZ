package com.px.employmentsite.controller;

import com.px.employmentsite.AppConfig;
import com.px.employmentsite.util.DateUtils;
import com.px.employmentsite.util.DesUtils;
import com.px.employmentsite.util.RemoteHelper;
import com.px.employmentsite.util.StringUtils;
import com.px.employmentsite.vo.DesModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/")
@Slf4j
public class AppController {

    @Autowired
    private StringUtils stringUtils;
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private RemoteHelper remoteHelper;
    @Autowired
    private DateUtils dateUtils;
    @Autowired
    private DesUtils desUtils;

    @GetMapping("")
    public String web(HttpServletRequest request, HttpServletResponse response, String ticket,String loginType) {
        /*if (!stringUtils.IsNullOrEmpty(ticket))
            return "redirect:/web/index.html?ticket=" + encoderTicket(ticket,appConfig.oauthconfig_webServiceUrl);

        if (!stringUtils.IsNullOrEmpty(loginType))
            return "redirect:/web/index.html?loginType=" + loginType;*/

        return "redirect:/web/index.html";
    }

    @GetMapping("web")
    public String web(String ticket,String loginType) {
        /*if (!stringUtils.IsNullOrEmpty(ticket))
            return "redirect:/web/index.html?ticket=" + encoderTicket(ticket,appConfig.oauthconfig_webServiceUrl);

        if (!stringUtils.IsNullOrEmpty(loginType))
            return "redirect:/web/index.html?loginType=" + loginType;*/

        return "redirect:/web/index.html";
    }

    @GetMapping("mobile")
    public String mobile(String routePath,String code) throws UnsupportedEncodingException {
        if (!stringUtils.IsNullOrEmpty(routePath))
            return "redirect:/mobile/index.html/#/index?routePath=" + URLEncoder.encode(routePath,"UTF-8")+"&code="+code;

        return "redirect:/mobile/index.html";
    }

    //解析单点登录返回的ticket获取用户帐号
    private String encoderTicket(String ticket,String serviceUrl) {
        log.info("getUserInfo-cas：ticket   " + ticket);
        Map<String, String> getParams = new HashMap<>();
        getParams.put("service", serviceUrl);
        getParams.put("ticket", ticket);
        String reData = remoteHelper.getJson(getParams, appConfig.oauthconfig_oauthUrl + "/auth/cas/p3/serviceValidate", "UTF-8");
        log.info("getUserInfo-cas：result   " + reData);

        String userCode = "";

        if (reData.indexOf("cas:authenticationSuccess") >= 0 && reData.indexOf("cas:user_code") >= 0) {
            userCode = reData.split("<cas:user_code>")[1].split("</cas:user_code>")[0];
        }

        if (!stringUtils.IsNullOrEmpty(userCode)) {
            Date expireDate = new Date(System.currentTimeMillis() + 60000);//过期时间一分钟
            String expireStr = dateUtils.dateToStrFormat(expireDate, "yyyy/MM/dd HH:mm:ss");
            DesModel desModel = desUtils.encoder(userCode, expireDate);

            String tokenEncode = URLEncoder.encode(desModel.getToken());
            String tmEncode = "&tm=" + URLEncoder.encode(expireStr);

            log.info("getUserInfo-cas：encoderResult   " + tokenEncode + tmEncode);

            return tokenEncode + tmEncode;
        }

        return ticket;
    }


}
