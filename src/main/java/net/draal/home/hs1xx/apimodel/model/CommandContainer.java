package net.draal.home.hs1xx.apimodel.model;

import lombok.Data;

@Data
public class CommandContainer {
    private SystemContainer system;
    private CloudContainer cnCloud;
    private EmeterContainer emeter;
    private TimeContainer time;
}
