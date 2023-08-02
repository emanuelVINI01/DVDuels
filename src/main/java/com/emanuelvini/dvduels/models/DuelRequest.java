package com.emanuelvini.dvduels.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class DuelRequest {

    private UUID sender;

    private UUID target;

    private Kit kit;

}
