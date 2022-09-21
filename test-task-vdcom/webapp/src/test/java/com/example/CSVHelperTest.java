package com.example;

import com.example.models.Book;
import com.example.utils.CSVHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@SpringBootTest
public class CSVHelperTest {

    @Test
    public void testParseValidFile() {
        InputStream validFile = getClass().getClassLoader().getResourceAsStream("valid.csv");

        MultipartFile multipartFile = null;
        try {
            byte[] receivedFile = Objects.requireNonNull(validFile).readAllBytes();
            multipartFile = new MockMultipartFile("valid.csv", receivedFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Book> books = CSVHelper.csvToBooks(multipartFile);
        Assertions.assertEquals(5, books.size());
    }

    @Test
    public void testParseInvalidFile() {
        InputStream validFile = getClass().getClassLoader().getResourceAsStream("invalid.csv");

        MultipartFile multipartFile = null;
        try {
            byte[] receivedFile = Objects.requireNonNull(validFile).readAllBytes();
            multipartFile = new MockMultipartFile("invalid.csv", receivedFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Book> books = CSVHelper.csvToBooks(multipartFile);

        Assertions.assertNotEquals(null, books);
        Assertions.assertTrue(books.isEmpty());

    }
}
