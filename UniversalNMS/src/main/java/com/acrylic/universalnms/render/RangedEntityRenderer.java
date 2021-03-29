package com.acrylic.universalnms.render;

import com.acrylic.universalnms.entity.NMSEntityInstance;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Objects;
import java.util.UUID;

public class RangedEntityRenderer extends SimpleEntityRenderer {

    private final WeakReference<NMSEntityInstance> watchFrom;
    private Location location;
    private float range = 32;

    public RangedEntityRenderer(@NotNull Location location) {
        this.location = location;
        this.watchFrom = null;
    }

    public RangedEntityRenderer(@NotNull NMSEntityInstance watchFrom) {
        this.location = null;
        this.watchFrom = new WeakReference<>(watchFrom);
    }

    public double getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public void setLocation(@NotNull Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public synchronized void run() {
        if (watchFrom != null)
            location = Objects.requireNonNull(watchFrom.get()).getLocation();
        if (cached != null) {
            Iterator<UUID> iterator = cached.iterator();
            iterator.forEachRemaining(uuid -> {
                Player player = Bukkit.getPlayer(uuid);
                if (!isPlayerValid(player)) {
                    if (player != null)
                        runTermination(player);
                    iterator.remove();
                    //Bukkit.broadcastMessage("Removed");
                }
            });
            for (Player player : Bukkit.getOnlinePlayers()) {
                UUID uuid = player.getUniqueId();
                if (isPlayerValid(player) && !cached.contains(uuid)) {
                    runInitialization(player);
                    cached.add(uuid);
                    //Bukkit.broadcastMessage("Added");
                }
            }
        }
    }

    private boolean isPlayerValid(@Nullable Player player) {
        return player != null && player.isOnline() && player.getLocation().distanceSquared(location) <= (range * range);
    }

}
