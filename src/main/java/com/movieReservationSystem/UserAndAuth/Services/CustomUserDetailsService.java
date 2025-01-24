package com.movieReservationSystem.UserAndAuth.Services;

import com.movieReservationSystem.UserAndAuth.Entity.UserEntity;
import com.movieReservationSystem.UserAndAuth.Repository.UserRepo;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

        private final UserRepo userRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UserEntity user = userRepository.findByusername(username)
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "User not found with username: " + username));

                return org.springframework.security.core.userdetails.User
                                .withUsername(user.getUsername())
                                .password(user.getPassword())
                                .authorities(user.getRole().stream()
                                                .map(role -> new SimpleGrantedAuthority(role.name()))
                                                .collect(Collectors.toList()))
                                .build();
        }
}
