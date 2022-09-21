package com.example.controllers;

import com.example.models.Book;
import com.example.repositories.BookRepository;
import com.example.utils.CSVHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class AddBookController {

    private final BookRepository bookRepository;

    public AddBookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/add")
    public String addBook(Model ignoredModel) {
        return "add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addBook(@RequestParam String author, @RequestParam String title, @RequestParam int numberOfPages, @RequestParam short publishingYear,
                          @RequestParam int price, @RequestParam(required = false) boolean inStock, Model springModel) {
        try {
            if (!CSVHelper.validateData(author, numberOfPages, publishingYear, price)) {
                springModel.addAttribute("errorMessage",
                        "Не удалось сохранить данные. Неправильно заполнены поля");
                return "add";
            }

            Book book = new Book(author, title, numberOfPages, publishingYear, price, inStock);
            bookRepository.save(book);
        } catch (Exception e) {
            e.printStackTrace();
            springModel.addAttribute("errorMessage",
                    "Не удалось сохранить данные. Неизвестная ошибка");
            return "add";
        }

        return "redirect:/";
    }

    @RequestMapping(value = "/add-file", method = RequestMethod.POST)
    public String addBookFromFile(@RequestParam MultipartFile file, Model springModel) {
        try {
            List<Book> books = CSVHelper.csvToBooks(file);
            if (books == null || books.size() == 0) {
                springModel.addAttribute("errorFileMessage",
                        "Не удалось добавить записи. " +
                                "Проверьте файл на соответствие формата. Требуемый разделитель ';'");
                return "add";
            }
            bookRepository.saveAll(books);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }
}
