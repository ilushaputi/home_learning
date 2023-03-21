package ru.liga.input;

import ru.liga.logic.BusinessLogic;
import ru.liga.emuns.RateType;

import java.util.Scanner;

public class CommandReader {
    BusinessLogic businessLogic = new BusinessLogic();

    public void readCommand() {
        System.out.println("Enter your command");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();

        if (command.contains("HELP")){
            printHelpCommand();
            readCommand();
        }
        else if (command.contains("week")){
            businessLogic.getWeekRate(getRateType(command));
            readCommand();
        } else if (command.contains("tomorrow")){
            businessLogic.getTomorrowRate(getRateType(command));
            readCommand();
        } else if ("0".equals(command)) {
            scanner.close();
            System.out.println("Exit from application");
        } else {
            System.out.println("Unknown command, please repeat");
            readCommand();
        }
        scanner.close();
    }

    public RateType getRateType(String command){
        if (command.contains("TRY")) {
            return RateType.TRY;
        } else if (command.contains("EUR")) {
            return RateType.EUR;
        } else if (command.contains("USD")) {
            return RateType.USD;
        }
        return null;
    }

    public void printHelpCommand() {
        System.out.println("Enter: rate \"three-letter currency designation, for example USD\" tomorrow - dollar exchange rate forecast for tomorrow");
        System.out.println("Enter rate \"three-letter currency designation, for example USD\" week - dollar exchange rate forecast for week");
        System.out.println("Enter 0 to exit from application");
    }
}
