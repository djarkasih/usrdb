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

    private final static String TOKEN_PREFIX = "Bearer ";
    private final static String AUTHORIZATION_HEADER = "Authorization";
    
    private static final Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);

    private final SimpleAuthenticator authenticator;
    
    public SecurityInterceptor(SimpleAuthenticator authenticator) {
        this.authenticator = authenticator;
    }
    
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        
        String password = "NOPASSWORD";
        
        String headerValue = req.getHeader(SecurityInterceptor.AUTHORIZATION_HEADER);
        if (headerValue != null)
            password = headerValue.replace(SecurityInterceptor.TOKEN_PREFIX, "");
        
        if (! this.authenticator.authenticate(password)) {
            throw new UserManagerException(UserManagerError.INVALID_CREDENTIAL,"Invalid or missing credential.");
        }
        
        return true;
    
    }
    
}
