package com.example;

import java.util.*;
import java.util.stream.Collectors;

public class UnitGraph {

    private final Map<String, Unit> units;

    private UnitGraph(Map<String, Unit> units) {
        this.units = units;
    }

    public static UnitGraph fromEqualityList(List<Equality> equalities) {
        Map<String, Unit> unitMap = new HashMap<>();
        equalities.stream().filter(equality
                -> !equality.getLeftUnit().getName().equals(equality.getRightUnit().getName()) && equality.getRightValue() != null).forEach(equality
                -> {
            Unit leftUnit = equality.getLeftUnit();
            Unit rightUnit = equality.getRightUnit();

            if (unitMap.containsKey(leftUnit.getName())) {
                leftUnit = unitMap.get(leftUnit.getName());
            }

            if (unitMap.containsKey(rightUnit.getName())) {
                rightUnit = unitMap.get(rightUnit.getName());
            }

            Double leftValue = equality.getLeftValue();
            Double rightValue = equality.getRightValue();
            Double ratioLeftUnitToRightUnit = leftValue / rightValue;
            Double ratioRightUnitToLeftUnit = rightValue / leftValue;
            leftUnit.addRelationWithRatioOtherToCurrent(rightUnit, ratioRightUnitToLeftUnit);
            rightUnit.addRelationWithRatioOtherToCurrent(leftUnit, ratioLeftUnitToRightUnit);
            unitMap.put(leftUnit.getName(), leftUnit);
            unitMap.put(rightUnit.getName(), rightUnit);
        });
        return new UnitGraph(unitMap);
    }

    public Optional<Double> calculateRightValueOfEquality(Equality equality) {
        if (equality.getLeftValue() != null && equality.getLeftUnit() != null && equality.getRightUnit() != null) {
            Unit knownUnit = units.get(equality.getLeftUnit().getName());
            Unit unknownUnit = units.get(equality.getRightUnit().getName());
            if (knownUnit == null || unknownUnit == null)
                return Optional.empty();
            Double ratioUnknownToKnown = findRatio(knownUnit, unknownUnit, new ArrayList<>());
            if (ratioUnknownToKnown != null) {
                return Optional.of(ratioUnknownToKnown * equality.getLeftValue());
            }
        }
        return Optional.empty();
    }

    private Double findRatio(Unit knownUnit, Unit unknownUnit, List<Unit> passedUnits) {
        Double ratioUnknownToKnown = null;
        Set<Unit> relations = knownUnit.getRelations();
        if (relations.contains(unknownUnit)) {
            ratioUnknownToKnown = knownUnit.getRatio(unknownUnit);
        } else {
            Set<Unit> filteredRelations = relations.stream()
                    .filter(unit -> !passedUnits.contains(unit))
                    .collect(Collectors.toSet());
            if (filteredRelations.isEmpty()) return null;
            for (Unit unit : filteredRelations) {
                passedUnits.add(unit);
                Double ratioUnknownToCurrent = findRatio(unit, unknownUnit, passedUnits);
                if (ratioUnknownToCurrent != null) {
                    Double ratioCurrentToKnown = knownUnit.getRatio(unit);
                    return ratioCurrentToKnown * ratioUnknownToCurrent;
                }
            }
        }
        return ratioUnknownToKnown;
    }
}
