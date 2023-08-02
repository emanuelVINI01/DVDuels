package com.emanuelvini.dvduels.commands;

import com.emanuelvini.dvduels.configuration.ConfigurationValue;
import com.emanuelvini.dvduels.configuration.MessageValue;
import com.emanuelvini.dvduels.managers.DuelManager;
import com.emanuelvini.dvduels.managers.KitManager;
import com.emanuelvini.dvduels.models.DuelRequest;
import lombok.AllArgsConstructor;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class InviteCommand implements CommandExecutor {

    private KitManager kitManager;

    private DuelManager duelManager;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if ((!(commandSender instanceof Player sender))) return true;
        if (strings.length < 1) {
            commandSender.sendMessage("§cUsage: /invite <player> [kit]");
            return true;
        }
        var kit = kitManager.getKit(ConfigurationValue.get(ConfigurationValue::defaultKit));
        if (strings.length > 1) {
            kit = kitManager.getKit(strings[1]);
        }
        if (kit == null) {
            commandSender.sendMessage(MessageValue.get(MessageValue::kitNotExists));
            return true;
        }
        val target = Bukkit.getPlayer(strings[0]);
        if (target == null) {
            commandSender.sendMessage(MessageValue.get(MessageValue::playerNotOnline));
            return true;
        }
        if (duelManager.getRequest(sender.getUniqueId()) != null) {
            commandSender.sendMessage(MessageValue.get(MessageValue::alreadyHavePendingRequest));
            return true;
        }
        duelManager.addRequest(sender.getUniqueId(), target.getUniqueId(), kit);
        return false;

    }
}
