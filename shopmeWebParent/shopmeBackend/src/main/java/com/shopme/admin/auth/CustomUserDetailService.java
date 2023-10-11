package com.shopme.admin.auth;

import com.shopme.admin.user.repository.UserRepository;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            System.out.println("GettingUserDetails for ::"+username);
            User user = userRepository.findByEmail(username).get(0);
            System.out.println("User from DB :: "+user);
            return new ShopmeUserDetails(user);
        } catch (Exception e){
            e.printStackTrace();
            throw new UsernameNotFoundException("User not found :: "+username);
        }
    }
}
