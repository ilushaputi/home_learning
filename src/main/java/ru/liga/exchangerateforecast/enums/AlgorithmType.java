package ru.liga.exchangerateforecast.enums;

public enum AlgorithmType {
    OLD("old"),
    LAST_YEAR("lastyear"),
    MYSTIC("mystic"),
    INTERNET("internet");

    private String algorithmName;

    AlgorithmType(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    @Override
    public String toString() {
        return algorithmName;
    }
}
