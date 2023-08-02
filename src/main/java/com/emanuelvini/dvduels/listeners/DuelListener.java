package com.emanuelvini.dvduels.listeners;

import com.emanuelvini.dvduels.managers.DuelManager;
import com.emanuelvini.dvduels.repository.PlayerDataRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

@AllArgsConstructor
public class DuelListener implements Listener {

    private DuelManager duelManager;

    private PlayerDataRepository dataRepository;
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        val player = event.getEntity();
        val duel = duelManager.getRunningDuel(player.getUniqueId());
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
            duelManager.removeRunningDuel(duel);
        }
    }

}
