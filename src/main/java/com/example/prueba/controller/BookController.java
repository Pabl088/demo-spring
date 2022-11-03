package com.example.prueba.controller;

import com.example.prueba.entitis.Book;
import com.example.prueba.repository.BookRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    private BookRepository repository;

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
    public Book create(@RequestBody Book book, @RequestHeader HttpHeaders headers) {

        System.out.println(headers.get("User-Agent")); // Obtener el UserAgent
        return repository.save(book);
    }
}
