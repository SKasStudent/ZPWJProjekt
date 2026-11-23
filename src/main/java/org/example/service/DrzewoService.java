package org.example.service;

import org.example.encje.Drzewo;
//import org.example.repository.DrzewoRepository;
import org.example.repository.jpa.DrzewoJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DrzewoService {

    private final DrzewoJpaRepository repo;

    public DrzewoService(DrzewoJpaRepository repo) {
        this.repo = repo;
    }

    public Drzewo add(Drzewo drzewo) {
        drzewo.setId(null);
        drzewo.setAktywny(true);
        return repo.save(drzewo);
    }

    public List<Drzewo> getAll() {
        return repo.findAll();
    }

    public Drzewo getOne(Long id) {
        return repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Nie znaleziono drzewa: " + id
                )
        );
    }

    public Drzewo update(Long id, Drzewo drzewo) {
        Drzewo istniejace = getOne(id);

        istniejace.setGatunek(drzewo.getGatunek());
        istniejace.setWysokosc(drzewo.getWysokosc());
        istniejace.setSrednica(drzewo.getSrednica());
        istniejace.setSciete(drzewo.isSciete());

        return repo.save(istniejace);
    }

    public void makeInactive(Long id) {
        Drzewo drzewo = getOne(id);
        drzewo.setAktywny(false);
        repo.save(drzewo);
    }

    public void delete(Long id) {
        Drzewo drzewo = getOne(id);
        repo.delete(drzewo);
    }

    public List<Drzewo> getActive() {
        return repo.findAll().stream()
                .filter(Drzewo::isAktywny)
                .toList();
    }

    public List<Drzewo> getByGatunek(String gatunek) {
        if (gatunek == null || gatunek.isBlank()) {
            throw new IllegalArgumentException(
                    "Gatunek nie moze byc pusty"
            );
        }

        return repo.findAll().stream()
                .filter(d -> d.getGatunek().equalsIgnoreCase(gatunek.trim()))
                .toList();
    }

    public List<Drzewo> getByMinWysokosc(double minWysokosc) {
        if (!Double.isFinite(minWysokosc) || minWysokosc < 0) {
            throw new IllegalArgumentException(
                    "Minimalna wysokosc musi byc liczba nieujemna"
            );
        }

        return repo.findAll().stream()
                .filter(d -> d.getWysokosc() >= minWysokosc)
                .toList();
    }
    
    public long countActive() {
        return repo.findAll().stream()
                .filter(Drzewo::isAktywny)
                .count();
    }

}
