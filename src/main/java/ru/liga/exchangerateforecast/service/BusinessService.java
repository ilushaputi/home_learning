package ru.liga.exchangerateforecast.service;

import ru.liga.exchangerateforecast.algorithms.Algorithm;
import ru.liga.exchangerateforecast.algorithms.AlgorithmFactory;
import ru.liga.exchangerateforecast.enums.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BusinessService {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyy");
    private final AlgorithmFactory algorithmFactory = new AlgorithmFactory();
    private List<RateType> rateTypeList = new ArrayList<>();
    private LocalDate date = null;
    private Period period = null;
    private AlgorithmType algorithmType = null;
    private OutputType outputType = null;

    public RateType readRateType(String type) throws Exception {
        try {
            return RateType.valueOf(type);
        } catch (Exception e) {
            clearResources();
            e.printStackTrace();
            throw new Exception("Не удалось считать валюты (валюты)! \nНачните вводить команду с самого начала!");
        }
    }

    public String parseRateTypes(String textMessage) {
        String[] arrayRateTypes = textMessage.substring(5).split(",");

        if (!rateTypeList.isEmpty()) {
            clearResources();
            return "Вы уже вводили валюты ранее! \nНачните вводить команду с самого начала!";
        }
        for (String type : arrayRateTypes) {
            RateType rateType = null;
            try {
                rateType = readRateType(type);
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
            rateTypeList.add(rateType);
        }

        if (rateTypeList.isEmpty()) {
            clearResources();
            return "Не удалось считать валюты (валюты)!\nНачните вводить команду с самого начала!";
        }
        return "Валюта (валюты) успешно считаны! \nВведите дату или период";
    }

    public String parseDate(String textMessage) {
        String[] arrayStringDates = textMessage.split(" ");
        if (date != null) {
            clearResources();
            return "Вы уже вводили дату ранее! \nНачните вводить команду с самого начала!";
        }
        if (arrayStringDates[1].equals(DateType.TOMORROW.toString())) {
            date = LocalDate.now().plusDays(1);
            return "Указанная дата успешно считана! \nВедите алгоритм расчета курса";
        } else if (isValidDate(arrayStringDates[1])) {
            date = LocalDate.parse(arrayStringDates[1], FORMATTER);
            return "Указанная дата успешно считана! \nВедите алгоритм расчета курса";
        } else {
            clearResources();
            return "Неизвестный формат даты. \nНачните вводить команду с самого начала!";
        }
    }

    public Period readPeriod(String period) throws Exception {
        try {
            return Period.valueOf(period.toUpperCase());
        } catch (Exception e) {
            clearResources();
            e.printStackTrace();
            throw new Exception("Не удалось считать период! \nНачните вводить команду с самого начала!");
        }
    }

    public String parsePeriod(String textMessage) {
        String[] arrayPeriods = textMessage.split(" ");
        if (period != null) {
            clearResources();
            return "Вы уже вводили период расчета ранее! \nНачните вводить команду с самого начала!";
        }
        try {
            period = readPeriod(arrayPeriods[1]);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "Период успешно считан! \nВедите алгоритм расчета курса";
    }

    public AlgorithmType readAlgorithm(String textMessage) throws Exception {
        try {
            return AlgorithmType.valueOf(textMessage.toUpperCase());
        } catch (Exception e) {
            clearResources();
            e.printStackTrace();
            throw new Exception("Не удалось считать алгоритм! \nНачните вводить команду с самого начала!");
        }
    }

    public String parseAlgorithm(String textMessage) {
        String[] arrayAlgs = textMessage.split(" ");

        if (algorithmType != null) {
            clearResources();
            return "Вы уже вводили алгоритм ранее! \nНачните вводить команду с самого начала!";
        }
        try {
            algorithmType = readAlgorithm(arrayAlgs[1]);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        String response = null;
        if (algorithmType == null) {
            clearResources();
            response = "Неизвестный алгоритм.\nНачните вводить команду с самого начала!";
        } else {
            if (date != null) {
                try {
                    Algorithm algorithm = algorithmFactory.createAlgorithmByAlgorithmType(algorithmType);
                    response = "Алгоритм успешно считан \nПроизвожу рассчет";
                    response = response + " \n" + algorithm.calculateRateForDay(date, rateTypeList);
                    response = response + "\nВы получили готовый расчет курса. \nНачните вводить команду с самого начала!";
                } catch (Exception e) {
                    e.printStackTrace();
                    clearResources();
                    return e.getMessage();
                }
            } else if (period != null) {
                return "Алгоритм успешно считан! \nВведите тип вывода расчетов.";
            }
        }
        clearResources();
        return response;
    }

    public OutputType readOutputType(String textMessage) throws Exception {
        try {
            return OutputType.valueOf(textMessage.toUpperCase());
        } catch (Exception e) {
            clearResources();
            e.printStackTrace();
            throw new Exception("Не удалось считать тип вывода! \nНачните вводить команду с самого начала!");
        }
    }

    public String parseOutputType(String textMessage) {
        String[] arrayOutputTypes = textMessage.split(" ");
        try {
            outputType = readOutputType(arrayOutputTypes[1]);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        String response = null;

        if (outputType == null) {
            clearResources();
            return "Неизвестный типо вывода. \nПовторите команду сначала";
        } else {
            response = "Тип вывода успешно считан \n";
            try {
                Algorithm algorithm = algorithmFactory.createAlgorithmByAlgorithmType(algorithmType);
                response += algorithm.calculateRateForPeriod(rateTypeList, period, outputType);
                response += "\nВы получили готовый расчет курса. \nНачните вводить команду с самого начала!";
                clearResources();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    public void clearResources() {
        rateTypeList.clear();
        date = null;
        period = null;
        algorithmType = null;
        outputType = null;
    }

    public boolean isValidDate(String dateString) {
        return dateString.matches("^(0[1-9]|[12][0-9]|3[0-1]).(0[1-9]|1[0-9]).\\d{4}");
    }

}
