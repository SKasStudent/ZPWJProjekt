package org.example.encje;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "uzytkownicy")
@Getter
@Setter
@NoArgsConstructor
public class Uzytkownik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String login;

    @Column(name= "haslo_hash", nullable = false, length = 255)
    private String hasloHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Rola rola = Rola.USER;
}
