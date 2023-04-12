package ru.liga.exchangerateforecast.serviceforlist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.exchangerateforecast.entity.ExchangeRateObj;
import ru.liga.exchangerateforecast.enums.RateType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PrepareResponseForListService {
    private final Logger logger = LoggerFactory.getLogger(ParseCommandForListService.class);
    private final Locale LOCALE = new Locale("ru", "RU");
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy", LOCALE);

    private StringBuilder prepareResponse(LocalDate date, Float rate) {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(date.getDayOfWeek().getDisplayName(TextStyle.SHORT, LOCALE).toUpperCase())
                .append(" ")
                .append(date.format(FORMATTER))
                .append(" - ")
                .append(String.format("%.2f", rate))
                .append("\n");
    }

    public String prepareResponseByMap(Map<RateType, List<ExchangeRateObj>> map) {
        logger.debug("Начинаю готовить ответ...");
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<RateType, List<ExchangeRateObj>> entry : map.entrySet()) {
            stringBuilder.append("Расчет курса ")
                    .append(entry.getKey().name())
                    .append("\n");
            for (ExchangeRateObj obj : entry.getValue()) {
                stringBuilder.append(prepareResponse(obj.getDate(), obj.getRate()));
            }
            stringBuilder.append("\n");
        }
        logger.debug("Отлвет готов!");
        return stringBuilder.append("\n").toString();
    }
}
