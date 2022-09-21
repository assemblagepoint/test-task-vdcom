package com.example.controllers;

import com.example.models.Book;
import com.example.repositories.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@Controller
public class EditBookController {

    private final BookRepository bookRepository;

    public EditBookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/edit/{id}")
    public String editBook(Model model, @PathVariable(value = "id") long id) {
        Book book = null;

        try {
            book = bookRepository.findById(id).orElseThrow();
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("book", book);

        return "edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String editBook(@PathVariable(value = "id") long id, @RequestParam String author, @RequestParam String title,
                           @RequestParam int numberOfPages, @RequestParam short publishingYear, @RequestParam int price,
                           @RequestParam(required = false) boolean inStock, Model springModel) {
        try {
            Book book = bookRepository.findById(id).orElseThrow();

            if (author == null || author.length() == 0 || title == null || title.length() == 0 || numberOfPages < 0
                    || publishingYear < 0 || publishingYear > Calendar.getInstance().get(Calendar.YEAR) || price < 0) {
                springModel.addAttribute("book", book);
                springModel.addAttribute("errorMessage",
                        "Не удалось сохранить данные. Неправильно заполнены поля");
                return "edit";
            }

            book.setAuthor(author);
            book.setTitle(title);
            book.setNumberOfPages(numberOfPages);
            book.setPublishingYear(publishingYear);
            book.setPrice(price);
            book.setInStock(inStock);

            bookRepository.save(book);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
}
