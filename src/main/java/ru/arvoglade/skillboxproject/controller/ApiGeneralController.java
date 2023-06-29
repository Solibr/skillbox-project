package ru.arvoglade.skillboxproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.arvoglade.skillboxproject.dto.GlobalSettingsDtoResponse;
import ru.arvoglade.skillboxproject.dto.InitDtoResponse;
import ru.arvoglade.skillboxproject.service.SettingsService;

@RestController
@RequestMapping("/api")
public class ApiGeneralController {

    private final InitDtoResponse initDtoResponse;
    private final SettingsService settingsService;

    public ApiGeneralController(InitDtoResponse initDtoResponse, SettingsService settingsService) {
        this.initDtoResponse = initDtoResponse;
        this.settingsService = settingsService;
    }

    @GetMapping("/init")
    public InitDtoResponse getInit() {
        return initDtoResponse;
    }

    @GetMapping("/settings")
    public GlobalSettingsDtoResponse getSettings() {
        return settingsService.getSettingsDto();
    }
}
