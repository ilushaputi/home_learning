package ru.liga.exchangerateforecast.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.exchangerateforecast.enums.Command;
import ru.liga.exchangerateforecast.serviceforlist.ParseCommandForListService;

import java.util.HashMap;
import java.util.Map;

public class CommandService {
    private final Logger logger = LoggerFactory.getLogger(CommandService.class);

    public Map<Command, String> parseCommand(String[] arrayCommand){
        logger.debug("Начинаю парсить команду...");
        Map<Command, String> commandStringMap = new HashMap<>();
        for (String s : arrayCommand) {
            String[] array = s.split(" ");
            commandStringMap.put(Command.valueOf(
                    array[0].startsWith("-") ? array[0].substring(1).toUpperCase() : array[0].toUpperCase()
            ), array[1]);
        }
        logger.debug("Команда готова...");
        return commandStringMap;
    }
}
