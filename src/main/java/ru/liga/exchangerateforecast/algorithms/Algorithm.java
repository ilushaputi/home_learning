package ru.liga.exchangerateforecast.algorithms;

import ru.liga.exchangerateforecast.entity.ExchangeRateObj;
import ru.liga.exchangerateforecast.enums.Period;
import ru.liga.exchangerateforecast.enums.RateType;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface Algorithm {

    Map<RateType, List<ExchangeRateObj>> calculateRateForDay(LocalDate date, List<RateType> rateTypeList);

    Map<RateType, List<ExchangeRateObj>> calculateRateForPeriod(List<RateType> rateTypeList, Period period);

}
