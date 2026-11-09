package org.example.dto;

import lombok.*;
import org.example.encje.Drzewo;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DrzewoDTO {
    private Long id;
    private String gatunek;
    private double wysokosc;
    private double srednica;
    private boolean sciete;
    private boolean aktywny = true;

    public DrzewoDTO(Long id, String gatunek, double wysokosc,
                     double srednica, boolean sciete) {
        this(id, gatunek, wysokosc, srednica, sciete, true);
    }

    public static DrzewoDTO mapuj(Drzewo drzewo) {
        return new DrzewoDTO(
                drzewo.getId(),
                drzewo.getGatunek(),
                drzewo.getWysokosc(),
                drzewo.getSrednica(),
                drzewo.isSciete(),
                drzewo.isAktywny()
        );
    }

    public Drzewo przywrocEncje() {
        return new Drzewo(id,gatunek,wysokosc,srednica,sciete,aktywny);
    }

}
