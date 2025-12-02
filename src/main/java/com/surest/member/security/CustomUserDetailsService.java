package com.surest.member.security;

import com.surest.member.entity.UserEntity;
import com.surest.member.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    public CustomUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        String roleName = user.getRole().getName();

        var authority = new SimpleGrantedAuthority("ROLE_" + roleName);

        return new User(
                user.getUsername(),
                user.getPasswordHash(),   // field is passwordHash now
                java.util.List.of(authority)
        );
    }
}