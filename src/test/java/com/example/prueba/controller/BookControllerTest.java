package com.example.prueba.controller;

import com.example.prueba.entitis.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate((restTemplateBuilder));
    }

    @DisplayName("Crear un libro")
    @Test
    void create() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                {
                        "title": "Libro creado desde spring test",
                        "author": "Alguien",
                        "pages": 385,
                        "description": "SpringTest",
                        "online": true
                    }
                """;

        HttpEntity<String> request = new HttpEntity<String>(json, headers);
        ResponseEntity<Book> response = testRestTemplate.exchange("/api/books", HttpMethod.POST, request, Book.class);
        Book result = response.getBody();

        assertEquals(1L, result.getId()); // Deberia crear el primer libro de la base de datos con un ID = 1
    }

    @DisplayName("Obtener todos los libros")
    @Test
    void findAll() {

        ResponseEntity<Book[]> response = testRestTemplate.getForEntity("/api/books", Book[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Book[] result = response.getBody();

        assert result != null; //no debe ser null
        assertEquals(1, result.length); // debe tener el libro creado en el test del metodo create
    }

    @DisplayName("Obtener un libro por medio de su ID")
    @Test
    void findById() {

        ResponseEntity<Book> response = testRestTemplate.getForEntity("/api/books/3", Book.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); //deberia devolver un status code not found (404) ya que no existe un libro con el id = 3

    }
}