package ru.liga;

import ru.liga.reader.CommandReader;

public class ExchangeRateForecastApp {
    private static final ExchangeRateForecastApp app = new ExchangeRateForecastApp();
    private final CommandReader COMMANDREADER = new CommandReader();

    public static void main(String[] args) {
        System.out.println("Exchange rate forecast app is start");
        app.startApp();
    }

    public void startApp(){


        System.out.println("Enter \"HELP\" to see help-command");

        COMMANDREADER.printHelpCommand();
        COMMANDREADER.readCommand();
    }


}
