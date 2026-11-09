package org.example.encje;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Drzewo {
    @NonNull
    private Long id;
    private String gatunek;
    private double wysokosc;
    private double srednica;
    private boolean sciete;
    private boolean aktywny = true;

    public Drzewo(Long id, String gatunek, double wysokosc,
                  double srednica, boolean sciete) {
        this(id, gatunek, wysokosc, srednica, sciete, true);
    }
}