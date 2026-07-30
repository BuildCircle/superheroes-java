package com.buildcircle.superheroes;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = SuperheroesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BattleIntegrationTests {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    private String createURLWithPort(String uri) {
        return "http://localhost:" + this.port + uri;
    }

    @Test
    void canGetHeroes() {
        //Given
        HttpEntity<String> entity = new HttpEntity<String>(null, new HttpHeaders());

        //When
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/battle?hero=Batman&villain=Joker"),
                HttpMethod.GET, entity, String.class);

        //Then
        assertTrue(response.getStatusCode() == HttpStatus.OK);
    }

}
