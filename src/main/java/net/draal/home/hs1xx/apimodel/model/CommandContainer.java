package net.draal.home.hs1xx.apimodel.model;

import lombok.Builder;

@Builder
public record CommandContainer(
        SystemContainer system,
        EmeterContainer emeter,
        TimeContainer time
) {
    public static CommandContainer of(SystemContainer system) {
        return CommandContainer.builder().system(system).build();
    }

    public static CommandContainer of(EmeterContainer emeter) {
        return CommandContainer.builder().emeter(emeter).build();
    }

    public static CommandContainer of(TimeContainer time) {
        return CommandContainer.builder().time(time).build();
    }
}
