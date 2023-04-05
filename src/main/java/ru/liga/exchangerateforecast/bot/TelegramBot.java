package ru.liga.exchangerateforecast.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.exchangerateforecast.enums.Command;
import ru.liga.exchangerateforecast.enums.OutputType;
import ru.liga.exchangerateforecast.enums.Period;
import ru.liga.exchangerateforecast.service.BusinessService;

import javax.print.attribute.standard.MediaSize;

public class TelegramBot extends TelegramLongPollingBot {

    private final String TOKEN_BOT = "6008144807:AAGvRvX4C76FeptwYQXALim79M5A4Vc8adY";
    private final String NAME_BOT = "exchangeLigaBot";

    private final BusinessService businessService = new BusinessService();

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
            Message inputMessage = update.getMessage();
            String chatId = inputMessage.getChatId().toString();
            String response = parseMessage(inputMessage.getText());
            SendMessage outputMessage = new SendMessage();
            outputMessage.setChatId(chatId);
            outputMessage.setText(response);
            try {
                execute(outputMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public String parseMessage(String textMessage) {
        String response;
        if (textMessage.equals(Command.START.toString())) {
            response = "Бот готов к работе! \nНачните вводить команду";
            businessService.clearResources();
        } else if (textMessage.equals(Command.HELP.toString())) {
            response = printHelpInformation();
            businessService.clearResources();
        } else if (textMessage.startsWith(Command.RATE.toString())) {
            response = businessService.parseRateTypes(textMessage);
        } else if (textMessage.startsWith(Command.DATE.toString())) {
            response = businessService.parseDate(textMessage);
        } else if (textMessage.startsWith(Command.PERIOD.toString())) {
            response = businessService.parsePeriod(textMessage);
        } else if (textMessage.startsWith(Command.ALGORITHM.toString())) {
            response = businessService.parseAlgorithm(textMessage);
        } else if (textMessage.startsWith(Command.OUTPUT.toString())) {
            response = businessService.parseOutputType(textMessage);
        } else {
            businessService.clearResources();
            response = "Не удалось распознать команду! \nНачните вводить команду с самого начала!";
        }

        return response;
    }

    public String printHelpInformation() {
        return "Какая-то команда помощи";

    }
}
