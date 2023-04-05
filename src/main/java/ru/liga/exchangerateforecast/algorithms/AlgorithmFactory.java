package ru.liga.exchangerateforecast.algorithms;

import ru.liga.exchangerateforecast.enums.AlgorithmType;

public class AlgorithmFactory {
    public Algorithm createAlgorithmByAlgorithmType(AlgorithmType type) throws Exception {
        switch (type) {
            case OLD:
                return new OldRateCalculationAlgorithm();
            case LAST_YEAR:
                return new LastYearRateCalculationAlgorithm();
            case MYSTIC:
                return new MysticalRateCalculationAlgorithm();
            case INTERNET:
                return new InternetAlg();
        }

        throw new Exception("Неизвестный алгоритм.\nВсе данные обнулены!\nНачните вводить команду с самого начала!");
    }
}
