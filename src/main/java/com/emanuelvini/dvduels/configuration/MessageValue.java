package com.emanuelvini.dvduels.configuration;

import com.emanuelvini.dvduels.DvDuels;
import com.emanuelvini.dvduels.util.ConfigurationUtil;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;
import java.util.function.Function;

import static com.emanuelvini.dvduels.util.ColorUtil.parseColors;

@Getter
@Accessors(fluent = true)

public class MessageValue {

    @Getter
    private static MessageValue instance = new MessageValue();

    private String notHavePermission;

    private String kitNotExists;

    private String playerNotOnline;

    private String alreadyHavePendingRequest;

    private String requestNotExists;

    private List<String> stats;



    public void load(DvDuels plugin) {


        val messagesSection = ConfigurationUtil.loadConfiguration(plugin, "messages.yml");

        notHavePermission = parseColors(messagesSection.getString("not_have_permission"));
        stats = parseColors(messagesSection.getStringList("stats"));
        kitNotExists = parseColors(messagesSection.getString("kit_not_exists"));
        playerNotOnline = parseColors(messagesSection.getString("player_is_not_online"));
        alreadyHavePendingRequest = parseColors(messagesSection.getString("already_have_pending_request"));
        requestNotExists = parseColors(messagesSection.getString("request_not_exists"));
    }

    public static <T> T get(Function<MessageValue, T> function) {
        return function.apply(instance);
    }

}
