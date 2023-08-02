package com.emanuelvini.dvduels.models;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class RunningDuel {

    private UUID playerOne;

    private UUID playerTwo;

    private Kit kit;

}
