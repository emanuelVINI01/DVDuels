package com.emanuelvini.dvduels.commands;

import com.emanuelvini.dvduels.configuration.ArenaValue;
import com.emanuelvini.dvduels.configuration.MessageValue;
import com.emanuelvini.dvduels.managers.DuelManager;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AcceptCommand implements CommandExecutor {

    private DuelManager duelManager;
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player sender)) return true;
        if (strings.length != 1) {
            sender.sendMessage("§cUsage: /accept <name>");
            return true;
        }
        val target = Bukkit.getPlayer(strings[0]);
        if (target == null) {
            sender.sendMessage(MessageValue.get(MessageValue::playerNotOnline));
            return true;
        }
        val request = duelManager.getRequest(target.getUniqueId());
        if (request == null) {
            sender.sendMessage(MessageValue.get(MessageValue::requestNotExists));
            return true;
        }
        val kit = request.getKit();
        val acceptedDuel = duelManager.addAcceptedDuel(sender.getUniqueId(), target.getUniqueId(), kit);
        duelManager.acceptDuel(sender.getUniqueId(), target.getUniqueId(), acceptedDuel);

        return false;
    }
}
