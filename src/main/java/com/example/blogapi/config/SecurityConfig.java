package com.example.blogapi.config;

import com.example.blogapi.user.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.net.http.HttpRequest;

@Configuration
@EnableMethodSecurity
public class SecurityConfig{
    CustomUserDetailsService userService;
    public SecurityConfig(CustomUserDetailsService uds){
         this.userService=uds;
    }
    @Bean
    public static PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
             return authConfig.getAuthenticationManager();
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable()).authorizeHttpRequests((authorize)->
                //authorize.anyRequest().authenticated()).httpBasic(Customizer.withDefaults());
                authorize.requestMatchers(HttpMethod.GET,"/api/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/auth/**").permitAll()
                        .anyRequest().authenticated()).httpBasic(Customizer.withDefaults());
        return http.build();
    }

   /*
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails manas= User.builder()
                .username("Manas")
                .password(passwordEncoder().encode("Manas"))
                .roles("USER").build();
        UserDetails admin=User.builder()
                .username("Admin")
                .password(passwordEncoder().encode("Admin"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(manas,admin);
    }

    */
}
