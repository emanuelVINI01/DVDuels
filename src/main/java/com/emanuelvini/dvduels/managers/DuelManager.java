package com.emanuelvini.dvduels.managers;

import com.emanuelvini.dvduels.DvDuels;
import com.emanuelvini.dvduels.configuration.ArenaValue;
import com.emanuelvini.dvduels.models.DuelRequest;
import com.emanuelvini.dvduels.models.Kit;
import com.emanuelvini.dvduels.models.AcceptedDuel;
import lombok.AllArgsConstructor;
import lombok.val;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@AllArgsConstructor
public class DuelManager {

    // Sender, Request
    private final HashMap<UUID, DuelRequest> duelRequests = new HashMap<>();

    private final ArrayList<AcceptedDuel> acceptedDuels = new ArrayList<>();

    private DvDuels plugin;
    public void addRequest(UUID sender, UUID target, Kit kit) {
        val request = DuelRequest
                .builder()
                .sender(sender)
                .target(target)
                .kit(kit)
                .build();
        duelRequests.put(sender,
                request
                );

        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                duelRequests.remove(request.getSender());
        }, 20L * 60);
    }

    public AcceptedDuel addAcceptedDuel(UUID playerOne, UUID playerTwo, Kit kit) {
        val acceptedDuel = AcceptedDuel
                .builder()
                .playerOne(playerOne)
                .playerTwo(playerTwo)
                .kit(kit)
                .build();
        acceptedDuels.add(
          acceptedDuel
        );
        return acceptedDuel;
    }

    public void acceptDuel(AcceptedDuel acceptedDuel) {

        val sender = Bukkit.getPlayer(acceptedDuel.getPlayerOne());
        val target = Bukkit.getPlayer(acceptedDuel.getPlayerTwo());
        val kit = acceptedDuel.getKit();

        if (sender == null || target == null) return;
        Bukkit.getOnlinePlayers().forEach(player -> {
            sender.hidePlayer(plugin, player);
            target.hidePlayer(plugin, player);
        });
        sender.showPlayer(plugin, target);
        target.showPlayer(plugin, sender);
        sender.teleport(ArenaValue.instance().pos1());
        target.teleport(ArenaValue.instance().pos2());

        sender.getInventory().setArmorContents(kit.getArmorContents().toArray(new ItemStack[0]));
        sender.getInventory().setContents(kit.getInventoryContents().toArray(new ItemStack[0]));

        target.getInventory().setArmorContents(kit.getArmorContents().toArray(new ItemStack[0]));
        target.getInventory().setContents(kit.getInventoryContents().toArray(new ItemStack[0]));

        val task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            val waitBar = acceptedDuel.getWaitBar();
            if (!acceptedDuel.isStarted()) {
                waitBar.setRemain(waitBar.getRemain() - 1);
                if (waitBar.getRemain() == 0) {
                    acceptedDuel.setStarted(true);
                }
                sender.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.format("§eThe game will start in %d", waitBar.getRemain())));
            } else {
                waitBar.setRemain(waitBar.getRemain() + 1);
                sender.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.format("§eThe game is running %d", waitBar.getRemain())));
            }
        }, 20L, 20L);
        acceptedDuel.setTask(task);
    }

    public AcceptedDuel removeAcceptedDuel(UUID uuid) {
        return acceptedDuels
                .stream()
                .filter(rd -> rd.getPlayerOne().equals(uuid) || rd.getPlayerTwo().equals(uuid))
                .findFirst()
                .orElse(null);
    }

    public void removeAcceptedDuel(AcceptedDuel acceptedDuel) {
        acceptedDuels.remove(acceptedDuel);
    }

    public DuelRequest getRequest(UUID sender) {
        return duelRequests.get(sender);
    }
}
