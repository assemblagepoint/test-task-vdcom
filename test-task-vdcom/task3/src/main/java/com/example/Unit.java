package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Unit {

    private final String name;
    private final Map<Unit, Double> relations = new HashMap<>();

    public Unit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addRelationWithRatioOtherToCurrent(Unit otherUnit, Double ratioOtherToCurrent) {
        if (otherUnit != null && ratioOtherToCurrent != null && !this.name.equals(otherUnit.getName())) {
            relations.put(otherUnit, ratioOtherToCurrent);
        }
    }

    public Set<Unit> getRelations() {
        return relations.keySet();
    }

    public Double getRatio(Unit otherUnit) {
        return relations.get(otherUnit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return Objects.equals(name, unit.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
