/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.chataja.usrdb.configs;

import id.chataja.usrdb.model.PartnerApp;
import id.chataja.usrdb.model.PartnerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author ahmad
 */
@Configuration
public class Manager {
    
    private final Logger logger = LoggerFactory.getLogger(Manager.class);
    
    private final PartnerRecord partnerRecord = new PartnerRecord("UMeetMe","admin@umeetme.id");
    private final PartnerApp partnerApp = new PartnerApp("ShareUserBase");
    
    @Bean
    public Logger getLogger() {
        return logger;
    }

    @Bean
    public PartnerRecord getPartnerRecord() {
        return partnerRecord;
    }
    
    @Bean
    public PartnerApp getPartnerApp() {
        return partnerApp;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
