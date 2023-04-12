package ru.liga.exchangerateforecast.serviceforgraph;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.exchangerateforecast.entity.ExchangeRateObj;
import ru.liga.exchangerateforecast.entity.GraphResult;
import ru.liga.exchangerateforecast.enums.Command;
import ru.liga.exchangerateforecast.enums.RateType;
import ru.liga.exchangerateforecast.service.ArgumentService;
import ru.liga.exchangerateforecast.service.JFreeFormatterService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ArgumentAndResponseForGraphService {
    private final Logger logger = LoggerFactory.getLogger(ArgumentAndResponseForGraphService.class);
    private final ArgumentService argumentService;

    public ArgumentAndResponseForGraphService(ArgumentService argumentService) {
        this.argumentService = argumentService;
    }

    public GraphResult prepareArgumentsAndPrepareGraphResult(Map<Command, String> map) {
        logger.debug("Начинаю готовить аргуметы для вычисления графика...");
        Map<RateType, List<ExchangeRateObj>> resultMap = argumentService.prepareAllArguments(map);
        JFreeFormatterService jFreeFormatterService = new JFreeFormatterService();
        Map<RateType, List<Float>> mapForGraph = new HashMap<>();
        for (RateType type : resultMap.keySet()) {
            mapForGraph.put(type, resultMap.get(type).stream().map(ExchangeRateObj::getRate).collect(Collectors.toList()));
        }
        return jFreeFormatterService.buildPredication(mapForGraph);
    }
}
