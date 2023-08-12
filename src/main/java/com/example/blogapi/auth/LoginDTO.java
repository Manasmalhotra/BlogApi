package com.example.blogapi.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description="Data Model for user to login")
public class LoginDTO {
    @Schema(
            description="User can enter either username or registered email id"
    )
    String usernameOrEmail;
    @Schema(
            description="User password"
    )
    String password;

}
