package com.example.project.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    public enum Role {
        ADMIN, DEFAULT_USER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String username;
    String password;

    public User(String username, String password, String email, Set roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public User(){};

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLogin() { return username; }
    public void setLogin(String username) { this.username = username; }

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null) {
            return Collections.emptySet();
        }
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString()))
                .collect(Collectors.toSet());
    }

    public String getPassword() { return password; }

    @Override
    public String getUsername() {
        return username;
    }

    public void setPassword(String password) { this.password = password; }
    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }
}
