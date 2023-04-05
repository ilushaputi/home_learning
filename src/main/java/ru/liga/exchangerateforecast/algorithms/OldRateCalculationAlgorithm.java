package ru.liga.exchangerateforecast.algorithms;

import ru.liga.exchangerateforecast.entity.ExchangeRateObj;
import ru.liga.exchangerateforecast.enums.OutputType;
import ru.liga.exchangerateforecast.enums.Period;
import ru.liga.exchangerateforecast.enums.RateType;
import ru.liga.exchangerateforecast.reader.ResourceReader;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OldRateCalculationAlgorithm implements Algorithm {
    private final Locale LOCALE = new Locale("ru", "RU");
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy", LOCALE);
    private final int DAYS_PER_WEEK = 7;

    private final ResourceReader resourceReader = new ResourceReader();

    public String calculateRateForDay(LocalDate date, RateType rateType) {
        List<ExchangeRateObj> exchangeRateObjList = resourceReader.giveListByRateType(rateType);
        float courseAvg = 0.0f;

        if (date.equals(LocalDate.now().plusDays(1))){
            for (int i = 0; i < DAYS_PER_WEEK; i++) {
                courseAvg += exchangeRateObjList.get(i).getRate();
            }
        } else {
          return calculateRateForPeriod(rateType, null,date);
        }

        return prepareResponse(date, courseAvg/DAYS_PER_WEEK).toString();
    }

    public ExchangeRateObj calculateTomorrowRateForWeek(List<ExchangeRateObj> list, LocalDate date) {
        float courseAvg = 0.0f;

        for (int j = 0; j < DAYS_PER_WEEK; j++) {
            courseAvg += list.get(j).getRate();
        }

        return prepareAndPrintExchangeRateObject(courseAvg, date);
    }

    public ExchangeRateObj prepareAndPrintExchangeRateObject(float rate7Days, LocalDate today) {
        ExchangeRateObj exchangeRateObj = new ExchangeRateObj();
        exchangeRateObj.setDate(today.plusDays(1L));
        exchangeRateObj.setRate((rate7Days / DAYS_PER_WEEK));

        return exchangeRateObj;
    }

    public String calculateRateForPeriod(RateType rateType, Period period, LocalDate date) {
        List<ExchangeRateObj> calculationList = new ArrayList<>();
        List<ExchangeRateObj> outputList = new ArrayList<>();
        for (int i = 0; i < DAYS_PER_WEEK; i++) {
            calculationList.add(resourceReader.giveListByRateType(rateType).get(i));
        }

        if (date.equals(LocalDate.now())){
            for (int i = 0; i < period.getPeriodDays(); i++) {
                ExchangeRateObj exchangeRateObj = calculateTomorrowRateForWeek(calculationList, LocalDate.now().plusDays(i));
                calculationList.remove(DAYS_PER_WEEK - 1);
                calculationList.add(0, exchangeRateObj);
                outputList.add(exchangeRateObj);
            }
        } else {
            int days = java.time.Period.between(LocalDate.now(), date).getDays();
            for (int i = 0; i < days; i++) {
                ExchangeRateObj exchangeRateObj = calculateTomorrowRateForWeek(calculationList, LocalDate.now().plusDays(i));
                calculationList.remove(DAYS_PER_WEEK - 1);
                calculationList.add(0, exchangeRateObj);
                outputList.clear();
                outputList.add(exchangeRateObj);
            }
        }
        return prepareAndPrintExchangeRateObjectList(outputList);
    }

    public String prepareAndPrintExchangeRateObjectList(List<ExchangeRateObj> exchangeRateObjList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ExchangeRateObj exchangeRateObj : exchangeRateObjList) {
            stringBuilder.append(prepareResponse(exchangeRateObj.getDate(), exchangeRateObj.getRate()));
        }
        return stringBuilder.toString();
    }

    public StringBuilder prepareResponse(LocalDate date, Float rate){
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(date.getDayOfWeek().getDisplayName(TextStyle.SHORT, LOCALE))
                .append(" ")
                .append(date.format(FORMATTER))
                .append(" - ")
                .append(String.format("%.2f", rate))
                .append("\n");
    }

    @Override
    public String calculateRateForDay(LocalDate date, List<RateType> rateTypeList) throws Exception {
        if (rateTypeList.size() == 1) {
            return calculateRateForDay(date, rateTypeList.get(0));
        }
        throw new Exception("Все сломалось, вы ввели несколько курсов, а надо было один! \nВедите команду с самого начала!");
    }

    @Override
    public String calculateRateForPeriod(List<RateType> rateTypeList, Period period, OutputType outputType) throws Exception {
        if (rateTypeList.size() == 1) {
            return calculateRateForPeriod(rateTypeList.get(0), period, LocalDate.now());
        }
        throw new Exception();
    }

}
