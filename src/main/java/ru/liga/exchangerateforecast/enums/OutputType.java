package ru.liga.exchangerateforecast.enums;

public enum OutputType {
    LIST("list"),
    GRAPH("graph");

    private String outputTypeName;

    OutputType(String outputTypeName) {
        this.outputTypeName = outputTypeName;
    }

    @Override
    public String toString() {
        return outputTypeName;
    }
}
