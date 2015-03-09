package ca.app.frontEnd.controller;



import org.apache.commons.lang3.StringUtils;
import org.openid4java.OpenIDException;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.ax.FetchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class AuthenticationController {

    @Autowired
    OpenID4JavaConsumer openIdConsumer;

    @Autowired
    AuthenticationManager authenticationManager;


    public static final String MOUNT_PATH = "openid/finish";

    @RequestMapping("/login")
    public String login(@RequestParam("openid") String openidIdentifier, @RequestParam("accountId") String accountId, Model model,
                        HttpServletRequest request, HttpServletResponse httpResp) {
        String requestedUrl = request.getRequestURL().toString();
        String baseUrl = requestedUrl.substring(0, requestedUrl.indexOf("/login"));

        String returnUrl = String.format("%s/%s", baseUrl, MOUNT_PATH);
        try {
            String redirectUrl = openIdConsumer.beginConsumption(request, openidIdentifier, returnUrl, baseUrl);
            System.out.println("redirectUrl: " + redirectUrl);
            return "redirect:" + redirectUrl;
        } catch (OpenIDConsumerException e) {
            System.out.println(String.format("Error occurred while trying to log in with %s.", openidIdentifier));
        }
        return "login";
    }

    @RequestMapping(MOUNT_PATH)
    public String returnOpenId(HttpServletRequest request){
        OpenIDAuthenticationToken token = null;
        try {
            token = openIdConsumer.endConsumption(request);
        } catch (OpenIDConsumerException e) {
            System.err.println("error ");
        }
        String requestedUrl = request.getRequestURL().toString();
        String baseUrl = requestedUrl.substring(0, requestedUrl.indexOf(MOUNT_PATH));
        if (token != null && token.getStatus() == OpenIDAuthenticationStatus.SUCCESS) {
            System.out.println("OpenID login for " + token.getIdentityUrl());
            Authentication authentication = authenticationManager.authenticate(token);
            authentication.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String to =  baseUrl + "converter";
            return "redirect:" + to;
        }
        return "redirect:" + baseUrl + "/loginInfo";
    }

    @RequestMapping("/logout")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "logout";
    }

}
