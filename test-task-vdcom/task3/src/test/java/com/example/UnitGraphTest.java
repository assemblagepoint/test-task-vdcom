package com.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class UnitGraphTest {

    @Test
    public void calculatingValuesTest() {
        Equality equality1 = new Equality.Builder()
                .leftValue(4.5).leftUnit(new Unit("km"))
                .rightValue(4500.0).rightUnit(new Unit("m"))
                .build();
        Equality equality2 = new Equality.Builder()
                .leftValue(10.0).leftUnit(new Unit("m"))
                .rightValue(10000.0).rightUnit(new Unit("mm"))
                .build();
        Equality equality3 = new Equality.Builder()
                .leftValue(1.5).leftUnit(new Unit("dm"))
                .rightValue(150.0).rightUnit(new Unit("mm"))
                .build();

        List<Equality> equalities = List.of(equality1, equality2, equality3);

        Equality notFilledEquality = new Equality.Builder()
                .leftValue(0.4).leftUnit(new Unit("km"))
                .rightUnit(new Unit("dm"))
                .build();

        Equality emptyEquality = new Equality.Builder().build();

        Equality unknownEquality = new Equality.Builder()
                .leftValue(10.0).leftUnit(new Unit("unknownUnit"))
                .rightUnit(new Unit("m"))
                .build();

        UnitGraph unitGraph = UnitGraph.fromEqualityList(equalities);
        Optional<Double> calculatedValueOfCorrectEquality = unitGraph.calculateRightValueOfEquality(notFilledEquality);
        Optional<Double> calculatedValueOfEmptyEquality = unitGraph.calculateRightValueOfEquality(emptyEquality);
        Optional<Double> calculatedValueOfUnknownEquality = unitGraph.calculateRightValueOfEquality(unknownEquality);

        Assertions.assertTrue(calculatedValueOfCorrectEquality.isPresent());
        Assertions.assertEquals(4000, calculatedValueOfCorrectEquality.get());
        Assertions.assertFalse(calculatedValueOfEmptyEquality.isPresent());
        Assertions.assertFalse(calculatedValueOfUnknownEquality.isPresent());
    }
}
