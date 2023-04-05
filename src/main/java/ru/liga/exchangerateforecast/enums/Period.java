package ru.liga.exchangerateforecast.enums;

public enum Period {
    WEEK("week", 7),
    MONTH("month", 30);

    private String periodName;
    private int periodDays;

    Period(String periodName, int periodDays) {
        this.periodName = periodName;
        this.periodDays = periodDays;
    }

    public int getPeriodDays() {
        return periodDays;
    }

    @Override
    public String toString() {
        return this.periodName;
    }

}
