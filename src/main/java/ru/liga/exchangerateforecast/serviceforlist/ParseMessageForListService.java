package ru.liga.exchangerateforecast.serviceforlist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParseMessageForListService {
    private final Logger logger = LoggerFactory.getLogger(ParseMessageForListService.class);

    private final ParseCommandForListService parseCommandForListService;

    public ParseMessageForListService(ParseCommandForListService parseCommandForListService) {
        this.parseCommandForListService = parseCommandForListService;
    }

    public String parseMessageForStringResponse(String message) {
        String[] arrayMessage = message.split("-");
        try {
            if (arrayMessage.length == 3 || arrayMessage.length == 4) {
                logger.debug("Размер сообщения удовлетворяет условию...");
                return parseCommandForListService.parseCommandForList(arrayMessage);
            } else if (message.equals("help")) {
                logger.debug("Введена команда \"Помощь\" ");
                return sendHelpCommand();
            } else if (message.equals("/start")) {
                return sendStartCommand();
            } else {
                logger.warn("Не удалось спарсить команду...");
                return "Не могу прочитать команду! \nВведите команду с самого начала";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private String sendHelpCommand() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Вы запросили команду \"Помощь\" \n")
                .append("Примеры валют USD,AMD,BGN,EUR,TRY\n")
                .append("Примеры дат: tomorrow/ 22.01.2022\n")
                .append("Примеры периодов: week,month\n")
                .append("Примеры алгоритмов: old,mystic,last_year,internet\n")
                .append("Для вывода графика вконце команды после алгоритма пиши: -output graph. Если не нужен график, после алгоритма ничего писать не нужно\n")
                .append("Ниже представленны примеры команд расчета курса валют по различным алгоритмам\n\n")
                .append("rate USD,TRY -period month -alg old -output list\n\n")
                .append("rate AMD -date tomorrow -alg mystic \n\n")
                .append("rate EUR -date 25.06.2023 -alg last_year \n\n")
                .append("Теперь введите свою команду!");

        return stringBuilder.toString();
    }

    private String sendStartCommand() {
        return "Бот готов к работе!\nВведите команду \"help\" и узнаете, что я умею!";
    }
}
