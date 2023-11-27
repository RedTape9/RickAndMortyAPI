package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/rick-and-morty")

public class RickAndMortyController {
    @GetMapping("/id")
    public ResponseEntity<RickAndMortyCharacter> getCharacter(@RequestParam(required = false) Integer id) {
        // Überprüfen, ob die ID null oder ungültig ist
        if (id == null || id <= 0) {
            // Rückgabe einer angemessenen Antwort, z.B. Bad Request
            return ResponseEntity.badRequest().body(null);
        }

        try {
            RickAndMortyCharacter character = WebClient.create()
                    .get()
                    .uri("https://rickandmortyapi.com/api/character/" + id)
                    .retrieve()
                    .toEntity(RickAndMortyCharacter.class)
                    .block()
                    .getBody();

            return ResponseEntity.ok(character);
        } catch (Exception e) {
            // Behandlung anderer Fehler, z.B. wenn der Charakter nicht gefunden wird
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /*@GetMapping("/characters")
    public List<RickAndMortyCharacters> getAllCharacters() {
        RickAndMortyCharacters characters = Objects.requireNonNull(
                WebClient.create()
                        .get()
                        .uri("https://rickandmortyapi.com/api/character")
                        .retrieve()
                        .toEntity(RickAndMortyCharacters.class)
                        .block()
        ).getBody();
        return List.of(characters);
    }
*/

    @GetMapping("/characters")
    public List<RickAndMortyCharacter> getCharactersByStatus(@RequestParam(required = false) String status) {
        RickAndMortyCharacters characters = Objects.requireNonNull(
                WebClient.create()
                        .get()
                        .uri("https://rickandmortyapi.com/api/character" + (status != null ? "?status=" + status : ""))
                        .retrieve()
                        .toEntity(RickAndMortyCharacters.class)
                        .block()
        ).getBody();

        return characters != null ? characters.results() : Collections.emptyList();
    }
}