package com.example.task4.controllers;

import com.example.task4.models.Book;
import com.example.task4.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/")
    public String index(Model model) {
        Iterable<Book> books = null;

        try {
            books = bookRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Добавляем в books все найденные записи из таблицы Book
        model.addAttribute("books", books);

        return "index";
    }
}
