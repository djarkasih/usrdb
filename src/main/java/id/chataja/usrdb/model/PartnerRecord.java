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
    private final String name;
    private final String email;
    private String password;

    public PartnerRecord(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "PartnerRecord{" + "id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + '}';
    }

}
