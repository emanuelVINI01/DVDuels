package com.emanuelvini.dvduels.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.UUID;

@Builder
@Setter
@Getter
public class AcceptedDuel {

    private UUID playerOne;

    private UUID playerTwo;

    private Location playerOneLocation;

    private Location playerTwoLocation;

    private int task;

    private Kit kit;

    private boolean started;

    private final WaitBar waitBar = new WaitBar(5);

}
