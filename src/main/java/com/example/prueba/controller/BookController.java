package com.example.prueba.controller;

import com.example.prueba.entitis.Book;
import com.example.prueba.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    private final BookRepository repository;

    private final Logger log = LoggerFactory.getLogger(BookController.class);

    public BookController(BookRepository repository) {

        this.repository = repository;
    }

    @GetMapping("/api/books")
    public List<Book> findAll() {

        return repository.findAll();
    }

    @GetMapping("/api/books/{id}")
    public ResponseEntity<Book> findById(@PathVariable Long id) {

        Optional<Book> bookOpt = repository.findById(id);

        if (bookOpt.isPresent()) return ResponseEntity.ok(bookOpt.get());
        else return ResponseEntity.notFound().build();
    }

    @PostMapping("/api/books")
    public ResponseEntity<Book> create(@RequestBody Book book, @RequestHeader HttpHeaders headers) {

        if (book.getId() != null) {
            log.warn("Trying to create a book with ID, use PUT method to update a book");
            return ResponseEntity.badRequest().build();
        }

        System.out.println(headers.get("User-Agent")); // Obtener el UserAgent
        Book result = repository.save(book);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/api/books")
    public ResponseEntity<Book> update(@RequestBody Book book) {

        if (book.getId() == null) {
            log.warn("Trying to update a non existent book, use POST method to create a book");
            return ResponseEntity.badRequest().build();
        }

        if (!repository.existsById(book.getId())) {
            log.warn("Trying to update a non existent book, use POST method to create a book");
            return ResponseEntity.notFound().build();
        }

        Book result = repository.save(book);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/api/books/{id}")
    public ResponseEntity<Book> delete(@PathVariable Long id) {

        if (!repository.existsById(id)) {
            log.warn("Trying to delete a non existent book");
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/api/books/delete-all")
    public ResponseEntity<Book> deleteAll() {

        repository.deleteAll();
        log.info("All books deleted");
        return ResponseEntity.noContent().build();
    }
}
