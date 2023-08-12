package com.example.blogapi.auth;

import com.example.blogapi.article.ArticleEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@Tag(name="REST APIs for Authorization")
public class AuthController {
    AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
            summary="Login API",
            description="Login REST API is used to allow user to login into the system"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 Created"
    )
    @PostMapping({"/login","/signin"})
    public ResponseEntity<JwtAuthResponse>login(@RequestBody LoginDTO loginDTO){
        String token= authService.login(loginDTO);
        JwtAuthResponse jwtAuthResponse=new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @Operation(
            summary="Register API",
            description="Register REST API is used to allow user to register into the system"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 Created"
    )
    @PostMapping({"/register","/signup"})
    public ResponseEntity<String>register(@RequestBody  RegisterDTO registerDTO){
        String response=authService.register(registerDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



}
