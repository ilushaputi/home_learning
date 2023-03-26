package ru.liga.reader;

import ru.liga.enums.Command;
import ru.liga.algorithms.RateCalculationAlgorithm;
import ru.liga.enums.RateType;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Scanner;

public class CommandReader {
    private final RateCalculationAlgorithm rateCalculationAlgorithm = new RateCalculationAlgorithm();


    public void readCommand() {
        boolean isReadyToReadCommand = true;
        while (isReadyToReadCommand){
            System.out.println("Enter your command");
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();

            if (command.contains(Command.HELP.name())){
                printHelpCommand();
            } else if (command.contains(Command.tomorrow.name())){
                rateCalculationAlgorithm.calculateTomorrowRateByRateType(Objects.requireNonNull(RateType.giveRateTypeByCommand(command)), LocalDate.now());
            } else if (command.contains(Command.week.name())){
                rateCalculationAlgorithm.calculateWeekRateByRateType(Objects.requireNonNull(RateType.giveRateTypeByCommand(command)));
            } else if (command.contains(Command.exit.name())){
                isReadyToReadCommand = false;
                scanner.close();
                System.out.println("Exit from application");
                System.exit(0);
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
