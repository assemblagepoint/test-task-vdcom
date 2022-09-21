package com.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EqualityTest {

    @Test
    public void equalityToStringTest() {
        Unit km = new Unit("km");
        Unit m = new Unit("m");
        Equality filledEquality = new Equality.Builder()
                .leftValue(4.5).leftUnit(km)
                .rightValue(4500.0).rightUnit(m)
                .build();
        Equality notFilledEquality = new Equality.Builder()
                .leftValue(4.5).leftUnit(km)
                .rightUnit(m)
                .build();
        Equality emptyEquality = new Equality.Builder().build();

        String filledResult = filledEquality.toString();
        String notFilledResult = notFilledEquality.toString();
        String emptyResult = emptyEquality.toString();

        Assertions.assertEquals(filledResult, "4.5 km = 4500 m");
        Assertions.assertEquals(notFilledResult, "4.5 km = ? m");
        Assertions.assertEquals(emptyResult, "? ? = ? ?");
    }
}
