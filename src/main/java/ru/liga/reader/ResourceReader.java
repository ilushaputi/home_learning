package ru.liga.reader;

import ru.liga.enums.FileName;
import ru.liga.enums.RateType;
import ru.liga.entity.ExchangeRateObj;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ResourceReader {
    public   DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyy");

    private final List<ExchangeRateObj> listEUR = readingFileByFileName(FileName.FILE_EUR);
    private final List<ExchangeRateObj> listTRY = readingFileByFileName(FileName.FILE_TRY);
    private final List<ExchangeRateObj> listUSD = readingFileByFileName(FileName.FILE_USD);

    public List<ExchangeRateObj> getListEUR() {
        return listEUR;
    }

    public List<ExchangeRateObj> getListTRY() {
        return listTRY;
    }

    public List<ExchangeRateObj> getListUSD() {
        return listUSD;
    }

    public List<ExchangeRateObj> readingFileByFileName(FileName filename){
        System.out.println("Start reading " + filename);

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename.toString());
        List<ExchangeRateObj> exchangeRateObjList = new ArrayList<>();

        Scanner scanner = new Scanner(inputStream);
        String line = scanner.nextLine();

        while (scanner.hasNext()) {

            String nextLine = scanner.nextLine();

            if (nextLine.isEmpty()){
                continue;
            }
            String[] strings = nextLine.split(";");

            ExchangeRateObj obj = new ExchangeRateObj();
            obj.setDate(LocalDate.parse(strings[1], FORMATTER));
            obj.setRate(Float.parseFloat(strings[2].replace(',', '.'))
                    / Float.parseFloat(strings[0].replace(" ", "")));

            exchangeRateObjList.add(obj);
        }

        scanner.close();
        System.out.println("Reading file " + filename + " is completed");
        return exchangeRateObjList;
    }

    public List<ExchangeRateObj> giveListByRateType(RateType type){
        if (type.equals(RateType.TRY)) return getListTRY();
        if (type.equals(RateType.EUR)) return getListEUR();
        if (type.equals(RateType.USD)) return getListUSD();
        return null;
    }
}
