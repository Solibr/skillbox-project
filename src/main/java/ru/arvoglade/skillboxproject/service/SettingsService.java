package ru.arvoglade.skillboxproject.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.arvoglade.skillboxproject.dto.GlobalSettingsDtoResponse;
import ru.arvoglade.skillboxproject.model.GlobalSetting;
import ru.arvoglade.skillboxproject.model.GlobalSettingType;
import ru.arvoglade.skillboxproject.repository.SettingsRepository;

import javax.annotation.PostConstruct;

@Data
@Service
public class SettingsService {

    private final SettingsRepository settingsRepository;

    @Value("${blog.startGlobalSettings.multiuserMode}")
    private String multiuserModeValue = "NO";

    @Value("${blog.startGlobalSettings.postPremoderation}")
    private String postPremoderationValue = "NO";

    @Value("${blog.startGlobalSettings.statisticsIsPublic}")
    private String statisticsIsPublicValue = "NO";



    public SettingsService(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    @PostConstruct
    public void initSettings() {
        GlobalSetting multiuserMode = new GlobalSetting(
                GlobalSettingType.MULTIUSER_MODE,
                "Многопользовательский режим",
                multiuserModeValue);
        GlobalSetting postPremoderation = new GlobalSetting(
                GlobalSettingType.POST_PREMODERATION,
                "Премодерация постов",
                postPremoderationValue);
        GlobalSetting statisticsIsPublic = new GlobalSetting(
                GlobalSettingType.STATISTICS_IS_PUBLIC,
                "Показывать вмес статистику блога",
                statisticsIsPublicValue);

        settingsRepository.findByCode(GlobalSettingType.MULTIUSER_MODE).ifPresentOrElse((x) -> {}, () ->
            settingsRepository.save(multiuserMode)
        );
        settingsRepository.findByCode(GlobalSettingType.POST_PREMODERATION).ifPresentOrElse((x) -> {}, () ->
            settingsRepository.save(postPremoderation)
        );
        settingsRepository.findByCode(GlobalSettingType.STATISTICS_IS_PUBLIC).ifPresentOrElse((x) -> {}, () ->
            settingsRepository.save(statisticsIsPublic)
        );
    }

    public GlobalSettingsDtoResponse getSettingsDto() {
        GlobalSettingsDtoResponse globalSettingsDto = new GlobalSettingsDtoResponse();

        String multiuserModeValue = settingsRepository.findByCode(GlobalSettingType.MULTIUSER_MODE)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)).getValue();
        String postPremoderationValue = settingsRepository.findByCode(GlobalSettingType.POST_PREMODERATION)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)).getValue();
        String statisticsIsPublicValue = settingsRepository.findByCode(GlobalSettingType.STATISTICS_IS_PUBLIC)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)).getValue();

        globalSettingsDto.setMultiuserMode(multiuserModeValue.equals("YES"));
        globalSettingsDto.setPostPremoderation(postPremoderationValue.equals("YES"));
        globalSettingsDto.setStatisticsIsPublic(statisticsIsPublicValue.equals("YES"));

        return globalSettingsDto;
    }
}
