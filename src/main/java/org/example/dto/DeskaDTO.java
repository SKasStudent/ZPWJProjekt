package org.example.dto;

import lombok.*;
import org.example.encje.Deska;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeskaDTO {
    private Long id;
    private String gatunekDrewna;
    private double dlugosc;
    private double szerokosc;
    private double grubosc;

    public static DeskaDTO mapuj(Deska d) {
        return new DeskaDTO(
                d.getId(),
                d.getGatunekDrewna(),
                d.getDlugosc(),
                d.getSzerokosc(),
                d.getGrubosc()
        );
    }

    public Deska przywrocEncje() {
        return new Deska(
                id, gatunekDrewna, dlugosc, szerokosc, grubosc
        );
    }
}