package org.example.controller;

import org.example.dto.RejestracjaRequest;
import org.example.service.UzytkownikService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UzytkownikService service;

    public AuthController(UzytkownikService service) {
        this.service = service;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody RejestracjaRequest request) {
        service.zarejestruj(request);
    }
}
