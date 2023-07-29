package com.emanuelvini.dvduels.configuration;

import com.emanuelvini.dvduels.DvDuels;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.function.Function;

import static com.emanuelvini.dvduels.util.ColorUtil.parseColors;

@Getter
@Accessors(fluent = true)

public class MessageValue {

    @Getter
    private static MessageValue instance = new MessageValue();

    private String notHavePermission;



    public void load(DvDuels plugin) {

        //Save its default if not exists and load to YAML.
        val messagesFile = new File(plugin.getDataFolder(), "messages.yml");

        if (messagesFile.exists()) plugin.saveResource("messages.yml", false);

        val messagesSection = YamlConfiguration.loadConfiguration(messagesFile);

        //Load to variables (I suggested to use (https://github.com/henrysaantos/configuration-injector/) because this.

        notHavePermission = parseColors(messagesSection.getString("not_have_permission"));

    }

    public static <T> T get(Function<MessageValue, T> function) {
        return function.apply(instance);
    }

}
