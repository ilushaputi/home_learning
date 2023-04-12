package ru.liga.exchangerateforecast.serviceforlist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.exchangerateforecast.enums.Command;
import ru.liga.exchangerateforecast.service.ArgumentService;

import java.util.Map;

public class ArgumentAndResponseForListService {
    private final ArgumentService argumentService;
    private final PrepareResponseForListService prepareResponseForListService;

    public ArgumentAndResponseForListService(PrepareResponseForListService prepareResponseForListService,
                                             ArgumentService argumentService) {
        this.prepareResponseForListService = prepareResponseForListService;
        this.argumentService = argumentService;
    }

    public String prepareArgumentsAndPrepareResponse(Map<Command, String> map) {
        return prepareResponseForListService.prepareResponseByMap(argumentService.prepareAllArguments(map));
    }
}
