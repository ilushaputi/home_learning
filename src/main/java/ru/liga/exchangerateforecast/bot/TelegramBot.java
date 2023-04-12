package ru.liga.exchangerateforecast.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.exchangerateforecast.entity.GraphResult;
import ru.liga.exchangerateforecast.serviceforgraph.PrepareResponseForGraphService;
import ru.liga.exchangerateforecast.serviceforlist.ParseMessageForListService;
import ru.liga.exchangerateforecast.serviceforgraph.ParseMessageForGraphService;

public class TelegramBot extends TelegramLongPollingBot {
    private final Logger logger = LoggerFactory.getLogger(TelegramBot.class);

    private final String TOKEN_BOT = "6008144807:AAGvRvX4C76FeptwYQXALim79M5A4Vc8adY";
    private final String NAME_BOT = "exchangeLigaBot";

    private final ParseMessageForListService parseMessageForListService;
    private final PrepareResponseForGraphService prepareResponseForGraphService;
    private final ParseMessageForGraphService parseMessageForGraphService;

    public TelegramBot(ParseMessageForListService parseMessageForListService, PrepareResponseForGraphService prepareResponseForGraphService, ParseMessageForGraphService parseMessageForGraphService) {
        this.parseMessageForListService = parseMessageForListService;
        this.prepareResponseForGraphService = prepareResponseForGraphService;
        this.parseMessageForGraphService = parseMessageForGraphService;
    }

    @Override
    public String getBotUsername() {
        return NAME_BOT;
    }

    @Override
    public String getBotToken() {
        return TOKEN_BOT;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            logger.debug("Принял сообщение: " + update.getMessage().getText());
            Message inputMessage = update.getMessage();
            String chatId = inputMessage.getChatId().toString();
            if (inputMessage.getText().endsWith("graph")) {
                GraphResult response = parseMessageForGraphResponse(inputMessage.getText());
                sendResponseGraph(chatId, prepareResponseForGraphService.preparePhoto(response));
            } else {
                String response = parseMessageForStringResponse(inputMessage.getText());
                sendResponseString(chatId, response);
            }
        }
    }

    private void sendResponseGraph(String chatId, SendPhoto sendPhoto) {
        sendPhoto.setChatId(chatId);
        try {
            logger.debug("Попытка отправить ответ...");
            execute(sendPhoto);
            logger.debug("Ответ успешно отправлен!");
        } catch (TelegramApiException e) {
            logger.error("Не удалось отправить сообщение!");
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendResponseString(String chatId, String response) {
        SendMessage outputMessage = new SendMessage();
        outputMessage.setChatId(chatId);
        outputMessage.setText(response);
        try {
            logger.debug("Попытка отправить ответ...");
            execute(outputMessage);
            logger.debug("Ответ успешно отправлен!");
        } catch (TelegramApiException e) {
            logger.error("Не удалось отправить сообщение!");
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private String parseMessageForStringResponse(String textMessage) {
        logger.debug("Начинаю парсить сообщение...");
        return parseMessageForListService.parseMessageForStringResponse(textMessage);
    }

    private GraphResult parseMessageForGraphResponse(String textMessage) {
        logger.debug("Начинаю парсить сообщение...");
        return parseMessageForGraphService.parseMessageForGraphResponse(textMessage);
    }
}
