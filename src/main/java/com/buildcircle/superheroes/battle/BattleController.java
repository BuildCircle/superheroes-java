package com.buildcircle.superheroes.battle;
import com.buildcircle.superheroes.characters.CharacterResponse;
import com.buildcircle.superheroes.characters.CharactersProvider;
import com.buildcircle.superheroes.characters.CharactersResponse;
import tools.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class BattleController {

    @Autowired
    private final CharactersProvider charactersProvider;

    @Autowired
    private ObjectMapper objectMapper;

    private static CharacterResponse character1;
    private static CharacterResponse character2;

    public BattleController(CharactersProvider charactersProvider, ObjectMapper objectMapper)
    {
        this.objectMapper = objectMapper;
        this.charactersProvider = charactersProvider;
    }

    @GetMapping("/battle")
    public ResponseEntity<String> battle(@RequestParam(value = "hero") final String hero,
                                         @RequestParam(value = "villain") final String villain) throws IOException, InterruptedException {

        CharactersResponse characters = charactersProvider.getCharacters();

        for (CharacterResponse character : characters.getItems()) {
            if(character.getName().equals(hero))
            {
                character1 = character;
            }

            if(character.getName().equals(villain))
            {
                character2 = character;
            }
        }

        if(character1.getScore() > character2.getScore())
        {
            return ResponseEntity.ok(objectMapper.writeValueAsString(character1));
        }

        return ResponseEntity.ok(objectMapper.writeValueAsString(character2));
    }

}
