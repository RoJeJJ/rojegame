package com.roje.game.core.entity.config;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter@Setter
public class CardRoomConfig implements Serializable {
    private static final long serialVersionUID = 4109170624240834693L;
    private String creator;
    private int gameId;
    private int maxPerson;
    private int round;
}
