package org.example.clients;

import org.example.security.VigenereDecryption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class WoodClient {
    private final RestClient restClient;

    @Value("${wood.server.url}")
    private String serverUrl;

    @Value("${vigenere.key}")
    private String encryptionKey;

    @Value("${wood.import.file:WoodCatalogue-imported.json}")
    private String outputFile;

    public WoodClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public String downloadAndSave() throws IOException {
        String encrypted = restClient.get()
                .uri(serverUrl + "/api/woods")
                .retrieve()
                .body(String.class);

        if (encrypted == null || encrypted.isBlank()) {
            throw new IllegalStateException("Serwer zwrócił pustą odpowiedź");
        }

        String decryptedJson = VigenereDecryption.decrypt(encrypted, encryptionKey);
        Files.writeString(Path.of(outputFile), decryptedJson, StandardCharsets.UTF_8);
        return decryptedJson;
    }
}
