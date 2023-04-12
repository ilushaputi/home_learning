package ru.liga.exchangerateforecast.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.exchangerateforecast.enums.AlgorithmType;
import ru.liga.exchangerateforecast.exeption.BusinessException;
import ru.liga.exchangerateforecast.reader.ResourceReader;

public class AlgorithmFactory {
    private static final Logger logger = LoggerFactory.getLogger(AlgorithmFactory.class);

    public Algorithm createAlgorithmByAlgorithmType(AlgorithmType type) throws BusinessException {
        switch (type) {
            case OLD:
                logger.debug("Создан алгоритм: Старый");
                return new OldRateCalculationAlgorithm(new ResourceReader());
            case LAST_YEAR:
                logger.debug("Создан алгоритм: Прошлогодний");
                return new LastYearRateCalculationAlgorithm(new ResourceReader());
            case MYSTIC:
                logger.debug("Создан алгоритм: Мистический");
                return new MysticalRateCalculationAlgorithm(new ResourceReader());
            case INTERNET:
                logger.debug("Создан алгоритм: Алгоритм из интернета");
                return new InternetRateCalculationAlgorithm();
        }
        logger.error("Не удалось создать алгоритм!");
        throw new BusinessException("Не могу создать алгоритм!\nВедите команду сначала");
    }
}
