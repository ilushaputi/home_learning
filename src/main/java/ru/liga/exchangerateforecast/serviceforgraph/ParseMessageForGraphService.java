package ru.liga.exchangerateforecast.serviceforgraph;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.exchangerateforecast.entity.GraphResult;
import ru.liga.exchangerateforecast.exeption.BusinessException;
import ru.liga.exchangerateforecast.serviceforlist.ParseMessageForListService;

public class ParseMessageForGraphService {
    private final Logger logger = LoggerFactory.getLogger(ParseMessageForListService.class);
    private final ParseCommandGraphService parseCommandForGraphService;

    public ParseMessageForGraphService(ParseCommandGraphService parseCommandForGraphService) {
        this.parseCommandForGraphService = parseCommandForGraphService;
    }

    public GraphResult parseMessageForGraphResponse(String message) {
        String[] arrayMessage = message.split("-");
        try {
            if (arrayMessage.length == 3 || arrayMessage.length == 4) {
                logger.debug("Размер сообщения удовлетворяет условию...");
                return parseCommandForGraphService.parseCommandForGraph(arrayMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new BusinessException("Ошибка в формировании графика!");
    }
}
