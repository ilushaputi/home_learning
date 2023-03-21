package ru.liga.object;

import ru.liga.input.CommandReader;
import ru.liga.resource_reader.ResourceReader;

public class ExchangeRateForecastApp {
    ResourceReader resourceReader = new ResourceReader();
    CommandReader commandReader = new CommandReader();

    public void startApp(){
        System.out.println("Exchange rate forecast app is start");

        resourceReader.readFile();
        System.out.println("Enter \"HELP\" to see help-command");
        commandReader.printHelpCommand();
        commandReader.readCommand();
    }


}
