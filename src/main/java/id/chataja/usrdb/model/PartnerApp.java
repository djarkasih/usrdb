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
public class PartnerApp {
    
    private int id;
    private int partnerId;
    private final String appName;
    private String appId;
    private String appSecret;
    private boolean OtpRequired;

    public PartnerApp(String appName) {
        this.appName = appName;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public String getAppName() {
        return appName;
    }

    public String getAppId() {
        return appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public boolean isOtpRequired() {
        return OtpRequired;
    }

    public void setOtpRequired(boolean OtpRequired) {
        this.OtpRequired = OtpRequired;
    }

    @Override
    public String toString() {
        return "PartnerApp{" + "id=" + id + ", partnerId=" + partnerId + ", appName=" + appName + ", appId=" + appId + ", appSecret=" + appSecret + ", OtpRequired=" + OtpRequired + '}';
    }
    
}
