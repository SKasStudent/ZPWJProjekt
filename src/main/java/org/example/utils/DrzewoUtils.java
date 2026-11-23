package org.example.utils;

import org.example.encje.Drzewo;

import java.util.List;

public class DrzewoUtils {

    public boolean czyAktywne(Drzewo drzewo) {
        sprawdzDrzewo(drzewo);
        return drzewo.isAktywny();
    }

    public boolean czySciete(Drzewo drzewo) {
        sprawdzDrzewo(drzewo);
        return drzewo.isSciete();
    }

    public boolean czyWyzszeNiz(Drzewo drzewo, double wysokosc) {
        sprawdzDrzewo(drzewo);
        sprawdzLiczbe(wysokosc);
        return drzewo.getWysokosc() > wysokosc;
    }

    public boolean czyGrubszeNiz(Drzewo drzewo, double srednica) {
        sprawdzDrzewo(drzewo);
        sprawdzLiczbe(srednica);
        return drzewo.getSrednica() > srednica;
    }

    public int porownajWysokosc(Drzewo pierwsze, Drzewo drugie) {
        sprawdzDrzewo(pierwsze);
        sprawdzDrzewo(drugie);

        return Double.compare(
                pierwsze.getWysokosc(),
                drugie.getWysokosc()
        );
    }

    public double roznicaWysokosci(Drzewo pierwsze, Drzewo drugie) {
        sprawdzDrzewo(pierwsze);
        sprawdzDrzewo(drugie);

        return Math.abs(
                pierwsze.getWysokosc() - drugie.getWysokosc()
        );
    }

    public boolean czyGatunek(Drzewo drzewo, String gatunek) {
        sprawdzDrzewo(drzewo);

        if (gatunek == null || gatunek.isBlank()) {
            throw new IllegalArgumentException(
                    "Gatunek nie moze byc pusty"
            );
        }

        return drzewo.getGatunek()
                .equalsIgnoreCase(gatunek.trim());
    }

    public long policzAktywne(List<Drzewo> drzewa) {
        sprawdzListe(drzewa);

        return drzewa.stream()
                .filter(Drzewo::isAktywny)
                .count();
    }

    public double sredniaWysokosc(List<Drzewo> drzewa) {
        sprawdzListe(drzewa);

        if (drzewa.isEmpty()) {
            throw new IllegalArgumentException(
                    "Lista drzew nie moze byc pusta"
            );
        }

        return drzewa.stream()
                .mapToDouble(Drzewo::getWysokosc)
                .average()
                .orElseThrow();
    }


    public Drzewo najwyzszeDrzewo(List<Drzewo> drzewa) {
        sprawdzListe(drzewa);

        if (drzewa.isEmpty()) {
            throw new IllegalArgumentException(
                    "Lista drzew nie moze byc pusta"
            );
        }

        return drzewa.stream()
                .max((a, b) -> Double.compare(
                        a.getWysokosc(),
                        b.getWysokosc()
                ))
                .orElseThrow();
    }

    private void sprawdzDrzewo(Drzewo drzewo) {
        if (drzewo == null) {
            throw new IllegalArgumentException(
                    "Drzewo nie moze byc null"
            );
        }
    }

    private void sprawdzLiczbe(double liczba) {
        if (!Double.isFinite(liczba) || liczba < 0) {
            throw new IllegalArgumentException(
                    "Wartosc musi byc liczba nieujemna"
            );
        }
    }

    private void sprawdzListe(List<Drzewo> drzewa) {
        if (drzewa == null || drzewa.contains(null)) {
            throw new IllegalArgumentException(
                    "Lista drzew jest niepoprawna"
            );
        }
    }
}
