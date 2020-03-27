/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.chataja.usrdb.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 *
 * @author ahmad
 */
public class SimpleUser {
    
    @Size(min=10,message="mobileNumber should be at least 10 characters long")
    private String mobileNumber;
    @Size(min=4,message="fullname name should be at least 4 characters long")
    private String fullname;
    @NotEmpty
    @Email(message = "email Should be a valid email address")
    private String email;

    public SimpleUser() {
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
