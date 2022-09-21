package com.example.utils;

import com.example.models.Book;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class CSVHelper {

    public static List<Book> csvToBooks(MultipartFile file) {
        try {
            byte[] receivedFile = file.getBytes();
            String csvToString = new String(receivedFile, StandardCharsets.UTF_8);
            List<String> linesFromCsv = CSVHelper.getLinesFromString(csvToString);
            List<Book> result = new ArrayList<>();

            for (String record : linesFromCsv) {
                String[] tempArr = record.split(";");

                if (tempArr.length != 6)
                    continue;

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

    private static List<String> getLinesFromString(String convertFile) {
        try {
            String[] tempHandlingFile = convertFile.split(System.getProperty("line.separator"));

            return new ArrayList<>(Arrays.asList(tempHandlingFile));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean validateData(String author, int numberOfPages, short publishingYear, int price) {
        return author != null && author.length() != 0 &&
                numberOfPages >= 0 &&
                publishingYear >= 0 && publishingYear <= Calendar.getInstance().get(Calendar.YEAR) &&
                price >= 0;
    }
}
