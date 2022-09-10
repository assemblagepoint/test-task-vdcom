package com.example.task4.controllers;

import com.example.task4.models.Book;
import com.example.task4.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

@Controller
public class AddBookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/add")
    public String addBook(Model model) {
        return "add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addBook(@RequestParam String author, @RequestParam String title, @RequestParam int numberOfPages, @RequestParam short publishingYear,
                          @RequestParam int price, @RequestParam(required = false) boolean inStock, Model springModel) {
        try {
            if (!validateData(author, numberOfPages, publishingYear, price)) {
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
    public String addBookFromFile(@RequestParam File file, Model springModel) {
        try {
            ArrayList<String> records = getRecordsFromFile(file);
            ArrayList<Book> books = generateBooksFromRecords(records);

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

    private boolean validateData(String author, int numberOfPages, short publishingYear, int price) {
        return author != null && author.length() != 0 &&
                numberOfPages >= 0 && numberOfPages <= Calendar.getInstance().get(Calendar.YEAR) &&
                publishingYear >= 0 && publishingYear <= Calendar.getInstance().get(Calendar.YEAR) &&
                price >= 0;
    }

    private ArrayList<Book> generateBooksFromRecords(ArrayList<String> records) {
        try {
            ArrayList<Book> result = new ArrayList<>();

            for (String record : records) {
                String[] tempArr = record.split(";");

                if (tempArr.length != 6)
                    throw new Exception("Invalid record format in file");

                String author = tempArr[0];
                String title = tempArr[1];
                int numberOfPages = Integer.parseInt(tempArr[2]);
                short publishingYear = Short.parseShort(tempArr[3]);
                int price = Integer.parseInt(tempArr[4]);
                boolean inStock = Byte.parseByte(tempArr[5]) == 1;

                if (validateData(author, numberOfPages, publishingYear, price)) {
                    Book book = new Book(author, title, numberOfPages, publishingYear, price, inStock);
                    result.add(book);
                }
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ArrayList<String> getRecordsFromFile(File file) {
        try {
            ArrayList<String> result = new ArrayList<>();

            BufferedReader fin = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file), "Windows-1251"));

            String lineFromFile;
            while ((lineFromFile = fin.readLine()) != null)
                result.add(lineFromFile);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
