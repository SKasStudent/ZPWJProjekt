package org.example.encje;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "drzewa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Drzewo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String gatunek;

    @Column(nullable = false)
    private double wysokosc;

    @Column(nullable = false)
    private double srednica;

    @Column(nullable = false)
    private boolean sciete;

    @Column(nullable = false)
    private boolean aktywny = true;

    public Drzewo(Long id, String gatunek, double wysokosc,
                  double srednica, boolean sciete) {
        this(id, gatunek, wysokosc, srednica, sciete, true);
    }
}