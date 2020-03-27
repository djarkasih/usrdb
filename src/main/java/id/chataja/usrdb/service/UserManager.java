/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.chataja.usrdb.service;

import id.chataja.usrdb.jpa.UserRepository;
import id.chataja.usrdb.model.PartnerApp;
import id.chataja.usrdb.model.Rules;
import id.chataja.usrdb.model.SimpleUser;
import id.chataja.usrdb.model.domain.User;
import java.time.LocalDateTime;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 *
 * @author ahmad
 */
@Service
@Validated
public class UserManager {
    
    private static final String INSERT_USER_ROLES_SQL = "insert into user_roles(user_id,role_id,created_at,updated_at) values(%d,%d,now(),now())";
    private static final String INSERT_USER_EMBED_SQL = "insert into user_embed_chats(user_id,embed_app_id,created_at,updated_at) values(%d,%d,now(),now())";
    
    @Autowired
    private Logger logger;
    
    @Autowired
    private JdbcTemplate jdbc;
    
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PartnerApp partnerApp;
    
    public boolean existsByMobileNumber(String mobileNumber) {
        
        return userRepo.existsByMobileNumber(mobileNumber);
        
    }

    public SimpleUser findByMobileNumber(String mobileNumber) {
        
        SimpleUser simpleUser = null;
        
        User user = userRepo.findByMobileNumber(mobileNumber);
        if (user != null) {
            
            simpleUser = new SimpleUser();
            simpleUser.setMobileNumber(mobileNumber);
            simpleUser.setFullname(user.getFullname());
            if (user.getEmail() != null) {
                simpleUser.setEmail(user.getEmail());
            }
            
        }
        
        return simpleUser;
        
    }
    
    private User saveUser(User prevData, SimpleUser simpleUser) {
        
        User user = new User();
        
        user.setMobileNumber(simpleUser.getMobileNumber());
        user.setFullname(simpleUser.getFullname());
        user.setEmail(simpleUser.getEmail());
        if (prevData == null) {
            //new user
            user.setAppId(Rules.CHATAJA_APP_ID);
            user.setQiscusToken("YOUSHOULDNOTREADTHIS");
            user.setQiscusEmail(Rules.buildQiscusEmail(user));
            user.setCreatedAt(LocalDateTime.now());
        } else {
            //existing user
            user.setId(prevData.getId());
            user.setAppId(prevData.getAppId());
            user.setQiscusToken(prevData.getQiscusToken());
            user.setQiscusEmail(prevData.getQiscusEmail());
            user.setCreatedAt(prevData.getCreatedAt());
        }
        user.setUpdatedAt(LocalDateTime.now());

        User savedUser = null;
        try {
            
            savedUser = userRepo.save(user);
            
        } catch (Exception ex) {
            
        }

        return savedUser;
        
    }
    
    private void saveUserDetail(User user) {
        
        try {
            
            String sql = String.format(INSERT_USER_ROLES_SQL, user.getId(), Rules.MEMBER_ROLE_ID);
            jdbc.update(sql);
            
        } catch (Exception ex) {
            
        }
        
        try {
            
            String sql = String.format(INSERT_USER_EMBED_SQL, user.getId(), partnerApp.getId());
            jdbc.update(sql);
            
        } catch (Exception ex) {
            
        }

    }
    
    public SimpleUser save(@Valid SimpleUser simpleUser) {
        
        boolean changeRequired;
        
        SimpleUser newUser = null;
        
        User user = null;
        
        //if the mobile number already listed in chataja db, no change is required
        if (userRepo.existsByMobileNumber(simpleUser.getMobileNumber())) {
            user = userRepo.findByMobileNumber(simpleUser.getMobileNumber());
            changeRequired = false;
        } else {
            changeRequired = true;
        }
        
        if (user != null) {
            //but if either the stored email or fullname is different than what input provided, change is required.
            changeRequired = (! simpleUser.getEmail().equals(user.getEmail())) ||
                             (! simpleUser.getFullname().equals(user.getFullname()));
        }
        
        if (changeRequired) {
            user = this.saveUser(user,simpleUser);
        }
        
        if (user != null) {
            
            this.saveUserDetail(user);
            
            newUser = simpleUser;
            
        }
        
        return newUser;
        
    }
    
}
