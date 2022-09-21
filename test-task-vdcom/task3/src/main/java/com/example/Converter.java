package com.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Дан перевод некоторых величин в формате a V = b W, где a, b – числа; V, W –
 * названия величин (могут быть любые), следом дана последовательность с
 * неизвестным вторым числом в формате a V = ? W, необходимо найти величину,
 * обозначенную в пропорции знаком вопроса. Ввод данных и вывод результата
 * осуществляются через стандартные потоки ввода/вывода.
 * Выходной формат a V = b W. Каждое равенство пишется на отдельной строке.
 * Постарайтесь максимально продемонстрировать свои знания ООP/OOD.
 * Код должен соответствовать критериям коммерческого качества кода.
 */
public class Converter {

    public static void main(String... args) {
        String filePath = getCurrentPath();
        if (args.length == 0) {
            filePath = filePath.concat("input.txt");
        } else {
            filePath = filePath.concat(args[0]);
        }

        List<Equality> equalities = FileParser.parseFile(filePath);
        UnitGraph unitGraph = UnitGraph.fromEqualityList(equalities);
        List<Equality> notFilledEqualities = equalities.stream()
                .filter(equality -> equality.getRightValue() == null)
                .collect(Collectors.toList());
        notFilledEqualities.forEach(equality -> {
            Optional<Double> rightValue = unitGraph.calculateRightValueOfEquality(equality);
            rightValue.ifPresent(equality::setRightValue);
        });
        writeResultFile(notFilledEqualities);
    }

    private static void writeResultFile(List<Equality> equalities) {
        File out = new File(getCurrentPath() + "result.txt");
        try {
            FileWriter fileWriter = new FileWriter(out);
            for (Equality equality : equalities) {
                if (equality.getRightValue() == null) {
                    fileWriter.write("Conversion not possible.");
                } else {
                    fileWriter.write(equality.toString());
                }
                fileWriter.write(System.lineSeparator());
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getCurrentPath() {
        String path = Converter.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = path.substring(0, path.length() - 1);
        path = path.substring(0, path.lastIndexOf("/"));
        path = path.substring(0, path.lastIndexOf("/"));
        path = path.concat("/");
        return path;
    }
}
