/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.chataja.usrdb.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 *
 * @author ahmad
 */
public class ItemEnvelope extends Envelope{
    
    @JsonInclude(Include.NON_NULL)
    private Object payload;

    public ItemEnvelope() {
        super();
    }

    public ItemEnvelope(boolean success, int code, String message) {
        super(success, code);
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
    
}
