package org.example.controller;

import org.example.encje.Drzewo;
import org.example.service.DrzewoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/drzewa")
public class DrzewoController {

    private final DrzewoService service;

    public DrzewoController(DrzewoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Drzewo> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Drzewo getOne(@PathVariable Long id) {
        return service.getOne(id);
    }

    @PostMapping
    public ResponseEntity<Drzewo> add(@RequestBody Drzewo drzewo) {
        Drzewo saved = service.add(drzewo);
        return ResponseEntity
                .created(URI.create("/api/drzewa/" + saved.getId()))
                .body(saved);
    }

    @PutMapping("/{id}")
    public Drzewo update(@PathVariable Long id, @RequestBody Drzewo drzewo) {
        return service.update(id, drzewo);
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
