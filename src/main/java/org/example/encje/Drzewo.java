package org.example.encje;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Drzewo {
    private Long id;
    private String gatunek;
    private double wysokosc;
    private double srednica;
    private boolean sciete;
}
