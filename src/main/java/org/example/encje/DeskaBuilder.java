package org.example.encje;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeskaBuilder {
    private Long id;
    private String gatunekDrewna;
    private double dlugosc;
    private double szerokosc;
    private double grubosc;
}
