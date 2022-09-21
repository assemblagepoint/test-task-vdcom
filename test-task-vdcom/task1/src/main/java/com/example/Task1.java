package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * На вход приложение получает целое число больше 0 (n), далее в консоль выводится следующее, все числа от 1 до n, при этом:
 * ● Если число кратно 3, выводится Foo;
 * ● Если число кратно 5, выводится Bar;
 * ● Если число кратно и 3, и 5, выводится FooBar;
 * ● Если число не кратно 3 или 5, выводится само число.
 * Необходимо предоставить минимум три разных решения, используя разные подходы (минимум ветвлений, без циклов и т.п.)
 */
public class Task1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int num;
        while (true) {
            System.out.print("Введите целое число больше 0: ");

            try {
                String str = in.next();
                num = Integer.parseInt(str);
                if (num <= 0) {
                    System.out.print("Вы ввели отрицательное целое число\n");
                } else {
                    in.close();
                    break;
                }
            } catch (NumberFormatException nfe) {
                System.out.print("Вы ввели не целое число\n");
            } catch (Exception e) {
                System.out.print("Неизвестная ошибка\n");
            }
        }

        System.out.printf("Вы ввели: %d \n", num);

        System.out.print("Способ 1:");
        System.out.printf(String.valueOf(fooBar(num)));

        System.out.print("\nСпособ 2: ");
        System.out.printf(fooBar2(num));

        System.out.print("\nСпособ 3:");
        System.out.printf(String.valueOf(fooBar3(num)));

        System.out.print("\nСпособ 4:");
        System.out.printf(String.valueOf(fooBar4(num)));
    }

    /**
     * Решение 1. Используя цикл и ветвления
     */
    public static List<String> fooBar(int n) {
        List<String> list = new ArrayList<>();
        for (int i = 1; i < n + 1; i++) {
            if (i % 15 == 0) {
                list.add("FooBar");
            } else if (i % 3 == 0) {
                list.add("Foo");
            } else if (i % 5 == 0)
                list.add("Bar");
            else {
                list.add(String.valueOf(i));
            }
        }
        return list;
    }

    /**
     * Решение 2. Минимум ветвлений
     */
    public static String fooBar2(int n) {
        StringBuilder res = new StringBuilder();
        for (int i = 1; i < n + 1; i++) {
            String result = "";
            if (i % 3 == 0) {
                result += "Foo";
            }
            if (i % 5 == 0) {
                result += "Bar";
            }
            if (result.equals("")) {
                result = String.valueOf(i);
            }
            res.append(result).append("  ");
        }
        return res.toString();
    }

    /**
     * Решение 3. Используя switch-case
     */
    public static List<String> fooBar3(int n) {
        List<String> list = new ArrayList<>();
        for (int i = 1; i < n + 1; i++) {
            final int mod = i % 15;
            switch (mod) {
                case 0 -> list.add("FooBar");
                case 3, 6, 9, 12 -> list.add("Foo");
                case 5, 10 -> list.add("Bar");
                default -> list.add(String.valueOf(i));
            }
        }
        return list;
    }

    /**
     * Решение 4. Используя Stream API
     */
    public static List<String> fooBar4(int n) {
        return IntStream.rangeClosed(1, n)
                .mapToObj(i -> i % 3 == 0 ? (i % 5 == 0 ? "FooBar" : "Foo") : (i % 5 == 0 ? "Bar" : String.valueOf(i)))
                .collect(Collectors.toList());
    }
}

