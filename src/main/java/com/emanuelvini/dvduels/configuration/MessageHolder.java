package com.emanuelvini.dvduels.configuration;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.configuration.ConfigurationSection;

import java.util.function.Function;

@Getter
@Accessors(fluent = true)

public class MessageHolder {

    private static MessageHolder instance = new MessageHolder();

    public void load(ConfigurationSection section) {

    }

    public static <T> T get(Function<MessageHolder, T> function) {
        return function.apply(instance);
    }

}
