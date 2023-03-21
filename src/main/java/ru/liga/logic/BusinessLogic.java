package ru.liga.logic;

import ru.liga.emuns.RateType;
import ru.liga.object.ExchangeRateObj;
import ru.liga.resource_reader.ResourceReader;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BusinessLogic {
    LocalDate today = LocalDate.now();
    Locale locale = new Locale("ru", "RU");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", locale);



    public void getTomorrowRate(RateType type) {
        if (type.equals(RateType.EUR)) {
            getTomorrowRateByRateType(ResourceReader.listEUR, today);
        } else if (type.equals(RateType.TRY)) {
            getTomorrowRateByRateType(ResourceReader.listTRY, today);
        } else if (type.equals(RateType.USD)) {
            getTomorrowRateByRateType(ResourceReader.listUSD, today);
        }
    }

    public ExchangeRateObj getTomorrowRateByRateType(List<ExchangeRateObj> list, LocalDate today) {
        int count = 0;
        float rate7Days = 0.0f;
        while (count < 7){
            rate7Days = rate7Days + list.get(count).getRate();
            count++;
        }

        ExchangeRateObj obj = new ExchangeRateObj();
        obj.setDate(today.plusDays(1L));
        obj.setRate((rate7Days/7));

        printRate(obj);

        return obj;
    }

    public void getWeekRate(RateType type) {
        if (type.equals(RateType.EUR)) {
            getWeekRateByRateType(ResourceReader.listEUR, today);
        } else if (type.equals(RateType.USD)){
            getWeekRateByRateType(ResourceReader.listUSD, today);
        } else if (type.equals(RateType.TRY)) {
            getWeekRateByRateType(ResourceReader.listTRY, today);
        }
    }

    public void getWeekRateByRateType(List<ExchangeRateObj> list, LocalDate today) {
        List<ExchangeRateObj> outputListObjects = new ArrayList<>();
        int j = 0;
        while (j < 7){
            outputListObjects.add(list.get(j));
            j++;
        }

        int i = 7;
        int g = 0;

        while (i > 0) {
            i--;
            ExchangeRateObj object = getTomorrowRateByRateType(outputListObjects, today.plusDays(g));
            outputListObjects.remove(i);
            outputListObjects.add(0, object);
            g++;
        }
    }

    public void printRate(ExchangeRateObj obj) {
        float count = 100.0f;
        System.out.println(obj.getDate().getDayOfWeek().getDisplayName(TextStyle.SHORT, locale) +
                                                                       " " + obj.getDate().format(formatter) +
                                                                       " - " + Math.round(obj.getRate() * count) / count
                                                                       );
    }
}
