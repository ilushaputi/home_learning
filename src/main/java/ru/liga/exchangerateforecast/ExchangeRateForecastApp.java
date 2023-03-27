package ru.liga.exchangerateforecast;

import ru.liga.exchangerateforecast.reader.CommandReader;

public class ExchangeRateForecastApp {
    private static final ExchangeRateForecastApp app = new ExchangeRateForecastApp();
//    private final CommandReader commandReader = new CommandReader();

    public static void main(String[] args) {
        System.out.println("Exchange rate forecast app is start");
        app.startApp();
    }

    public void startApp(){

        CommandReader commandReader = new CommandReader();
        System.out.println("Enter \"HELP\" to see help-command");

        commandReader.printHelpCommand();
        commandReader.readCommand();
    }
}
