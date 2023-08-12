package com.example.blogapi.auth;

import com.example.blogapi.user.UserEntity;
import com.example.blogapi.user.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    UserRepository userRepository;
    public CustomUserDetailsService(UserRepository repo){
        this.userRepository=repo;
    }
    @Override
    public UserDetails loadUserByUsername(String UsernameOrEmail) throws UsernameNotFoundException {
        Optional<UserEntity> retrivedUser=userRepository.findByUsernameOrEmail(UsernameOrEmail,UsernameOrEmail);
        UserEntity user=retrivedUser.get();
        Set<GrantedAuthority>authorities=user.getRoles().stream().map((role)->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
        return new User(user.getEmail(),user.getPassword(),authorities);
    }
}
