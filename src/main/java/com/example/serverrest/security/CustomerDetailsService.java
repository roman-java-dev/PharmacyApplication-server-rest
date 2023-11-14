package com.example.serverrest.security;

import com.example.serverrest.service.CustomerService;
import communication.CustomerThrift;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import static org.springframework.security.core.userdetails.User.withUsername;

@Service
@RequiredArgsConstructor
public class CustomerDetailsService implements UserDetailsService {
    private final CustomerService service;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CustomerThrift customer = service.getByEmail(email);
        UserBuilder builder = withUsername(email);
        builder.password(customer.getPassword());
        builder.roles(customer.getRole().name());
        return builder.build();
    }
}