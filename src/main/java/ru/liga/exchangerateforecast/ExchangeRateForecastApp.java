package ru.liga.exchangerateforecast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.liga.exchangerateforecast.bot.TelegramBot;
import ru.liga.exchangerateforecast.service.ArgumentService;
import ru.liga.exchangerateforecast.service.CommandService;
import ru.liga.exchangerateforecast.service.DirectionService;
import ru.liga.exchangerateforecast.serviceforgraph.PrepareResponseForGraphService;
import ru.liga.exchangerateforecast.serviceforgraph.ArgumentAndResponseForGraphService;
import ru.liga.exchangerateforecast.serviceforgraph.ParseCommandGraphService;
import ru.liga.exchangerateforecast.serviceforlist.*;
import ru.liga.exchangerateforecast.serviceforgraph.ParseMessageForGraphService;

public class ExchangeRateForecastApp {
    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateForecastApp.class);

    public static void main(String[] args) {
        logger.info("Приложение начинает работу...");
        try {
            logger.debug("Создаем телеграмм бота...");
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new TelegramBot(
                                            new ParseMessageForListService(
                                                    new ParseCommandForListService(
                                                            new CommandService(),
                                                            new ArgumentAndResponseForListService(
                                                                    new PrepareResponseForListService(),
                                                                    new ArgumentService(
                                                                            new DirectionService()
                                                                    )))),
                                            new PrepareResponseForGraphService(),
                                            new ParseMessageForGraphService(
                                                    new ParseCommandGraphService(
                                                            new CommandService(),
                                                            new ArgumentAndResponseForGraphService(
                                                                    new ArgumentService(
                                                                            new DirectionService()
                                                                    ))))));
            logger.debug("Телеграмм бот успешно создан...");
        } catch (TelegramApiException e) {
            logger.error("Возникла ошибка пр создании бота...");
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
