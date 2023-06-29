package ru.arvoglade.skillboxproject.repository;

import org.springframework.data.repository.CrudRepository;
import ru.arvoglade.skillboxproject.model.GlobalSetting;
import ru.arvoglade.skillboxproject.model.GlobalSettingType;

import java.util.Optional;

public interface SettingsRepository extends CrudRepository<GlobalSetting, Integer> {

    Optional<GlobalSetting> findByCode(GlobalSettingType settingType);
}
