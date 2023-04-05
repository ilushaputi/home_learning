package ru.liga.exchangerateforecast.reader;

import ru.liga.exchangerateforecast.enums.FileName;
import ru.liga.exchangerateforecast.enums.RateType;
import ru.liga.exchangerateforecast.entity.ExchangeRateObj;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ResourceReader {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyy");

    private final List<ExchangeRateObj> listEUR = readingFileByFileNameForList(FileName.FILE_EUR);
    private final List<ExchangeRateObj> listTRY = readingFileByFileNameForList(FileName.FILE_TRY);
    private final List<ExchangeRateObj> listUSD = readingFileByFileNameForList(FileName.FILE_USD);

    private final Map<LocalDate, Float> mapEUR = readingFileByFileNameForMap(FileName.FILE_EUR);
    private final Map<LocalDate, Float> mapUSD = readingFileByFileNameForMap(FileName.FILE_USD);
    private final Map<LocalDate, Float> mapTRY = readingFileByFileNameForMap(FileName.FILE_TRY);
    private final Map<LocalDate, Float> mapBGN = readingFileByFileNameForMap(FileName.FILE_BGN);
    private final Map<LocalDate, Float> mapAMD = readingFileByFileNameForMap(FileName.FILE_AMD);

    public List<ExchangeRateObj> getListEUR() {
        return listEUR;
    }

    public List<ExchangeRateObj> getListTRY() {
        return listTRY;
    }

    public List<ExchangeRateObj> getListUSD() {
        return listUSD;
    }

    public Map<LocalDate, Float> getMapEUR() {
        return mapEUR;
    }

    public Map<LocalDate, Float> getMapUSD() {
        return mapUSD;
    }

    public Map<LocalDate, Float> getMapTRY() {
        return mapTRY;
    }

    public Map<LocalDate, Float> getMapBGN() {
        return mapBGN;
    }

    public Map<LocalDate, Float> getMapAMD() {
        return mapAMD;
    }


    public Map<LocalDate, Float> readingFileByFileNameForMap(FileName filename) {
        System.out.println("Start reading " + filename);

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename.toString());
        Map<LocalDate, Float> map = new HashMap<>();

        Scanner scanner = new Scanner(inputStream);
        String line = scanner.nextLine();

        while (scanner.hasNext()) {

            String nextLine = scanner.nextLine();

            if (nextLine.isEmpty()) {
                continue;
            }
            String[] strings = nextLine.split(";");

            map.put(LocalDate.parse(strings[1], FORMATTER), Float.parseFloat(strings[2].replace(',', '.'))
                    / Float.parseFloat(strings[0].replace(" ", "")));

            ExchangeRateObj obj = new ExchangeRateObj();
            obj.setDate(LocalDate.parse(strings[1], FORMATTER));
            obj.setRate(Float.parseFloat(strings[2].replace(',', '.'))
                    / Float.parseFloat(strings[0].replace(" ", "")));
        }

        scanner.close();
        System.out.println("Reading file " + filename + " is completed");
        return map;
    }

    public List<ExchangeRateObj> readingFileByFileNameForList(FileName filename) {
        System.out.println("Start reading " + filename);

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename.toString());
        List<ExchangeRateObj> exchangeRateObjList = new ArrayList<>();

        Scanner scanner = new Scanner(inputStream);
        String line = scanner.nextLine();

        while (scanner.hasNext()) {

            String nextLine = scanner.nextLine();

            if (nextLine.isEmpty()) {
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

    public List<ExchangeRateObj> giveListByRateType(RateType type) {
        if (type.equals(RateType.TRY)) return getListTRY();
        if (type.equals(RateType.EUR)) return getListEUR();
        if (type.equals(RateType.USD)) return getListUSD();

        throw new IllegalArgumentException("unknown currency type");
    }

    public Map<LocalDate, Float> giveMapByRateType(RateType type) {
        if (type.equals(RateType.TRY)) return getMapTRY();
        if (type.equals(RateType.EUR)) return getMapEUR();
        if (type.equals(RateType.USD)) return getMapUSD();
        if (type.equals(RateType.AMD)) return getMapAMD();
        if (type.equals(RateType.BGN)) return getMapBGN();

        throw new IllegalArgumentException("unknown currency type");
    }
}
