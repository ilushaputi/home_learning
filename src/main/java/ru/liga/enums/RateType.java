package ru.liga.enums;

public enum RateType {
    EUR,
    USD,
    TRY;

    public static RateType giveRateTypeByCommand(String command) {
        if (command.contains(TRY.name())) return TRY;
        if (command.contains(EUR.name())) return EUR;
        if (command.contains(USD.name())) return USD;

        return null;
    }
}
