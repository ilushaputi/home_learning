package ru.liga.exchangerateforecast.enums;

public enum Command {
    HELP("help"),
    START("/start"),
    EXIT("exit"),
    DATE("-date"),
    PERIOD("-period"),
    OUTPUT("-output"),
    ALGORITHM("-alg"),
    RATE("rate");

    private String commandName;

    Command(String commandName) {
        this.commandName = commandName;
    }

    @Override
    public String toString() {
        return commandName;
    }
}
