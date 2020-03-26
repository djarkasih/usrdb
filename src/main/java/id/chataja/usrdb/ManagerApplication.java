package id.chataja.usrdb;

import id.chataja.usrdb.jpa.SqlConstants;
import id.chataja.usrdb.model.PartnerApp;
import id.chataja.usrdb.model.PartnerRecord;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ManagerApplication {
    
    private final String DEFAULT_PASSWORD = "UMEETME_CLIENT_PASSWORD";
    private final String DEFAULT_APP_ID = "umeetmeAdm";
    private final String DEFAULT_APP_SECRET = "umeetmeAdmKey";
    
    @Autowired
    private Environment env;

    @Autowired
    private Logger logger;
    
    @Autowired
    private JdbcTemplate jdbc;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PartnerRecord partnerRecord;
    
    @Autowired
    private PartnerApp partnerApp;

    private void createUmeetMePartnerRec(PartnerRecord rec) {
        
        String findSql = String.format(SqlConstants.FIND_PARTNER_SQL, rec.getEmail());
                
        Map<String, Object> res = null;
        try {
            res = jdbc.queryForMap(findSql);
        } catch (DataAccessException ex) {
            
        }
        
        if ((res == null) || (res.isEmpty())) {
            
            String insertSql = String.format(SqlConstants.INSERT_PARTNER_SQL, rec.getName(),rec.getEmail(),passwordEncoder.encode(this.DEFAULT_PASSWORD));
            int numOfRows = jdbc.update(insertSql);
            if (numOfRows == 1) {
                res = jdbc.queryForMap(findSql);
            }
            
        }

        int id = -1;
        try {
            id = Integer.parseInt(res.get("id").toString());
        } catch (Exception ex) {
            
        }
        
        rec.setId(id);
        rec.setPassword(res.get("password_digest").toString());
        
    }

    private void createUmeetMePartnerApp(PartnerApp app) {
        
        String findSql = String.format(SqlConstants.FIND_PARTNER_APP_SQL, app.getPartnerId());
                
        Map<String, Object> res = null;
        try {
            res = jdbc.queryForMap(findSql);
        } catch (DataAccessException ex) {
            
        }
        
        if ((res == null) || (res.isEmpty())) {
            
            app.setAppId(this.DEFAULT_APP_ID);
            app.setOtpRequired(false);
            
            String insertSql = String.format(
                SqlConstants.INSERT_PARTNER_APP_SQL, 
                app.getPartnerId(),
                app.getAppName(),
                app.getAppId(),
                passwordEncoder.encode(this.DEFAULT_APP_SECRET),
                app.isOtpRequired()
            );
            int numOfRows = jdbc.update(insertSql);
            if (numOfRows == 1) {
                res = jdbc.queryForMap(findSql);
            }
            
        }

        int id = -1;
        try {
            id = Integer.parseInt(res.get("id").toString());
        } catch (NumberFormatException ex) {
            
        }
        
        app.setId(id);
        app.setAppSecret(res.get("app_secret").toString());
        
    }

    public static void main(String[] args) {
	SpringApplication.run(ManagerApplication.class, args);
    }

    @Bean
    public CommandLineRunner initDatabaseInfo() {
        
        return (var args) -> {
            
            Set<String> profs = Set.of(env.getActiveProfiles());            
            logger.info("Active profiles = " + profs.toString());
                
            this.createUmeetMePartnerRec(this.partnerRecord);
            logger.info("partner record : " + this.partnerRecord);
            
            this.partnerApp.setPartnerId(this.partnerRecord.getId());
            this.createUmeetMePartnerApp(partnerApp);
            logger.info("partner app : " + this.partnerApp);

        };
        
    }

}
