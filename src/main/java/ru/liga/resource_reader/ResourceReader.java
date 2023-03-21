package ru.liga.resource_reader;

import ru.liga.object.ExchangeRateObj;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ResourceReader {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyy");

    public static List<ExchangeRateObj> listEUR = new ArrayList<>();
    public static List<ExchangeRateObj> listTRY = new ArrayList<>();
    public static List<ExchangeRateObj> listUSD = new ArrayList<>();

    public void readFile(){

        System.out.println("Start reading resource files");

        File fileEUR = new File("/home/ilya/IdeaProjects/home_learning/src/main/resources/EUR.csv");
        File fileTRY = new File("/home/ilya/IdeaProjects/home_learning/src/main/resources/TRY.csv");
        File fileUSD = new File("/home/ilya/IdeaProjects/home_learning/src/main/resources/USD.csv");

        reading(fileEUR, listEUR);
        reading(fileTRY, listTRY);
        reading(fileUSD, listUSD);

        System.out.println("Reading resource files is completed");
    }

    public List<ExchangeRateObj> reading(File file, List<ExchangeRateObj> list) {
        String line;
        try {
            FileReader fileReader = new FileReader(file);
            Scanner scanner = new Scanner(fileReader);
            line = scanner.nextLine();

            while (scanner.hasNext()) {

                String nextLine = scanner.nextLine();

                if (nextLine.isEmpty()){
                    continue;
                }
                String[] strings = nextLine.split(";");

                ExchangeRateObj obj = new ExchangeRateObj();
                obj.setDate(LocalDate.parse(strings[1], formatter));
                obj.setRate(Float.parseFloat(strings[2].replace(',', '.'))
                            / Float.parseFloat(strings[0].replace(" ", "")));

                list.add(obj);
            }

            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list.stream().sorted(Comparator.comparing(ExchangeRateObj::getDate)).collect(Collectors.toList());
    }
}
