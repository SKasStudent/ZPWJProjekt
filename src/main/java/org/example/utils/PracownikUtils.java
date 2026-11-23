package org.example.utils;

import org.example.encje.Pracownik;

import java.util.List;

public class PracownikUtils {
    public boolean czyAktywny(Pracownik pracownik) {
        sprawdzPracownika(pracownik);
        return pracownik.isAktywny();
    }

    public boolean czyWysokaPensja(Pracownik pracownik, double prog) {
        sprawdzPracownika(pracownik);
        sprawdzLiczbe(prog);
        return pracownik.getPensja() > prog;
    }

    public boolean czyPensjaCoNajmniej(Pracownik pracownik, double minimum) {
        sprawdzPracownika(pracownik);
        sprawdzLiczbe(minimum);
        return pracownik.getPensja() >= minimum;
    }

    public boolean czyStanowisko(Pracownik pracownik, String stanowisko) {
        sprawdzPracownika(pracownik);

        if (stanowisko == null || stanowisko.isBlank()) {
            throw new IllegalArgumentException(
                    "Stanowisko nie moze byc puste"
            );
        }

        return pracownik.getStanowisko()
                .equalsIgnoreCase(stanowisko.trim());
    }

    public int porownajPensje(Pracownik pierwszy, Pracownik drugi) {
        sprawdzPracownika(pierwszy);
        sprawdzPracownika(drugi);

        return Double.compare(
                pierwszy.getPensja(),
                drugi.getPensja()
        );
    }

    public double roznicaPensji(Pracownik pierwszy, Pracownik drugi) {
        sprawdzPracownika(pierwszy);
        sprawdzPracownika(drugi);

        return Math.abs(
                pierwszy.getPensja() - drugi.getPensja()
        );
    }

    public double pensjaPoPodwyzce(Pracownik pracownik, double procent) {
        sprawdzPracownika(pracownik);
        sprawdzLiczbe(procent);

        return pracownik.getPensja() * (1 + procent / 100);
    }

    public long policzAktywnych(List<Pracownik> pracownicy) {
        sprawdzListe(pracownicy);

        return pracownicy.stream()
                .filter(Pracownik::isAktywny)
                .count();
    }

    public double sredniaPensja(List<Pracownik> pracownicy) {
        sprawdzListe(pracownicy);

        if (pracownicy.isEmpty()) {
            throw new IllegalArgumentException(
                    "Lista pracownikow nie moze byc pusta"
            );
        }

        return pracownicy.stream()
                .mapToDouble(Pracownik::getPensja)
                .average()
                .orElseThrow();
    }

    public Pracownik najlepiejZarabiajacy(List<Pracownik> pracownicy) {
        sprawdzListe(pracownicy);

        if (pracownicy.isEmpty()) {
            throw new IllegalArgumentException(
                    "Lista pracownikow nie moze byc pusta"
            );
        }

        return pracownicy.stream()
                .max((a, b) -> Double.compare(
                        a.getPensja(),
                        b.getPensja()
                ))
                .orElseThrow();
    }

    private void sprawdzPracownika(Pracownik pracownik) {
        if (pracownik == null) {
            throw new IllegalArgumentException(
                    "Pracownik nie moze byc null"
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

    private void sprawdzListe(List<Pracownik> pracownicy) {
        if (pracownicy == null || pracownicy.contains(null)) {
            throw new IllegalArgumentException(
                    "Lista pracownikow jest niepoprawna"
            );
        }
    }
}
