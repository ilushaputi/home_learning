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

public class OldRateCalculationAlgorithm implements Algorithm {
    private final Logger logger = LoggerFactory.getLogger(OldRateCalculationAlgorithm.class);
    private final int DAYS_PER_WEEK = 7;
    private final ResourceReader resourceReader;

    public OldRateCalculationAlgorithm(ResourceReader resourceReader) {
        this.resourceReader = resourceReader;
    }

    public List<ExchangeRateObj> calculateOldRateForDay(LocalDate date, RateType rateType) {
        logger.debug("Расчитываю курс {},  на дату {}...", rateType.toString(),  date.toString());
        List<ExchangeRateObj> exchangeRateObjList = resourceReader.giveListByRateType(rateType);
        float courseAvg = 0.0f;
        List<ExchangeRateObj> outputList = new ArrayList<>();

        if (date.equals(LocalDate.now().plusDays(1))) {
            for (int i = 0; i < DAYS_PER_WEEK; i++) {
                courseAvg += exchangeRateObjList.get(i).getRate();
            }
            outputList.add(new ExchangeRateObj(date, courseAvg / DAYS_PER_WEEK));
        } else {
            return calculateOldRateForPeriod(rateType, null, date);
        }
        logger.debug("Расчет курса {},  на дату {} успешно закончен", rateType.toString(),  date.toString());
        return outputList;
    }

    public ExchangeRateObj calculateTomorrowRateForWeek(List<ExchangeRateObj> list, LocalDate date) {
        float courseAvg = 0.0f;
        for (int j = 0; j < DAYS_PER_WEEK; j++) {
            courseAvg += list.get(j).getRate();
        }
        return prepareExchangeRateObject(courseAvg, date);
    }

    public ExchangeRateObj prepareExchangeRateObject(float rate7Days, LocalDate today) {
        ExchangeRateObj exchangeRateObj = new ExchangeRateObj();
        exchangeRateObj.setDate(today.plusDays(1L));
        exchangeRateObj.setRate((rate7Days / DAYS_PER_WEEK));

        return exchangeRateObj;
    }

    public List<ExchangeRateObj> calculateOldRateForPeriod(RateType rateType, Period period, LocalDate date) {
        String secondLogParameter = period != null ? period.toString() : date.toString();
        logger.debug("Расчитываю курс {},  на {}...", rateType.toString(),  secondLogParameter);
        List<ExchangeRateObj> calculationList = new ArrayList<>();
        List<ExchangeRateObj> outputList = new ArrayList<>();
        for (int i = 0; i < DAYS_PER_WEEK; i++) {
            calculationList.add(resourceReader.giveListByRateType(rateType).get(i));
        }

        if (date.equals(LocalDate.now())) {
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
        logger.debug("Расчет курса {},  на {} успешно закончен", rateType.toString(), secondLogParameter);
        return outputList;
    }

    @Override
    public Map<RateType, List<ExchangeRateObj>> calculateRateForDay(LocalDate date, List<RateType> rateTypeList) {
        logger.debug("Начинаю расчет курсов: {}, на дату: {}", rateTypeList.toString(),  date.toString());
        Map<RateType, List<ExchangeRateObj>> outputMap = new HashMap<>();
        for (RateType type : rateTypeList) {
            outputMap.put(type, calculateOldRateForDay(date, type));
        }
        return outputMap;
    }

    @Override
    public Map<RateType, List<ExchangeRateObj>> calculateRateForPeriod(List<RateType> rateTypeList, Period period) {
        logger.debug("Начинаю расчет курсов: {},  на период: {}", rateTypeList.toString(),  period.toString());
        Map<RateType, List<ExchangeRateObj>> outputMap = new HashMap<>();
        for (RateType type : rateTypeList) {
            outputMap.put(type, calculateOldRateForPeriod(type, period, LocalDate.now()));
        }
        return outputMap;
    }
}
