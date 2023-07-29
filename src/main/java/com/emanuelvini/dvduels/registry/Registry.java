package com.emanuelvini.dvduels.registry;

import com.emanuelvini.dvduels.DvDuels;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class Registry {

    protected DvDuels plugin;

    public abstract void register();

    public abstract void unregister();


}
