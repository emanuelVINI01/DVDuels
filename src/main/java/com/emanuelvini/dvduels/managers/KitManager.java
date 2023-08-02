package com.emanuelvini.dvduels.managers;

import com.emanuelvini.dvduels.DvDuels;
import com.emanuelvini.dvduels.models.Kit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;

@AllArgsConstructor
public class KitManager {

    private DvDuels plugin;

    @Getter
    private final HashMap<String, Kit> kits = new HashMap<>();

    public void addKit(Kit kit) {
        kits.put(kit.getName(), kit);
    }

    public Kit getKit(String name) {
        return kits.get(name);
    }
}
