package com.emanuelvini.dvduels.commands;

import com.emanuelvini.dvduels.configuration.MessageValue;
import com.emanuelvini.dvduels.repository.PlayerDataRepository;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {

    private PlayerDataRepository dataRepository;
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return true;
        var uuid = ((Player) commandSender).getUniqueId();
        if (strings.length != 0) {
            try {
                uuid = Bukkit.getOfflinePlayer(strings[0]).getUniqueId();
            } catch (Exception ignore) {}
        }
        val playerData = dataRepository.getData(uuid);
        MessageValue.get(MessageValue::stats).stream().map(
                l -> l
                        .replace("{kills}", String.valueOf(playerData.getKills()))
                        .replace("{deaths", String.valueOf(playerData.getDeaths()))
                        .replace("{wins}", String.valueOf(playerData.getWins()))
                        .replace("{losses}", String.valueOf(playerData.getLosses()))
                        .replace("{win_streak}", String.valueOf(playerData.getWinStreak()))
        ).forEach(commandSender::sendMessage);
        return false;
    }
}
