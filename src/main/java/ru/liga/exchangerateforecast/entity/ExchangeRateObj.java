package ru.liga.exchangerateforecast.entity;

import java.time.LocalDate;

public class ExchangeRateObj {

    private LocalDate date;
    private float rate;

    public ExchangeRateObj(LocalDate date, float rate) {
        this.date = date;
        this.rate = rate;
    }

    public ExchangeRateObj() {

    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

}
