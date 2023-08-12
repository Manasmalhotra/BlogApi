package com.example.blogapi.config;

import com.example.blogapi.auth.CustomUserDetailsService;
import com.example.blogapi.auth.JwtAuthenticationFilter;
import com.example.blogapi.auth.JwtEntryPoint;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@SecurityScheme(
        name="Bearer Authentication",
        type= SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme="bearer"

)
public class SecurityConfig{
    CustomUserDetailsService userService;
    JwtEntryPoint jwtAuthenticationEntryPoint;
    JwtAuthenticationFilter jwtAuthenticationFilter;
    public SecurityConfig(CustomUserDetailsService uds, JwtEntryPoint jwtauth, JwtAuthenticationFilter authFilter){

        this.userService=uds;
        this.jwtAuthenticationEntryPoint=jwtauth;
        this.jwtAuthenticationFilter=authFilter;
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
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated())
                        .exceptionHandling(
                                exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                        .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
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
