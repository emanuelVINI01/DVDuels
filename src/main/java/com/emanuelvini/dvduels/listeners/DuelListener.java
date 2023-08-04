package com.emanuelvini.dvduels.listeners;

import com.emanuelvini.dvduels.managers.DuelManager;
import com.emanuelvini.dvduels.repository.PlayerDataRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

@AllArgsConstructor
public class DuelListener implements Listener {

    private DuelManager duelManager;

    private PlayerDataRepository dataRepository;
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        val player = event.getEntity();
        val duel = duelManager.removeAcceptedDuel(player.getUniqueId());
        if (duel != null) {
            val killer = event.getEntity().getKiller();
            if (killer == null) return;
            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
                player.showPlayer(onlinePlayer);
                killer.showPlayer(onlinePlayer);
            });

            val winnerData = dataRepository.getData(killer.getUniqueId());
            val loserData = dataRepository.getData(player.getUniqueId());

            winnerData.setWins(winnerData.getWins() + 1);
            winnerData.setKills(winnerData.getKills() + 1);
            winnerData.setWinStreak(winnerData.getWinStreak() + 1);

            loserData.setDeaths(loserData.getKills() + 1);
            loserData.setWinStreak(0);

            player.teleport(duel.getPlayerOneLocation());
            killer.teleport(duel.getPlayerTwoLocation());

            Bukkit.getScheduler().cancelTask(duel.getTask());

            duelManager.removeAcceptedDuel(duel);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent moveEvent) {

        val duel = duelManager.removeAcceptedDuel(moveEvent.getPlayer().getUniqueId());
        if (duel != null && !duel.isStarted()) {
            moveEvent.setCancelled(true);
        }
    }

}
