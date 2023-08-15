package com.example.blogapi.user;

import com.example.blogapi.role.RoleEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class UserEntity {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(nullable=false,unique=true)
    String username;
    @Column(nullable=false,unique=true)
    @Email
    String email;
    @Column(nullable=false)
    String password;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    Set<RoleEntity> roles;
}
