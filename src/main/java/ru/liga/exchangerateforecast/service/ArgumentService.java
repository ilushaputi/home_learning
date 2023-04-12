package ru.liga.exchangerateforecast.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.exchangerateforecast.entity.ExchangeRateObj;
import ru.liga.exchangerateforecast.enums.*;
import ru.liga.exchangerateforecast.exeption.BusinessException;
import ru.liga.exchangerateforecast.serviceforlist.ArgumentAndResponseForListService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ArgumentService {
    private DirectionService directionService;
    private final Logger logger = LoggerFactory.getLogger(ArgumentService.class);
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyy");

    public ArgumentService(DirectionService directionService){
        this.directionService = directionService;
    }

    public Map<RateType, List<ExchangeRateObj>> prepareAllArguments(Map<Command, String> map){
        logger.debug("Начинаю готовить аргуметы для вычисления ответа...");
        try {
            List<RateType> rateTypeList = parseRateTypes(map.get(Command.RATE));
            LocalDate date = map.get(Command.DATE) == null ? null : parseDate(map.get(Command.DATE));
            Period period = map.get(Command.PERIOD) == null ? null : parsePeriod(map.get(Command.PERIOD));
            AlgorithmType algorithmType = parseAlgorithm(map.get(Command.ALG));
            logger.debug("Аргументы для вычисления ответа готовы...");

            return directionService.directToAlgorithm(rateTypeList, date, period, algorithmType);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(Arrays.toString(e.getStackTrace()));
            throw new BusinessException("Не могу подготовить аргументы!\nВведите команду сначала!");
        }
    }

    private List<RateType> parseRateTypes(String types) {
        List<RateType> list = new ArrayList<>();
        logger.debug("Считываю типы валют...");
        try {
            Arrays.stream(types.split(",")).forEach(s -> list.add(RateType.valueOf(s.toUpperCase())));
            logger.debug("Типы валют успешно считаны...");
        } catch (Exception e) {
            logger.error("Не удалось считать валюты!");
            logger.error(e.getMessage());
            throw new BusinessException("Не могу считать типы валют!\nВведи команду сначала!");
        }
        return list;
    }

    private LocalDate parseDate(String date) {
        logger.debug("Считываю дату...");
        try {
            return date.equals("tomorrow") ? LocalDate.now().plusDays(1) : LocalDate.parse(date, FORMATTER);
        } catch (Exception e) {
            logger.error("Не удалось считать дату!");
            logger.error(e.getMessage());
            throw new BusinessException("Не могу считать дату!\nВведи команду сначала!");
        }
    }

    private Period parsePeriod(String period) {
        logger.debug("Считываю период...");
        try {
            return Period.valueOf(period.toUpperCase());
        } catch (Exception e) {
            logger.error("Не удалось считать период!");
            logger.error(e.getMessage());
            throw new BusinessException("Не могу считать период!\nВведи команду сначала!");
        }
    }

    private AlgorithmType parseAlgorithm(String alg) {
        logger.debug("Считываю алгоритм...");
        try {
            return AlgorithmType.valueOf(alg.toUpperCase());
        } catch (Exception e) {
            logger.error("Не удалось считать алгоритм!");
            logger.error(e.getMessage());
            throw new BusinessException("Не могу считать алгоритм!\nВведи команду сначала!");
        }
    }

    private OutputType parseOutputTypes(String out) {
        logger.debug("Считываю тип вывода...");
        try {
            return OutputType.valueOf(out.toUpperCase());
        } catch (Exception e) {
            logger.error("Не удалось считать тип вывода!");
            logger.error(e.getMessage());
            throw new BusinessException("Не могу считать тип вывода!\nВведи команду сначала!");
        }
    }
}
