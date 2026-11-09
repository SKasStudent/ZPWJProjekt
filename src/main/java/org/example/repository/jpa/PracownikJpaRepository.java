package org.example.repository.jpa;

import org.example.encje.Pracownik;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PracownikJpaRepository extends JpaRepository<Pracownik, Long> {
}
