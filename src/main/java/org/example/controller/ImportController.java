package org.example.controller;

import org.example.clients.WoodClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/import")
public class ImportController {

    private final WoodClient client;

    public ImportController(WoodClient client) {
        this.client = client;
    }

    @PostMapping(value = "/woods", produces = MediaType.APPLICATION_JSON_VALUE)
    public String importWoods() throws IOException {
        return client.downloadAndSave();
    }
}
