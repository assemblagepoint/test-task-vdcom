package com.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

public class FileParserTest {

    @Test
    public void parsingFileTest() {
        String resource = Objects.requireNonNull(getClass().getClassLoader().getResource("in.txt")).getFile();

        List<Equality> equalities = FileParser.parseFile(resource);
        int filledEqualityCount = 0;
        int notFilledEqualityCount = 0;
        for (Equality equality : equalities) {
            if (equality.getRightValue() == null) {
                notFilledEqualityCount++;
            } else {
                filledEqualityCount++;
            }
        }
        Assertions.assertEquals(11, equalities.size());
        Assertions.assertEquals(7, filledEqualityCount);
        Assertions.assertEquals(4, notFilledEqualityCount);
    }
}
