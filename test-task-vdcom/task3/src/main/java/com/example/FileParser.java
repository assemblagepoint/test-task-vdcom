package com.example;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParser {

    public static List<Equality> parseFile(String filePath) {
        List<Equality> equalities = new ArrayList<>();
        try {
            FileReader fr = new FileReader(filePath);
            Scanner scanner = new Scanner(fr);
            for (int lineNumber = 1; scanner.hasNext(); lineNumber++) {
                String line = scanner.nextLine();
                if (line.isEmpty()) continue;
                Equality currentEquality = parseLine(line, lineNumber);
                equalities.add(currentEquality);
            }
        } catch (FileNotFoundException e) {
            System.out.format("Файл не найден '%s'", filePath);
            System.exit(1);
        }
        return equalities;
    }

    private static Equality parseLine(String line, int lineNumber) {
        String equalityRegex = "\\d*\\.?\\d+ \\D+ = ([\\?]|(\\d*\\.?\\d+)) \\D+";
        Pattern equalityPattern = Pattern.compile(equalityRegex);
        Matcher equalityMatcher = equalityPattern.matcher(line);
        boolean isCorrectLine = equalityMatcher.matches();

        if (!isCorrectLine) {
            throw new IllegalArgumentException(String.format("Неверный формат данных (строка %d)", lineNumber));
        }

        String[] partsOfEquality = line.split(" = ");

        String[] leftPart = partsOfEquality[0].split(" ");
        Double leftValue = Double.parseDouble(leftPart[0]);
        Unit leftUnit = new Unit(leftPart[1]);

        String[] rightPart = partsOfEquality[1].split(" ");
        Double rightValue = rightPart[0].equals("?") ? null : Double.parseDouble(rightPart[0]);
        Unit rightUnit = new Unit(rightPart[1]);

        return new Equality.Builder()
                .leftValue(leftValue).leftUnit(leftUnit)
                .rightValue(rightValue).rightUnit(rightUnit)
                .build();
    }
}
