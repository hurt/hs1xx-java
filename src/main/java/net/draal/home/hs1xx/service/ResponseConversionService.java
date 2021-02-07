package net.draal.home.hs1xx.service;

import net.draal.home.hs1xx.apimodel.model.CommandContainer;

/**
 * Converter for {@link CommandContainer}s
 */
public interface ResponseConversionService {
    /**
     * Determines the appropriate converter for the given target type and uses it to convert
     * the given source command container to the given target type.
     *
     * This method is thread safe.
     *
     * @param command source command container
     * @param targetType Type of target
     * @param <T> type parameter for target type
     * @return Instance of given target type with values from given command container
     * @throws net.draal.home.hs1xx.service.exception.Hs1xxException if the conversion failed for any reason
     */
    <T> T convert(CommandContainer command, Class<T> targetType);

    /**
     * Registers a converter for a given target type.
     *
     * If there is already a registered converter for the given type, the current converter is replaced
     * with the given one.
     *
     * This method may not be thread safe.
     *
     * @param targetType target type for the converter to be registered
     * @param converter Instance of the converter for the given target type
     * @param <T> type parameter for target type
     */
    <T> void registerConverter(Class<T> targetType, CommandResponseConverter<T> converter);

    /**
     * Removes converter for given target type.
     *
     * This method may not be thread safe.
     *
     * @param targetType Type for which the converter shall be removed
     * @param <T> type parameter for target type
     */
    <T> void unregisterConverter(Class<T> targetType);

    /**
     * Checks if the given type can be converted
     *
     * This method is thread safe.
     *
     * @param targetType the type that shall be converted
     * @param <T> type parameter for target type
     * @return true if the type can be converted, otherwise false
     */
    <T> boolean canConvert(Class<T> targetType);
}
