package com.example.controllers;

import com.example.models.Book;
import com.example.repositories.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final BookRepository bookRepository;

    public MainController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        Iterable<Book> books = null;

        try {
            books = bookRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("books", books);

        return "index";
    }
}
