package ru.liga.exchangerateforecast.serviceforlist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.exchangerateforecast.exeption.BusinessException;
import ru.liga.exchangerateforecast.service.CommandService;

public class ParseCommandForListService {
    private final Logger logger = LoggerFactory.getLogger(ParseCommandForListService.class);

    private CommandService commandService;
    private final ArgumentAndResponseForListService argumentAndResponseForListService;

    public ParseCommandForListService(CommandService commandService, ArgumentAndResponseForListService argumentAndResponseForListService) {
        this.argumentAndResponseForListService = argumentAndResponseForListService;
        this.commandService = commandService;
    }

    public String parseCommandForList(String[] arrayCommand) {
        try {
            return argumentAndResponseForListService.prepareArgumentsAndPrepareResponse(commandService.parseCommand(arrayCommand));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("Не могу спарсить команду!\nВведите команду сначала!");
        }
    }

}
