package com.findapickle.backend.services;

import com.findapickle.backend.entities.UserEntity;
import com.findapickle.backend.exceptions.EntityNotFoundException;
import com.findapickle.backend.exceptions.UnauthorizedException;
import com.findapickle.backend.models.User;
import com.findapickle.backend.repositories.UsersRepository;
import com.findapickle.backend.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UsersService implements UserDetailsService {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                new ArrayList<>());
    }

    public UserEntity save(String token, User user){
        if(!this.isAdmin(jwtTokenUtil.getIdFromToken(token))){
            throw new UnauthorizedException();
        }
        UserEntity newUser = new UserEntity();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userRepository.save(newUser);
    }

    public boolean isAdmin(Integer userId){
        return this.userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(userId, "User")).isAdmin();
    }
}
