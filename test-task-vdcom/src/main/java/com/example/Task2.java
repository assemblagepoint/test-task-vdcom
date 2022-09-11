package com.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * На вход передается целое число, больше 0, кратное 2 (n). Создается файл с именем
 * out.txt, в который пишется значение 0. Создается два потока, работающих
 * параллельно, каждый поток в цикле, считывает значение из файла out.txt увеличивает
 * его на 1, выводит в консоли старое значение, новое значение и идентификатор потока
 * (идентификатор может быть произвольным), и записывает обратно в файл.
 * В конечном итоге оба потока должны успешно завершить свою работу, и в консоль
 * должно вывестись содержимое файла out.txt (которое должно быть равно заданному
 * на входе n).
 */
public class Task2 {
    private static final Path filePath = Paths.get("out.txt");

    public static void main(String[] args) {
        int evenNumber = getNumberFromInput();
        if (initializeFile())
            startThreads(evenNumber);
        System.out.print(filePath);
    }

    public static void startThreads(int evenNumber) {
        Runnable task1 = generateTask("Поток #1", evenNumber);
        Runnable task2 = generateTask("Поток #2", evenNumber);

        if (task1 == null || task2 == null) {
            System.out.print("Не удалось создать потоки\n");
            return;
        }

        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);

        thread2.start();
        thread1.start();

    }

    private static Runnable generateTask(String nameThread, int evenNumber) {
        Runnable task = null;
        try {
            task = () -> {
                try {
                    fileHandling(nameThread, evenNumber);
                } catch (Exception e) {
                    System.out.print("Ошибка в работе потока '" + nameThread + "'\n");
                    e.printStackTrace();
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return task;
    }

    private static void fileHandling(String name, int limiter) throws Exception {
        while (true) {
            if (!changeValueFile(name, limiter))
                break;
        }
    }

    private static synchronized boolean changeValueFile(String name, int limiter) throws Exception {
        String valueFromFile = Files.readString(filePath);
        int num = Integer.parseInt(valueFromFile);

        if (num < limiter) {
            System.out.print((num++) + " " + num + " - " + name + "\n");
            Files.write(filePath, String.valueOf(num).getBytes());
            return true;
        } else {
            System.out.print(num + " - " + name + "\n");
            return false;
        }
    }

    public static boolean initializeFile() {
        try {
            if (Files.notExists(filePath)) {
                Files.createFile(filePath);
            }
            Files.write(filePath, "0".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("Не удалось инициализировать файл\n");
            return false;
        }
        return true;
    }

    private static int getNumberFromInput() {
        Scanner scanner = new Scanner(System.in);
        int number;

        while (true) {
            System.out.print("Введите целое четное число больше 0: ");
            String receivedResponse = scanner.next();
            if (validateInput(receivedResponse)) {
                number = Integer.parseInt(receivedResponse);
                break;
            }
        }
        return number;
    }

    public static boolean validateInput(String receivedResponse) {
        try {
            int number = Integer.parseInt(receivedResponse);
            if (number <= 0) {
                System.out.print("Вы ввели не положительное число\n");
                return false;
            } else if (number % 2 != 0) {
                System.out.print("Вы ввели нечетное число\n");
                return false;
            }
        } catch (NumberFormatException nfe) {
            System.out.print("Вы ввели не целое число\n");
            return false;
        } catch (Exception e) {
            System.out.print("Неизвестная ошибка\n");
            return false;
        }
        return true;
    }
}