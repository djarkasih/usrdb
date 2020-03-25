/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.chataja.usrdb.model;

import id.chataja.usrdb.model.domain.User;

/**
 *
 * @author ahmad
 */
public class Rules {
    
    public static Integer MEMBER_ROLE_ID = 2;
    public static Integer CHATAJA_APP_ID = 3;
    public static String QISCUS_SDK_APP_ID = "kiwari-prod";
    
    public static String buildQiscusEmail(User user) {
        //userid_46955_6282213620002@kiwari-prod.com
        String email = "userid_" +
               user.getId() + "_" +
               user.getMobileNumber() + "@" +
               QISCUS_SDK_APP_ID;
        
        if (email.contains("+"))
            email = email.replace("+", "");
        
        if (!email.contains(".com")) {
            email = email + ".com";
        }
        
        return email;
    }
    
}
