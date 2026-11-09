package org.example.service;

import org.example.encje.Pracownik;
//import org.example.repository.PracownikRepository;
import org.example.repository.jpa.PracownikJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PracownikService {

    private final PracownikJpaRepository repo;

    public PracownikService(PracownikJpaRepository repo) {
        this.repo = repo;
    }

    public Pracownik add(Pracownik pracownik) {
        pracownik.setId(null);
        pracownik.setAktywny(true);
        return repo.save(pracownik);
    }

    public List<Pracownik> getAll() {
        return repo.findAll();
    }

    public Pracownik getOne(Long id) {
        return repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Nie znaleziono pracownika: " + id
                )
        );
    }

    public Pracownik update(Long id, Pracownik pracownik) {
        Pracownik istniejacy = getOne(id);

        istniejacy.setImie(pracownik.getImie());
        istniejacy.setStanowisko(pracownik.getStanowisko());
        istniejacy.setPensja(pracownik.getPensja());

        return repo.save(istniejacy);
    }

    public void makeInactive(Long id) {
        Pracownik pracownik = getOne(id);
        pracownik.setAktywny(false);
        repo.save(pracownik);
    }

    public void delete(Long id) {
        Pracownik pracownik = getOne(id);
        repo.delete(pracownik);
    }
}
