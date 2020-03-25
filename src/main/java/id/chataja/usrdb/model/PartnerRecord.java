/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.chataja.usrdb.model;

/**
 *
 * @author ahmad
 */
public class PartnerRecord {
    
    private int id;
    private final String email;
    private final String defaultPassword;
    private String password;

    public PartnerRecord(String email, String defaultPassword) {
        this.email = email;
        this.defaultPassword = defaultPassword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getDefaultPassword() {
        return defaultPassword;
    }

    @Override
    public String toString() {
        return "PartnerRecord{" + "id=" + id + ", email=" + email + ", defaultPassword=" + defaultPassword + ", password=" + password + '}';
    }
    
}
