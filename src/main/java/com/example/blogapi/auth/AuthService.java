package com.example.blogapi.auth;

import com.example.blogapi.Exceptions.BlogApiException;
import com.example.blogapi.role.RoleEntity;
import com.example.blogapi.role.RoleRepository;
import com.example.blogapi.user.UserEntity;
import com.example.blogapi.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {
    private AuthenticationManager authenticationManager;
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    public AuthService(AuthenticationManager authManager,UserRepository userepo,RoleRepository rolerepo,PasswordEncoder passwordencoder){
        this.authenticationManager=authManager;
        this.userRepository=userepo;
        this.roleRepository=rolerepo;
        this.passwordEncoder=passwordencoder;
    }
    public String login(LoginDTO loginDTO){
       Authentication authenticate=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(),loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return "User login successful";
    }

    public String register(RegisterDTO registerDTO){
        //check if username already exists
        if(userRepository.existsByUsername(registerDTO.getUsername())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"User already exists with username: "+registerDTO.getUsername());
        }
        //check if email is already in use.
        if(userRepository.existsByEmail((registerDTO.getEmail()))){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Email is already in use.");
        }
        UserEntity user=new UserEntity();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        Set<RoleEntity> roles=new HashSet<>();
        RoleEntity userRole=roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
        return "User registered successfully";
    }
}
