package org.example.service;

import org.example.encje.Drzewo;
import org.example.repository.DrzewoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DrzewoService {

    private final DrzewoRepository repo;

    public DrzewoService(DrzewoRepository repo) {
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
        if (repo.update(id, drzewo) == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Nie znaleziono drzewa: " + id
            );
        }
        return getOne(id);
    }

    public void makeInactive(Long id) {
        if (repo.makeInactive(id) == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Nie znaleziono drzewa: " + id
            );
        }
    }

    public void delete(Long id) {
        if (repo.delete(id) == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Nie znaleziono drzewa: " + id
            );
        }
    }
}
