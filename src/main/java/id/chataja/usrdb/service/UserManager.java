/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.chataja.usrdb.service;

import id.chataja.usrdb.jpa.UserRepository;
import id.chataja.usrdb.model.PartnerRecord;
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
    private static final String INSERT_USER_PARTNER_SQL = "insert into public.user_partner_embed_chats(user_id,partner_embed_id,created_at,updated_at) values(%d,%d,now(),now())";
    
    @Autowired
    private Logger logger;
    
    @Autowired
    private JdbcTemplate jdbc;
    
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PartnerRecord partnerRecord;
    
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
    
    private User saveUser(SimpleUser simpleUser) {
        
        User user = new User();
        
        user.setMobileNumber(simpleUser.getMobileNumber());
        user.setFullname(simpleUser.getFullname());
        if (simpleUser.getEmail() != null) {
            user.setEmail(simpleUser.getEmail());
        }
        user.setAppId(Rules.CHATAJA_APP_ID);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setQiscusToken("YOUSHOULDNOTREADTHIS");
        user.setQiscusEmail(Rules.buildQiscusEmail(user));

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
            
            String sql = String.format(INSERT_USER_PARTNER_SQL, user.getId(), partnerRecord.getId());
            jdbc.update(sql);
            
        } catch (Exception ex) {
            
        }

    }
    
    public SimpleUser save(@Valid SimpleUser simpleUser) {
        
        SimpleUser newUser = null;
        
        User user = null;
        if (userRepo.existsByMobileNumber(simpleUser.getMobileNumber())) {
            user = userRepo.findByMobileNumber(simpleUser.getMobileNumber());
        } else {
            user = this.saveUser(simpleUser);
        }
        
        if (user != null) {
            
            this.saveUserDetail(user);
            
            newUser = simpleUser;
            
        }
        
        return newUser;
        
    }
    
}
