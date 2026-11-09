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

    public static DrzewoDTO mapuj(Drzewo drzewo) {
        return new DrzewoDTO(
                drzewo.getId(),
                drzewo.getGatunek(),
                drzewo.getWysokosc(),
                drzewo.getSrednica(),
                drzewo.isSciete()
        );
    }

    public Drzewo przywrocEncje() {
        return new Drzewo(id,gatunek,wysokosc,srednica,sciete);
    }

}
