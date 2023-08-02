package com.emanuelvini.dvduels.managers;

import com.emanuelvini.dvduels.DvDuels;
import com.emanuelvini.dvduels.models.DuelRequest;
import com.emanuelvini.dvduels.models.Kit;
import com.emanuelvini.dvduels.models.RunningDuel;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.AllArgsConstructor;
import lombok.val;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class DuelManager {

    // Sender, Request
    private final HashMap<UUID, DuelRequest> duelRequests = new HashMap<>();

    private final ArrayList<RunningDuel> runningDuels = new ArrayList<>();

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

    public void addRunningDuel(UUID playerOne, UUID playerTwo, Kit kit) {
        runningDuels.add(
          RunningDuel
                  .builder()
                  .playerOne(playerOne)
                  .playerTwo(playerTwo)
                  .kit(kit)
                  .build()
        );
    }

    public RunningDuel getRunningDuel(UUID uuid) {
        return runningDuels
                .stream()
                .filter(rd -> rd.getPlayerOne().equals(uuid) || rd.getPlayerTwo().equals(uuid))
                .findFirst()
                .orElse(null);
    }

    public void removeRunningDuel(RunningDuel runningDuel) {
        runningDuels.remove(runningDuel);
    }

    public DuelRequest getRequest(UUID sender) {
        return duelRequests.get(sender);
    }
}
