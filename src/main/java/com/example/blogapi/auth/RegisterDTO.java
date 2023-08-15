package com.example.blogapi.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description="Data Model for registering new users")
public class RegisterDTO {
    @Schema(description="User can choose a username, it should be unique")
    String username;
    @Schema(description="Email id for the user, it should be unique")
    @Email
    String email;
    @Schema(description="User password")
    String password;


}
