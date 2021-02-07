package net.draal.home.hs1xx.service;

import net.draal.home.hs1xx.apimodel.model.CommandContainer;

/**
 * This interface defines a converter from CommandContainer to a given target type.
 *
 * @param <T> target type for conversion
 */
public interface CommandResponseConverter<T> {
    /**
     * Converts the given {@link CommandContainer} to the target type
     *
     * @param commandContainer given command container as returned from the device
     * @return a new instance of the target type populated with data from the given CommandContainer
     * @throws IllegalArgumentException if the given command container does not contain attributes required for
     *                                  conversion
     */
    T convert(CommandContainer commandContainer);
}
