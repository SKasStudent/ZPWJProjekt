package org.example.service;

import org.example.encje.Pracownik;
import org.example.repository.PracownikRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PracownikService {

    private final PracownikRepository repo;

    public PracownikService(PracownikRepository repo) {
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
        if (repo.update(id, pracownik) == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Nie znaleziono pracownika: " + id
            );
        }
        return getOne(id);
    }

    public void makeInactive(Long id) {
        if (repo.makeInactive(id) == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Nie znaleziono pracownika: " + id
            );
        }
    }

    public void delete(Long id) {
        if (repo.delete(id) == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Nie znaleziono pracownika: " + id
            );
        }
    }
}
