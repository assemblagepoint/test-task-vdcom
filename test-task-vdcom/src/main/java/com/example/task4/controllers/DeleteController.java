package com.example.task4.controllers;

import com.example.task4.models.Book;
import com.example.task4.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DeleteController {

    @Autowired
    private BookRepository bookRepository;

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteBook(@PathVariable(value = "id") long id, Model model) {
        try {
            Book book = bookRepository.findById(id).orElseThrow();

            bookRepository.delete(book);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage",
                    "Не удалось удалить запись");
            return "index";
        }

        return "redirect:/";
    }
}
