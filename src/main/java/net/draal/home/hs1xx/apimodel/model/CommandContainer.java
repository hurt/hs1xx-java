package net.draal.home.hs1xx.apimodel.model;

import lombok.Builder;
import lombok.Data;

@Builder
public record CommandContainer(
        SystemContainer system,
        CloudContainer cnCloud,
        EmeterContainer emeter,
        TimeContainer time
) {
    public static CommandContainer of(SystemContainer system) {
        return CommandContainer.builder().system(system).build();
    }

    public static CommandContainer of(CloudContainer cloud) {
        return CommandContainer.builder().cnCloud(cloud).build();
    }

    public static CommandContainer of(EmeterContainer emeter) {
        return CommandContainer.builder().emeter(emeter).build();
    }

    public static CommandContainer of(TimeContainer time) {
        return CommandContainer.builder().time(time).build();
    }
}
