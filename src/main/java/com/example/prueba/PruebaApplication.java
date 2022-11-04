package com.example.prueba;

import com.example.prueba.entitis.Book;
import com.example.prueba.repository.BookRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PruebaApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(PruebaApplication.class, args);
        BookRepository repository = context.getBean(BookRepository.class);

        Book libro1 = new Book(null, "Viven", "Alguien", 385, "Canibales en la nieve", true);
        Book libro2 = new Book(null, "Pachula", "Otro alguien", 258, "Biografia de Pachula", false);
        Book libro3 = new Book(null, "Ser o no ser", "Anonimo", 158, "Dudas existenciales", true);

        repository.save(libro1);
        repository.save(libro2);
        repository.save(libro3);

//        System.out.println(repository.count());
//        System.out.println(repository.findAll());
//
//        System.out.println(repository.existsById(2L));
//        repository.deleteById(2L);
//
//        System.out.println(repository.count());
//        System.out.println(repository.findAll());
    }
}
