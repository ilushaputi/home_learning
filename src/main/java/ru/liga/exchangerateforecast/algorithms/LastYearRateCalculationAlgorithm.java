package ru.liga.exchangerateforecast.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.exchangerateforecast.entity.ExchangeRateObj;
import ru.liga.exchangerateforecast.enums.Period;
import ru.liga.exchangerateforecast.enums.RateType;
import ru.liga.exchangerateforecast.reader.ResourceReader;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LastYearRateCalculationAlgorithm implements Algorithm {
    private final Logger logger = LoggerFactory.getLogger(OldRateCalculationAlgorithm.class);
    private final int YEAR = 1;
    private ResourceReader resourceReader;

    public LastYearRateCalculationAlgorithm(ResourceReader resourceReader) {
        this.resourceReader = resourceReader;
    }

    public List<ExchangeRateObj> calculateLastYeaRateForPeriod(Period period, RateType type) {
        logger.debug("Расчитываю курс {},  на период {}...", type.toString(), period.toString());
        LocalDate date = LocalDate.now();
        List<ExchangeRateObj> exchangeRateObjList = new ArrayList<>();
        for (int i = 0; i < period.getPeriodDays(); i++) {
            exchangeRateObjList.addAll(calculateLastYearRareForDay(date.plusDays(i), type));
        }
        logger.debug("Расчет курса {},  на период {} успешно закончен", type.toString(), period.toString());
        return exchangeRateObjList;
    }

    public List<ExchangeRateObj> calculateLastYearRareForDay(LocalDate date, RateType type) {
        logger.debug("Расчитываю курс {},  на дату {}...", type.toString(), date.toString());
        Map<LocalDate, Float> mapByRateType = resourceReader.giveMapByRateType(type);
        LocalDate newDate = date.minusYears(YEAR);
        List<ExchangeRateObj> exchangeRateObjList = new ArrayList<>();

        while (true) {
            for (Map.Entry<LocalDate, Float> entry : mapByRateType.entrySet()) {
                if (newDate.equals(entry.getKey())) {
                    exchangeRateObjList.add(new ExchangeRateObj(date, entry.getValue()));
                    logger.debug("Расчет курса {},  на дату {} успешно закончен", type.toString(), date.toString());
                    return exchangeRateObjList;
                }
            }
            newDate = newDate.minusDays(1);
        }
    }

    @Override
    public Map<RateType, List<ExchangeRateObj>> calculateRateForDay(LocalDate date, List<RateType> rateTypeList) {
        logger.debug("Начинаю расчет курсов: {}, на дату: {}", rateTypeList.toString(), date.toString());
        Map<RateType, List<ExchangeRateObj>> outputMap = new HashMap<>();
        for (RateType type : rateTypeList) {
            outputMap.put(type, calculateLastYearRareForDay(date, type));
        }
        return outputMap;
    }

    @Override
    public Map<RateType, List<ExchangeRateObj>> calculateRateForPeriod(List<RateType> rateTypeList, Period period) {
        logger.debug("Начинаю расчет курсов: {},  на период: {}", rateTypeList.toString(), period.toString());
        Map<RateType, List<ExchangeRateObj>> outputMap = new HashMap<>();
        for (RateType type : rateTypeList) {
            outputMap.put(type, calculateLastYeaRateForPeriod(period, type));
        }
        return outputMap;
    }

}
