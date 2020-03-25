/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.chataja.usrdb.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ahmad
 */
public class DataSourceBuilder {
    
    private static Logger logger = LoggerFactory.getLogger(DataSourceBuilder.class);
        
    public static DataSource build(String jdbcUrl, String jdbcUser, String jdbcPassword) throws java.sql.SQLException, java.sql.SQLNonTransientConnectionException {
        
        DataSource ds = null;
        
        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(jdbcUrl);
        cfg.setUsername(jdbcUser);
        cfg.setPassword(jdbcPassword);
        
        try {
            ds = new HikariDataSource(cfg);
        } catch (Exception ex) {
            logger.info("build error : " + ex.getClass().getCanonicalName());
            throw ex;
        }
                
        return ds;
        
    }

}
