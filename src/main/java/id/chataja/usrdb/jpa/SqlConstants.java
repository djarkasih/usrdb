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
    public static String INSERT_PARTNER_SQL = "insert into partner_embed_chats(email,password_digest,created_at,updated_at) values ('%s','%s',now(),now())";

}
