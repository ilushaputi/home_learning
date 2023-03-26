package ru.liga.algorithms;

import ru.liga.enums.RateType;
import ru.liga.entity.ExchangeRateObj;
import ru.liga.reader.ResourceReader;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RateCalculationAlgorithm {
    private final Locale locale = new Locale("ru", "RU");
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy", locale);
    private final ResourceReader resourceReader = new ResourceReader();
    private final int WEEK = 7;

    public void calculateTomorrowRateByRateType(RateType type, LocalDate today) {
        float rate7Days = 0.0f;

        for (int j = 0; j < WEEK; j++){
            rate7Days = rate7Days + resourceReader.giveListByRateType(type).get(j).getRate();
        }

        prepareAndPrintExchangeRateObject(rate7Days, today);
    }

    public ExchangeRateObj calculateTomorrowRateForWeek(List<ExchangeRateObj> list, LocalDate today){
        float rate7Days = 0.0f;

        for (int j = 0; j < WEEK; j++){
            rate7Days = rate7Days + list.get(j).getRate();
        }

        return prepareAndPrintExchangeRateObject(rate7Days, today);
    }

    public ExchangeRateObj prepareAndPrintExchangeRateObject(float rate7Days, LocalDate today) {
        ExchangeRateObj exchangeRateObj = new ExchangeRateObj();
        exchangeRateObj.setDate(today.plusDays(1L));
        exchangeRateObj.setRate((rate7Days/WEEK));

        printRate(exchangeRateObj);

        return exchangeRateObj;
    }

    public void calculateWeekRateByRateType(RateType type) {
        List<ExchangeRateObj> outputListObjects = new ArrayList<>();
        for (int i = 0; i < WEEK; i++){
            outputListObjects.add(resourceReader.giveListByRateType(type).get(i));
        }
        for (int i = 0; i < WEEK; i++){
            ExchangeRateObj exchangeRateObj = calculateTomorrowRateForWeek(outputListObjects, LocalDate.now().plusDays(i));
            outputListObjects.remove(WEEK-i-1);
            outputListObjects.add(0, exchangeRateObj);
        }
    }

    public void printRate(ExchangeRateObj obj) {
        System.out.println(obj.getDate().getDayOfWeek().getDisplayName(TextStyle.SHORT, locale) +
                                                                       " " + obj.getDate().format(FORMATTER) +
                                                                       " - " + String.format("%.2f", obj.getRate())
                                                                       );
    }
}
