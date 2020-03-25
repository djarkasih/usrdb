/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.chataja.usrdb.service;

import id.chataja.usrdb.jpa.SqlConstants;
import id.chataja.usrdb.model.PartnerRecord;
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
    private PartnerRecord partnerRec;
    
    public boolean authenticate(String maskedPassword) {
        
        boolean bOk = maskedPassword.equals(partnerRec.getPassword());
        
        if (! bOk) {
            
            lookupPassword(partnerRec);
            
            bOk = maskedPassword.equals(partnerRec.getPassword());
            
        }
        
        return bOk;
        
    }

    private void lookupPassword(PartnerRecord rec) {

        String findSql = String.format(SqlConstants.FIND_PARTNER_SQL, rec.getEmail());
                
        Map<String, Object> res = null;
        try {
            res = jdbc.queryForMap(findSql);
            
            rec.setPassword(res.get("password_digest").toString());
        } catch (DataAccessException ex) {
            
        }

    }
    
    
}
