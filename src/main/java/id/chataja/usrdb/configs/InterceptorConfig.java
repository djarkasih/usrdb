/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.chataja.usrdb.configs;

import id.chataja.usrdb.controller.SecurityInterceptor;
import id.chataja.usrdb.service.SimpleAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author ahmad
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    
    @Autowired
    private SimpleAuthenticator simpleAuthenticator;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new SecurityInterceptor(simpleAuthenticator));

    }
    
}
