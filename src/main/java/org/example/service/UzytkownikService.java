package org.example.service;

import org.example.dto.RejestracjaRequest;
import org.example.encje.Rola;
import org.example.encje.Uzytkownik;
import org.example.repository.jpa.UzytkownikRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UzytkownikService {

    private final UzytkownikRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UzytkownikService(UzytkownikRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void zarejestruj(RejestracjaRequest request) {

        if (request == null
                || request.login() == null
                || request.login().isBlank()
                || request.haslo() == null
                || request.haslo().length() < 8) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Login jest wymagany, a haslo musi miec minimum 8 znakow"
            );
        }

        String login = request.login().trim();

        if (repository.existsByLogin(login)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Uzytkownik o takim loginie juz istnieje"
            );
        }

        Uzytkownik uzytkownik = new Uzytkownik();

        uzytkownik.setLogin(login);

        uzytkownik.setHasloHash(passwordEncoder.encode(request.haslo())
        );

        uzytkownik.setRola(Rola.USER);

        repository.save(uzytkownik);
    }
}
