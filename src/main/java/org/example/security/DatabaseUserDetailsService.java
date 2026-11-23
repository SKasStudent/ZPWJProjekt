package org.example.security;

import org.example.encje.Uzytkownik;
import org.example.repository.jpa.UzytkownikRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {
    private final UzytkownikRepository repository;
    public DatabaseUserDetailsService(UzytkownikRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Uzytkownik uzytkownik = repository.findByLogin(login)
                .orElseThrow(() ->
                        new UsernameNotFoundException("nie znaleziono"));
        return User.builder()
                .username(uzytkownik.getLogin())
                .password(uzytkownik.getHasloHash())
                .roles(uzytkownik.getRola().name())
                .build();
    }


}
