package com.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Task2Test {
    private static final Path filePath = Paths.get("out.txt");

    @Test
    @DisplayName("Валидное число")
    void validInputTest() {
        Assertions.assertTrue(() -> Task2.validateInput(String.valueOf(2)));
        Assertions.assertTrue(() -> Task2.validateInput(String.valueOf(16)));
        Assertions.assertTrue(() -> Task2.validateInput(String.valueOf(124)));
    }

    @Test
    @DisplayName("Невалидное число")
    void invalidInputTest() {
        Assertions.assertFalse(() -> Task2.validateInput(String.valueOf(0)));
        Assertions.assertFalse(() -> Task2.validateInput(String.valueOf(-6)));
        Assertions.assertFalse(() -> Task2.validateInput(String.valueOf(-1.5)));
        Assertions.assertFalse(() -> Task2.validateInput(String.valueOf(1)));
        Assertions.assertFalse(() -> Task2.validateInput(String.valueOf(15)));
    }

    @Test
    @DisplayName("Инициализация out.txt с исходным значением 0")
    void initTextFileTest() {
        try {
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }

            Assertions.assertTrue(Task2.initializeFile());
            Assertions.assertTrue(Files.exists(filePath));
            Assertions.assertEquals("0", Files.readString(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Итоговое число в out.txt")
    void threadsTest() {
        try {
            if (Task2.initializeFile())
                Task2.startThreads(10);
            Thread.sleep(1000);
            Assertions.assertEquals("10", Files.readString(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
