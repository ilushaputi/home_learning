package ru.liga.exchangerateforecast.enums;

public enum DateType {
    TOMORROW("tomorrow");
    private String dateTypeName;

    DateType(String dateTypeName) {
        this.dateTypeName = dateTypeName;
    }

    @Override
    public String toString() {
        return dateTypeName;
    }
}
