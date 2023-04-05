package ru.liga.exchangerateforecast.enums;

public enum FileName {
    FILE_EUR("EUR.csv"),
    FILE_USD("USD.csv"),
    FILE_TRY("TRY.csv"),
    FILE_AMD("AMD.csv"),
    FILE_BGN("BGN.csv");

    private String fileName;

    FileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return this.fileName;
    }
}
