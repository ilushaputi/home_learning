package ru.liga.exchangerateforecast.algorithms;

import ru.liga.exchangerateforecast.entity.ExchangeRateObj;
import ru.liga.exchangerateforecast.enums.Period;
import ru.liga.exchangerateforecast.enums.RateType;
import ru.liga.exchangerateforecast.reader.ResourceReader;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InternetRateCalculationAlgorithm implements Algorithm {
    private final ResourceReader resourceReader = new ResourceReader();
    private final int DAYS_IN_MONTH = 30;

    public List<ExchangeRateObj> calculateInternetRateForDay(RateType type, LocalDate date) {
        List<ExchangeRateObj> outputList = new ArrayList<>();
        double[] rates = resourceReader.giveListByRateType(type).stream()
                .limit(DAYS_IN_MONTH)
                .map(ExchangeRateObj::getRate)
                .map(Float::doubleValue)
                .mapToDouble(Double::doubleValue)
                .toArray();

        double[] days = resourceReader.giveListByRateType(type).stream()
                .limit(DAYS_IN_MONTH)
                .map(ExchangeRateObj::getDate)
                .map(LocalDate::getDayOfMonth)
                .map(Integer::doubleValue)
                .mapToDouble(Double::doubleValue)
                .toArray();

        InternetAlgorithm internetAlgorithm = new InternetAlgorithm(days, rates);

        double newDate = java.time.Period.between(resourceReader.giveListByRateType(type).get(0).getDate(), date).getDays();
        Float newRate = (float) internetAlgorithm.predict(newDate);
        outputList.add(new ExchangeRateObj(date, newRate));
        return outputList;
    }

    public List<ExchangeRateObj> calculateInternetRateForPeriod(Period period, RateType type) {
        LocalDate date = LocalDate.now();
        List<ExchangeRateObj> outputList = new ArrayList<>();
        for (int i = 0; i < period.getPeriodDays(); i++) {
            outputList.addAll(calculateInternetRateForDay(type, date.plusDays(i)));
        }
        return outputList;
    }

    @Override
    public Map<RateType, List<ExchangeRateObj>> calculateRateForDay(LocalDate date, List<RateType> rateTypeList) {
        Map<RateType, List<ExchangeRateObj>> outputMap = new HashMap<>();
        for (RateType type : rateTypeList) {
            outputMap.put(type, calculateInternetRateForDay(type, date));
        }
        return outputMap;
    }

    @Override
    public Map<RateType, List<ExchangeRateObj>> calculateRateForPeriod(List<RateType> rateTypeList, Period period) {
        Map<RateType, List<ExchangeRateObj>> outputMap = new HashMap<>();
        for (RateType type : rateTypeList) {
            outputMap.put(type, calculateInternetRateForPeriod(period, type));
        }
        return outputMap;
    }
}
