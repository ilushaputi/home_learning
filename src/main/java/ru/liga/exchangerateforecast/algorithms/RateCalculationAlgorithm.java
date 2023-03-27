package ru.liga.exchangerateforecast.algorithms;

import ru.liga.exchangerateforecast.enums.RateType;
import ru.liga.exchangerateforecast.entity.ExchangeRateObj;
import ru.liga.exchangerateforecast.reader.ResourceReader;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RateCalculationAlgorithm {
    private static final Locale LOCALE = new Locale("ru", "RU");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy", LOCALE);
    private static final int WEEK = 7;

    private final ResourceReader resourceReader = new ResourceReader();

    public void calculateTomorrowRateByRateType(RateType type, LocalDate today) {
        float rateForSevenDays = 0.0f;

        if (type != null) {
            for (int j = 0; j < WEEK; j++) {
                rateForSevenDays = rateForSevenDays + resourceReader.giveListByRateType(type).get(j).getRate();
            }

            prepareAndPrintExchangeRateObject(rateForSevenDays, today);
        } else {
            System.out.println("Unknown currency type, repeat please");
        }
    }

    public ExchangeRateObj calculateTomorrowRateForWeek(List<ExchangeRateObj> list, LocalDate today) {
        float rateForSevenDays = 0.0f;

        for (int j = 0; j < WEEK; j++) {
            rateForSevenDays = rateForSevenDays + list.get(j).getRate();
        }

        return prepareAndPrintExchangeRateObject(rateForSevenDays, today);
    }

    public ExchangeRateObj prepareAndPrintExchangeRateObject(float rate7Days, LocalDate today) {
        ExchangeRateObj exchangeRateObj = new ExchangeRateObj();
        exchangeRateObj.setDate(today.plusDays(1L));
        exchangeRateObj.setRate((rate7Days / WEEK));

        printRate(exchangeRateObj);

        return exchangeRateObj;
    }

    public void calculateWeekRateByRateType(RateType type) {
        if (type != null) {
            List<ExchangeRateObj> outputListObjects = new ArrayList<>();
            for (int i = 0; i < WEEK; i++) {
                outputListObjects.add(resourceReader.giveListByRateType(type).get(i));
            }
            for (int i = 0; i < WEEK; i++) {
                ExchangeRateObj exchangeRateObj = calculateTomorrowRateForWeek(outputListObjects, LocalDate.now().plusDays(i));
                outputListObjects.remove(WEEK - i - 1);
                outputListObjects.add(0, exchangeRateObj);
            }
        } else {
            System.out.println("Unknown currency type, repeat please");
        }
    }

    public void printRate(ExchangeRateObj obj) {
        System.out.println(obj.getDate().getDayOfWeek().getDisplayName(TextStyle.SHORT, LOCALE) +
                " " + obj.getDate().format(FORMATTER) +
                " - " + String.format("%.2f", obj.getRate())
        );
    }
}
