package org.example.repository.jpa;

import org.example.encje.Drzewo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrzewoJpaRepository extends JpaRepository<Drzewo, Long> {
}
