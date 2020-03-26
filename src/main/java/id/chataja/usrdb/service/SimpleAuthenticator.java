/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.chataja.usrdb.service;

import id.chataja.usrdb.jpa.SqlConstants;
import id.chataja.usrdb.model.PartnerApp;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author ahmad
 */
@Service
public class SimpleAuthenticator {
    
    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private PartnerApp partnerApp;
    
    public boolean authenticate(String appId, String secretKey) {
        
        boolean bOk = appId.equals(partnerApp.getAppId()) && secretKey.equals(partnerApp.getAppSecret());
        
        if (! bOk) {
            
            lookupPartnerApp(partnerApp);
            
            bOk = appId.equals(partnerApp.getAppId()) && secretKey.equals(partnerApp.getAppSecret());;
            
        }
        
        return bOk;
        
    }

    private void lookupPartnerApp(PartnerApp app) {

        String findSql = String.format(SqlConstants.FIND_PARTNER_APP_SQL, app.getPartnerId());
                
        Map<String, Object> res = null;
        try {
            res = jdbc.queryForMap(findSql);
            
            app.setAppId(res.get("app_id").toString());
            app.setAppSecret(res.get("app_secret").toString());
        } catch (DataAccessException ex) {
            
        }

    }
    
    
}
