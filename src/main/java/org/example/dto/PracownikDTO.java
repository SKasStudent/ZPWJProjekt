package org.example.dto;

import lombok.*;
import org.example.encje.Pracownik;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PracownikDTO {
    private Long id;
    private String imie;
    private String stanowisko;
    private double pensja;

    public static PracownikDTO mapuj(Pracownik p) {
        return new PracownikDTO(
                p.getId(),
                p.getImie(),
                p.getStanowisko(),
                p.getPensja()
        );
    }

    public Pracownik przywrocEncje() {
        return new Pracownik(id, imie, stanowisko, pensja);
    }
}