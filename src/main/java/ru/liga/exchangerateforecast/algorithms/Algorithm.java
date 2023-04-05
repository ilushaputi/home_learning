package ru.liga.exchangerateforecast.algorithms;

import ru.liga.exchangerateforecast.enums.OutputType;
import ru.liga.exchangerateforecast.enums.Period;
import ru.liga.exchangerateforecast.enums.RateType;

import java.time.LocalDate;
import java.util.List;

public interface Algorithm {

    String calculateRateForDay(LocalDate date, List<RateType> rateTypeList) throws Exception;

    String calculateRateForPeriod(List<RateType> rateTypeList, Period period, OutputType outputType) throws Exception;

}
