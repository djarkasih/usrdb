/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.chataja.usrdb.controller;

import id.chataja.usrdb.error.UserManagerError;
import id.chataja.usrdb.error.UserManagerException;
import id.chataja.usrdb.service.SimpleAuthenticator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author ahmad
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    private final static String APP_ID_HEADER = "AppId";
    private final static String SECRET_KEY_HEADER = "SecretKey";
    
    private static final Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);

    private final SimpleAuthenticator authenticator;
    
    public SecurityInterceptor(SimpleAuthenticator authenticator) {
        this.authenticator = authenticator;
    }
    
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        
        String appId = req.getHeader(SecurityInterceptor.APP_ID_HEADER);
        if (appId == null)
            appId = "NOAPPID";
        
        String secretKey = req.getHeader(SecurityInterceptor.SECRET_KEY_HEADER);
        if (appId == null)
            appId = "NOSECRETKEY";

        if (! this.authenticator.authenticate(appId,secretKey)) {
            throw new UserManagerException(UserManagerError.INVALID_CREDENTIAL,"Invalid or missing credential.");
        }
        
        return true;
    
    }
    
}
