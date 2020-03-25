/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.chataja.usrdb.jpa;

import id.chataja.usrdb.model.domain.User;
import org.springframework.data.repository.Repository;

/**
 *
 * @author ahmad
 */
public interface UserRepository extends Repository<User,Integer> {

    public boolean existsByMobileNumber(String mobileNumber);
    public User findByMobileNumber(String mobileNumber);
    public User save(User user);
    
}
