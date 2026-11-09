package org.example.encje;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PracownikBuilder {
    private Long id;
    private String imie;
    private String stanowisko;
    private double pensja;
}
