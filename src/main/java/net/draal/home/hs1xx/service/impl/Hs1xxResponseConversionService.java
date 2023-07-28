package net.draal.home.hs1xx.service.impl;

import com.google.common.reflect.MutableTypeToInstanceMap;
import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToInstanceMap;
import com.google.common.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import net.draal.home.hs1xx.apimodel.model.CommandContainer;
import net.draal.home.hs1xx.service.CommandResponseConverter;
import net.draal.home.hs1xx.service.ResponseConversionService;
import net.draal.home.hs1xx.service.converters.*;
import net.draal.home.hs1xx.service.data.*;
import net.draal.home.hs1xx.service.exception.Hs1xxException;

@SuppressWarnings("UnstableApiUsage")
@Slf4j
public class Hs1xxResponseConversionService implements ResponseConversionService {
    private final TypeToInstanceMap<CommandResponseConverter<?>> CONVERTER_MAPPING = new MutableTypeToInstanceMap<>();

    public static Hs1xxResponseConversionService withDefaultConverters() {
        Hs1xxResponseConversionService instance = new Hs1xxResponseConversionService();
        instance.registerDefaultConverters();
        return instance;
    }

    @Override
    public <T> T convert(CommandContainer command, Class<T> targetType) {
        try {
            LOG.trace("Converting command {} to type {}", command, targetType.getSimpleName());
            CommandResponseConverter<T> converter = getConverter(targetType);
            if (converter == null) {
                throw new IllegalStateException("No converter registered for target type " + targetType.getSimpleName());
            }
            return converter.convert(command);
        } catch (RuntimeException e) {
            throw new Hs1xxException("Could not convert response from device.", e);
        }
    }

    @Override
    public <T> void registerConverter(Class<T> targetType, CommandResponseConverter<T> converter) {
        LOG.trace("Registering converter [{}] for target type [{}]", converter, targetType.getTypeName());
        CONVERTER_MAPPING.putInstance(getConverterToken(targetType), converter);
    }

    @Override
    public <T> void unregisterConverter(Class<T> targetType) {
        LOG.trace("Unregistering converter for target type [{}]", targetType.getTypeName());
        CONVERTER_MAPPING.remove(getConverterToken(targetType));
    }

    @Override
    public <T> boolean canConvert(Class<T> targetType) {
        return CONVERTER_MAPPING.containsKey(getConverterToken(targetType));
    }

    private void registerDefaultConverters() {
        LOG.trace("Using default converters for [{}]", this);
        registerConverter(RealtimePowerStats.class, new RealtimePowerStatsConverter());
        registerConverter(SystemInformation.class, new SystemInformationConverter());
        registerConverter(DayPowerStats.class, new DayPowerStatsConverter());
        registerConverter(MonthPowerStats.class, new MonthPowerStatsConverter());
        registerConverter(DeviceTime.class, new DeviceTimeConverter());
    }

    private <T> TypeToken<CommandResponseConverter<T>> getConverterToken(Class<T> targetClass) {
        return new TypeToken<CommandResponseConverter<T>>() {
        }.where(new TypeParameter<T>() {
        }, targetClass);
    }

    private <T> CommandResponseConverter<T> getConverter(Class<T> targetType) {
        LOG.trace("Converter lookup for target type [{}]", targetType.getTypeName());
        return CONVERTER_MAPPING.getInstance(getConverterToken(targetType));
    }
}
