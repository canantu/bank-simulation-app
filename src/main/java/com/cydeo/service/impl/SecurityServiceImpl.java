package com.cydeo.service.impl;

import com.cydeo.entity.User;
import com.cydeo.entity.common.UserPrincipal;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //TASK
        //we need to get our own user from database. how ?
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Username does not exist!"));

        //return some exception if user does not exist
       /* if (user == null) {
            throw new UsernameNotFoundException("Username: " + user + " not exist!");
        }
        */

        //return user information as a UserDetails

        return new UserPrincipal(user);
    }
}
