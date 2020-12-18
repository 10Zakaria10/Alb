package com.theodo.albeniz.database.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "APP_USER")
@Data
@NoArgsConstructor
public class UserEntity implements UserDetails
{
    @Id
    @GenericGenerator(name="uuid",strategy = "uuid2")
    @Column(name = "ID")
    private UUID id;

    @Email
    @Column(name = "USER_NAME")
    private String username;

    @NotNull
    @Column(name = "PASSWORD")
    private String password;

    public UserEntity(UUID id, @Email String username, @NotNull String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
    public UserEntity(@Email String username, @NotNull String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
