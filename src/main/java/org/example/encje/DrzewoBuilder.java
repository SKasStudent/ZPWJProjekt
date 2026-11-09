package org.example.encje;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DrzewoBuilder {
    @NonNull
    private Long id;
    private String gatunek;
    private double wysokosc;
    private double srednica;
    private boolean sciete;
}
