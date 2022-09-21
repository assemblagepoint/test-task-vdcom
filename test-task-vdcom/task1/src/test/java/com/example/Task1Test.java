package com.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Task1Test {

    private final int NUMBER = 15;
    private final String EXPECTED = "[1, 2, Foo, 4, Bar, Foo, 7, 8, Foo, Bar, 11, Foo, 13, 14, FooBar]";

    @Test
    @DisplayName("Решение 1. Используя цикл и ветвления")
    public void solution1Test() {
        Assertions.assertEquals(EXPECTED, Task1.fooBar(NUMBER).toString());
    }

    @Test
    @DisplayName("Решение 2. Минимум ветвлений")
    public void solution2Test() {
        String EXPECTED2 = "1  2  Foo  4  Bar  Foo  7  8  Foo  Bar  11  Foo  13  14  FooBar  ";
        Assertions.assertEquals(EXPECTED2, Task1.fooBar2(NUMBER));
    }

    @Test
    @DisplayName("Решение 3. Используя switch-case")
    public void solution3Test() {
        Assertions.assertEquals(EXPECTED, Task1.fooBar3(NUMBER).toString());
    }

    @Test
    @DisplayName("Решение 4. Используя Stream API")
    public void solution4Test() {
        Assertions.assertEquals(EXPECTED, Task1.fooBar4(NUMBER).toString());
    }

    @Test
    @DisplayName("Числа, кратные 3")
    public void numberIsDividableBy3Test() {
        Assertions.assertEquals("Foo", Task1.fooBar(3).get(2));
        Assertions.assertEquals("Foo", Task1.fooBar2(3).split(" {2}")[2]);
        Assertions.assertEquals("Foo", Task1.fooBar3(3).get(2));
        Assertions.assertEquals("Foo", Task1.fooBar4(3).get(2));
    }

    @Test
    @DisplayName("Числа, кратные 5")
    public void numberIsDividableBy5Test() {
        Assertions.assertEquals("Bar", Task1.fooBar(5).get(4));
        Assertions.assertEquals("Bar", Task1.fooBar2(5).split(" {2}")[4]);
        Assertions.assertEquals("Bar", Task1.fooBar3(5).get(4));
        Assertions.assertEquals("Bar", Task1.fooBar4(5).get(4));
    }

    @Test
    @DisplayName("Числа, кратные 15")
    public void numberIsDividableBy15Test() {
        Assertions.assertEquals("FooBar", Task1.fooBar(15).get(14));
        Assertions.assertEquals("FooBar", Task1.fooBar2(15).split(" {2}")[14]);
        Assertions.assertEquals("FooBar", Task1.fooBar3(15).get(14));
        Assertions.assertEquals("FooBar", Task1.fooBar4(15).get(14));
    }

    @Test
    @DisplayName("Числа, не кратные 3 или 5")
    public void otherNumbersTest() {
        Assertions.assertEquals("1", Task1.fooBar(1).get(0));
        Assertions.assertEquals("1", Task1.fooBar2(1).split(" {2}")[0]);
        Assertions.assertEquals("1", Task1.fooBar3(1).get(0));
        Assertions.assertEquals("1", Task1.fooBar4(1).get(0));
    }
}
