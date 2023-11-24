package ru.skypro.homework.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.skypro.homework.dto.authdto.Role;
import ru.skypro.homework.entity.User;

import java.util.*;

@Getter
public class TradingUserDetails implements UserDetails {
    private final long id;
    private final String email;
    private final String password;
    private final Role role;

    public TradingUserDetails(long id, String email, String password, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = new HashSet<>();
        roles.add(this.role);
        return roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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

    public static TradingUserDetails from(User user) {
        return new TradingUserDetails(user.getId(), user.getEmail(), user.getPassword(), user.getRole());
    }
}
