package com.example.project.utilities;

import com.example.project.models.User;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService (UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public User register(User userToRegister){
        if (userRepository.existsByUsername(userToRegister.getUsername())) throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        userToRegister.setPassword(passwordEncoder.encode(userToRegister.getPassword()));
        if (userToRegister.getRoles() == null || userToRegister.getRoles().isEmpty()){
            log.info("New user registered");
            userToRegister.setRoles(Set.of(User.Role.DEFAULT_USER));
            log.info(userToRegister.getRoles().toString());
        }
        return userRepository.save(userToRegister);
    }
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Пользователя с таким именем не существует"));
    }
}
