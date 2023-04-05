package ru.liga.exchangerateforecast.algorithms;

import ru.liga.exchangerateforecast.entity.ExchangeRateObj;
import ru.liga.exchangerateforecast.enums.OutputType;
import ru.liga.exchangerateforecast.enums.Period;
import ru.liga.exchangerateforecast.enums.RateType;
import ru.liga.exchangerateforecast.reader.ResourceReader;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class InternetAlg implements Algorithm {
    private final Locale LOCALE = new Locale("ru", "RU");
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy", LOCALE);
    private final ResourceReader resourceReader = new ResourceReader();
    private final int DAYS_IN_MONTH = 30;

    public String calculateInternetRateForDay(RateType type, LocalDate date) {
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

        InternetRateCalculationAlgorithm internetRateCalculationAlgorithm = new InternetRateCalculationAlgorithm(days, rates);

        double newDate = java.time.Period.between(resourceReader.giveListByRateType(type).get(0).getDate(), date).getDays();
        Float rate = (float) internetRateCalculationAlgorithm.predict(newDate);
        return prepareResponse(date, rate).toString();
    }

    public String calculateInternetRateForPeriod(Period period, RateType type) {
        StringBuilder stringBuilder = new StringBuilder();
        LocalDate date = LocalDate.now();
        for (int i = 0; i < period.getPeriodDays(); i++) {
            stringBuilder.append(calculateInternetRateForDay(type, date.plusDays(i)));
        }
        return stringBuilder.toString();
    }

    public StringBuilder prepareResponse(LocalDate date, Float rate) {
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
        return calculateInternetRateForDay(rateTypeList.get(0), date);
    }

    @Override
    public String calculateRateForPeriod(List<RateType> rateTypeList, Period period, OutputType outputType) throws Exception {
        return calculateInternetRateForPeriod(period, rateTypeList.get(0));
    }
}
