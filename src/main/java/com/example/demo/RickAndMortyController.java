package com.example.demo;

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
    @GetMapping("/character")
    public RickAndMortyCharacter getCharacter() {
        RickAndMortyCharacter character = Objects.requireNonNull(
                WebClient.create()
                        .get()
                        .uri("https://rickandmortyapi.com/api/character/2")
                        .retrieve()
                        .toEntity(RickAndMortyCharacter.class)
                        .block()
        ).getBody();
        return character;

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
