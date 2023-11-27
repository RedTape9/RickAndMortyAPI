package com.example.demo;

import java.util.List;

public record RickAndMortyCharacter(
        int id,
        String name,
        /*RickAndMortyOrigin origin,
        List<String> episode,*/
        String species,
        String status

) {

}
