package org.example.encje;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pracownik {
    private Long id;
    private String imie;
    private String stanowisko;
    private double pensja;
}
