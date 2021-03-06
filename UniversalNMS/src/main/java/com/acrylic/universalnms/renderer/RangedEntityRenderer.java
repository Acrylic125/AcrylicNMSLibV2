package com.acrylic.universalnms.renderer;

import com.acrylic.universalnms.entity.NMSEntityInstance;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

public class RangedEntityRenderer extends SimpleEntityRenderer {

    private final Entity watchFrom;
    private Location location;
    private float range = 32;

    public RangedEntityRenderer(@NotNull Location location) {
        this.location = location;
        this.watchFrom = null;
    }

    public RangedEntityRenderer(@NotNull NMSEntityInstance watchFrom) {
        this.location = null;
        this.watchFrom = watchFrom.getBukkitEntity();
    }

    public RangedEntityRenderer(@NotNull Entity watchFrom) {
        this.location = null;
        this.watchFrom = watchFrom;
    }

    public RangedEntityRenderer(@NotNull Collection<UUID> cached, @NotNull Location location) {
        super(cached);
        this.location = location;
        this.watchFrom = null;
    }

    public RangedEntityRenderer(@NotNull Collection<UUID> cached, @NotNull NMSEntityInstance watchFrom) {
        super(cached);
        this.location = null;
        this.watchFrom = watchFrom.getBukkitEntity();
    }

    public RangedEntityRenderer(@NotNull Collection<UUID> cached, @NotNull Entity watchFrom) {
        super(cached);
        this.location = null;
        this.watchFrom = watchFrom;
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
            location = watchFrom.getLocation();
        if (cached != null) {
            Iterator<UUID> iterator = cached.iterator();
            iterator.forEachRemaining(uuid -> {
                Player player = Bukkit.getPlayer(uuid);
                if (!isPlayerValid(player)) {
                    if (player != null)
                        runTermination(player);
                    iterator.remove();
                }
            });
            for (Player player : Bukkit.getOnlinePlayers()) {
                UUID uuid = player.getUniqueId();
                if (isPlayerValid(player) && !cached.contains(uuid)) {
                    runInitialization(player);
                    cached.add(uuid);
                }
            }
        }
    }

    private boolean isPlayerValid(@Nullable Player player) {
        return player != null && location.getWorld().equals(player.getWorld()) && player.isOnline() && player.getLocation().distanceSquared(location) <= (range * range);
    }

    @Override
    public RangedEntityRenderer clone() {
        RangedEntityRenderer rangedEntityRenderer;
        if (watchFrom != null)
            rangedEntityRenderer = new RangedEntityRenderer(cached, watchFrom);
        else
            rangedEntityRenderer = new RangedEntityRenderer(cached, location);
        rangedEntityRenderer.range = range;
        return rangedEntityRenderer;
    }

}
