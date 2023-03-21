package ru.liga.reader;

import ru.liga.constants.CurrencyType;
import ru.liga.object.ExchangeRateObj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ResourceReader {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyy");

    public void readFile(){

        System.out.println("Start reading resource files");

        SortedSet<ExchangeRateObj> sortedSetEUR = new TreeSet<>(Comparator.comparing(ExchangeRateObj::getDate));
        SortedSet<ExchangeRateObj> sortedSetTRY = new TreeSet<>(Comparator.comparing(ExchangeRateObj::getDate));
        SortedSet<ExchangeRateObj> sortedSetUSD = new TreeSet<>(Comparator.comparing(ExchangeRateObj::getDate));

        File fileEUR = new File("/home/ilya/IdeaProjects/home_learning/src/main/resources/EUR.csv");
        File fileTRY = new File("/home/ilya/IdeaProjects/home_learning/src/main/resources/TRY.csv");
        File fileUSD = new File("/home/ilya/IdeaProjects/home_learning/src/main/resources/USD.csv");

        reading(fileEUR, sortedSetEUR);
        reading(fileTRY, sortedSetTRY);
        reading(fileUSD, sortedSetUSD);

        System.out.println("Reading resource files is completed");
    }

    public void reading(File file, SortedSet<ExchangeRateObj> set) {
        String line;
        try {
            FileReader fileReader = new FileReader(file);
            Scanner scanner = new Scanner(fileReader);
            line = scanner.nextLine();

            while (scanner.hasNext()) {
                String[] strings = scanner.nextLine().split(";");

                ExchangeRateObj obj = new ExchangeRateObj();
                obj.setDate(LocalDate.parse(strings[1], formatter));
                obj.setRate(Float.parseFloat(strings[2].replace(',', '.')) / Float.parseFloat(strings[0].replace(" ", "")));

                set.add(obj);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
