package ru.liga.exchangerateforecast.algorithms;

import ru.liga.exchangerateforecast.enums.OutputType;
import ru.liga.exchangerateforecast.enums.Period;
import ru.liga.exchangerateforecast.enums.RateType;
import ru.liga.exchangerateforecast.reader.ResourceReader;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LastYearRateCalculationAlgorithm implements Algorithm {
    private final ResourceReader resourceReader = new ResourceReader();
    private final Locale LOCALE = new Locale("ru", "RU");
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy", LOCALE);
    private final int YEAR = 1;

    public String calculateLastYeaRateForPeriod(Period period, RateType type) {
        StringBuilder stringBuilder = new StringBuilder();
        LocalDate date = LocalDate.now();
        for (int i = 0; i < period.getPeriodDays(); i++) {
            stringBuilder.append(calculateLastYearRareForDay(date.plusDays(i), type));
        }
        return stringBuilder.toString();
    }

    public String calculateLastYearRareForDay(LocalDate date, RateType type) {
        Map<LocalDate, Float> mapByRateType = resourceReader.giveMapByRateType(type);
        LocalDate newDate = date.minusYears(YEAR);

        while (true) {
            for (Map.Entry<LocalDate, Float> entry : mapByRateType.entrySet()) {
                if (newDate.equals(entry.getKey())) {
                    return prepareResponse(date, entry.getValue()).toString();
                }
            }
            newDate = newDate.minusDays(1);
        }
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
        if (rateTypeList.size() == 1) {
            return calculateLastYearRareForDay(date, rateTypeList.get(0));
        }
        throw new Exception();
    }

    @Override
    public String calculateRateForPeriod(List<RateType> rateTypeList, Period period, OutputType outputType) throws Exception {
        if (rateTypeList.size() == 1) {
            return calculateLastYeaRateForPeriod(period, rateTypeList.get(0));
        }
        throw new Exception();
    }

}
