package com.emanuelvini.dvduels.repository.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder(builderMethodName = "generate")
public class PlayerData {

    private UUID uuid;

    private int wins;

    private int losses;

    private int kills;

    private int deaths;

    private int winStreak;

}
