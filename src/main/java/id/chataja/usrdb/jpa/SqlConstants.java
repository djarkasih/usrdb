/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.chataja.usrdb.jpa;

/**
 *
 * @author ahmad
 */
public class SqlConstants {
    
    public static String FIND_PARTNER_SQL = "select * from partner_embed_chats where email = '%s'";
    public static String INSERT_PARTNER_SQL = "insert into partner_embed_chats(name,email,password_digest,created_at,updated_at) values ('%s','%s','%s',now(),now())";
    public static String FIND_PARTNER_APP_SQL = "select * from embed_chat_apps where partner_embed_id = '%d'";
    public static String INSERT_PARTNER_APP_SQL = "insert into embed_chat_apps (partner_embed_id,app_name,app_id,app_secret,is_otp,created_at,updated_at) values ('%d','%s','%s','%s','%b',now(),now())";

}
