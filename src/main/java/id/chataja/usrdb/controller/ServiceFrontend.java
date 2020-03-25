/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.chataja.usrdb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.chataja.usrdb.error.UserManagerError;
import id.chataja.usrdb.error.UserManagerException;
import id.chataja.usrdb.model.Envelope;
import id.chataja.usrdb.model.ItemEnvelope;
import id.chataja.usrdb.model.SimpleUser;
import id.chataja.usrdb.service.UserManager;
import id.chataja.usrdb.utils.MapUtils;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ahmad
 */
@RestController
public class ServiceFrontend {
    
    //private final static Set<String> MANDATORY_KEYS = Set.of("mobileNumber","fullname","email");
    private final static Set<String> MANDATORY_KEYS = Set.of("mobileNumber","fullname");
        
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    UserManager userMgr;

    @GetMapping(value="/users/{mobileNumber}")
    public ResponseEntity<Envelope> findUser(@PathVariable("mobileNumber") String mobileNumber) {
        
        ItemEnvelope item = new ItemEnvelope();
        item.setSuccess(true);
        
        HttpStatus status = HttpStatus.I_AM_A_TEAPOT;

        SimpleUser user = userMgr.findByMobileNumber(mobileNumber);
        if (user == null) {
            status = HttpStatus.NOT_FOUND;
            item.setCode(status.value());
        } else {
            status = HttpStatus.OK;
            item.setCode(status.value());
            item.setPayload(user);
        }

        return new ResponseEntity(item,status);

    }
    
    @PostMapping(value="/users")
    public ResponseEntity<Envelope> saveUser(@RequestBody Map<String,Object> rec) throws UserManagerException {

        Set<String> missingFields = MapUtils.getMissingFields(rec, MANDATORY_KEYS);
        
        if (missingFields.size() > 0) {
            throw new UserManagerException(UserManagerError.DATA_INCOMPLETE,"Missing field(s) : " + missingFields.toString() + ".");
        }

        SimpleUser inp = mapper.convertValue(rec, SimpleUser.class);

        ItemEnvelope item = new ItemEnvelope();
        item.setSuccess(true);
        
        HttpStatus status = HttpStatus.OK;
        if (! userMgr.existsByMobileNumber(inp.getMobileNumber()))
            status = HttpStatus.CREATED;
        
        SimpleUser out = userMgr.save(inp);
        if (out == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            item.setSuccess(false);
        }
        
        item.setCode(status.value());
        item.setPayload(out);
                
        return new ResponseEntity(item,status);

    }
    
}
