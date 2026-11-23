package org.example.repository.jpa;

import org.example.encje.Uzytkownik;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UzytkownikRepository
        extends JpaRepository<Uzytkownik, Long> {

    Optional<Uzytkownik> findByLogin(String login);
    boolean existsByLogin(String login);
}
