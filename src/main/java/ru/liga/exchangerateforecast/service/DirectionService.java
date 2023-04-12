package ru.liga.exchangerateforecast.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.exchangerateforecast.algorithms.Algorithm;
import ru.liga.exchangerateforecast.algorithms.AlgorithmFactory;
import ru.liga.exchangerateforecast.entity.ExchangeRateObj;
import ru.liga.exchangerateforecast.enums.AlgorithmType;
import ru.liga.exchangerateforecast.enums.Period;
import ru.liga.exchangerateforecast.enums.RateType;
import ru.liga.exchangerateforecast.exeption.BusinessException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class DirectionService {
    private final Logger logger = LoggerFactory.getLogger(DirectionService.class);

    public Map<RateType, List<ExchangeRateObj>> directToAlgorithm(List<RateType> rateTypeList, LocalDate date, Period period, AlgorithmType algorithmType) throws BusinessException {
        AlgorithmFactory algorithmFactory = new AlgorithmFactory();

        Algorithm algorithm = algorithmFactory.createAlgorithmByAlgorithmType(algorithmType);
        if (date == null) {
            logger.debug("Возвращаю нужный алгоритм для расчета курса на период: " + algorithm.getClass().getName());
            return algorithm.calculateRateForPeriod(rateTypeList, period);
        } else {
            logger.debug("Возвращаю нужный алгоритм для расчета курса на дату: " + algorithm.getClass().getName());
            return algorithm.calculateRateForDay(date, rateTypeList);
        }
    }
}
