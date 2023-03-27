package ru.liga.exchangerateforecast.reader;

import ru.liga.exchangerateforecast.algorithms.RateCalculationAlgorithm;
import ru.liga.exchangerateforecast.enums.Command;
import ru.liga.exchangerateforecast.enums.Period;
import ru.liga.exchangerateforecast.enums.RateType;

import java.time.LocalDate;
import java.util.Scanner;

public class CommandReader {
    private final RateCalculationAlgorithm rateCalculationAlgorithm = new RateCalculationAlgorithm();

    public void readCommand() {
        boolean isReadyToReadCommand = true;
        while (isReadyToReadCommand) {
            System.out.println("Enter your command");
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();

            if (command.contains(Command.HELP.name())) {
                printHelpCommand();
            } else if (command.contains(Command.RATE.name().toLowerCase())) {
                if (command.contains(Period.TOMORROW.name().toLowerCase())) {
                    rateCalculationAlgorithm.calculateTomorrowRateByRateType(RateType.giveRateTypeByCommand(command), LocalDate.now());
                } else if (command.contains(Period.WEEK.name().toLowerCase())) {
                    rateCalculationAlgorithm.calculateWeekRateByRateType(RateType.giveRateTypeByCommand(command));
                } else {
                    System.out.println("Unknown command, repeat please");
                }
            } else if (command.contains(Command.EXIT.name())) {
                isReadyToReadCommand = false;
                scanner.close();
                System.out.println("Exit from application");
            } else {
                System.out.println("Unknown command, please repeat");
            }
        }
    }

    public void printHelpCommand() {
        System.out.println("Enter: rate \"three-letter currency designation, for example USD\" tomorrow - dollar exchange rate forecast for tomorrow");
        System.out.println("Enter rate \"three-letter currency designation, for example USD\" week - dollar exchange rate forecast for week");
        System.out.println("Enter \"exit\" to exit from application");
    }
}
