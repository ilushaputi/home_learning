package ru.liga.exchangerateforecast.serviceforgraph;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.exchangerateforecast.entity.GraphResult;
import ru.liga.exchangerateforecast.exeption.BusinessException;
import ru.liga.exchangerateforecast.service.CommandService;

import java.util.Arrays;

public class ParseCommandGraphService {
    private final Logger logger = LoggerFactory.getLogger(ParseCommandGraphService.class);
    private CommandService commandService;
    private ArgumentAndResponseForGraphService argumentAndResponseForGraphService;

    public ParseCommandGraphService(CommandService commandService, ArgumentAndResponseForGraphService argumentAndResponseForGraphService){
        this.commandService = commandService;
        this.argumentAndResponseForGraphService = argumentAndResponseForGraphService;
    }

    public GraphResult parseCommandForGraph(String[] arrayCommand) {
        logger.debug("Начинаю парсить команду для графика...");
        try {
            return argumentAndResponseForGraphService.prepareArgumentsAndPrepareGraphResult(commandService.parseCommand(arrayCommand));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Ошибка парсинга...");
            logger.error(Arrays.toString(e.getStackTrace()));
            throw new BusinessException("Не могу спарсить команду!\nВведите команду сначала!");
        }
    }
}
