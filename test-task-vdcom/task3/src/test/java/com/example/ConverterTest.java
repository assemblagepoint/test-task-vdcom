package com.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class ConverterTest {

    @Test
    public void mainTest() throws FileNotFoundException {
        Converter.main("input.txt");

        File result = new File("result.txt");
        Scanner scanner = new Scanner(new FileReader(result));

        Assertions.assertEquals(scanner.nextLine(), "1 pyramid = 1.4 bar");
        Assertions.assertEquals(scanner.nextLine(), "1 giraffe = 40 hare");
        Assertions.assertEquals(scanner.nextLine(), "Conversion not possible.");
        Assertions.assertEquals(scanner.nextLine(), "2 kilobyte = 16384 bit");
    }
}
