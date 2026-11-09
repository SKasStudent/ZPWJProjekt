package org.example.controller;

import org.example.encje.Pracownik;
import org.example.service.PracownikService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pracownicy")
public class PracownikController {

    private final PracownikService service;

    public PracownikController(PracownikService service) {
        this.service = service;
    }

    @GetMapping
    public List<Pracownik> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Pracownik getOne(@PathVariable Long id) {
        return service.getOne(id);
    }

    @PostMapping
    public ResponseEntity<Pracownik> add(@RequestBody Pracownik pracownik) {
        Pracownik saved = service.add(pracownik);
        return ResponseEntity
                .created(URI.create("/api/pracownicy/" + saved.getId()))
                .body(saved);
    }

    @PutMapping("/{id}")
    public Pracownik update(@PathVariable Long id, @RequestBody Pracownik pracownik) {
        return service.update(id, pracownik);
    }

    @PatchMapping("/{id}/inactive")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void makeInactive(@PathVariable Long id) {
        service.makeInactive(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
