package com.multishop.fusiontech.security;

import com.multishop.fusiontech.models.UserEntity;
import com.multishop.fusiontech.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity findUser = userRepository.findByEmail(username);

        if (findUser != null) {
            User user = new User(findUser.getEmail(), findUser.getPassword(),
                    findUser.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList());

            return user;
        } else {
            throw new UsernameNotFoundException("User with email " + username + " not found");
        }
    }
}
