package org.example.encje;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "pracownicy")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pracownik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imie;

    @Column(nullable = false)
    private String stanowisko;

    @Column(nullable = false)
    private double pensja;

    @Column(nullable = false)
    private boolean aktywny = true;

    public Pracownik(Long id, String imie, String stanowisko, double pensja) {
        this(id, imie, stanowisko, pensja, true);
    }
}