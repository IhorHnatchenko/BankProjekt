package org.example.service;

import org.example.entities.Client;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ClientDetailService implements UserDetailsService {

    private final ClientService clientService;

    public ClientDetailService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client = clientService.getByEmail(email);
        if (client == null) {
            throw new UsernameNotFoundException("User with email " + email + " not found.");
        }

        return new User(client.getEmail(), client.getPassword(),
                Arrays.asList(new SimpleGrantedAuthority("client")));
    }
}
