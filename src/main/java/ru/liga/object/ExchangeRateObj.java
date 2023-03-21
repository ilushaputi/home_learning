package ru.liga.object;

import java.time.LocalDate;

public class ExchangeRateObj {

    private LocalDate date;
    private float rate;

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
