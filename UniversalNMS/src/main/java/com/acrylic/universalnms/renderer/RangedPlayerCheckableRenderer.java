package com.acrylic.universalnms.renderer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

public class RangedPlayerCheckableRenderer implements PlayerCheckableRenderer {

    private double range = 32;
    private volatile Location location;
    private volatile Collection<UUID> rendered;
    private Consumer<Player> initialize, deinitialize;

    public RangedPlayerCheckableRenderer() {
        this(new HashSet<>());
    }

    public RangedPlayerCheckableRenderer(@NotNull Collection<UUID> rendered) {
        this.rendered = rendered;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    @NotNull
    public Collection<UUID> getRendered() {
        return rendered;
    }

    public void setRendered(@NotNull Collection<UUID> rendered) {
        this.rendered = rendered;
    }

    public void setLocation(@Nullable Location location) {
        this.location = location;
    }

    @Nullable
    public Location getLocation() {
        return location;
    }

    @Override
    public synchronized void doChecks() {
        if (location != null) {
            Iterator<UUID> iterator = rendered.iterator();
            iterator.forEachRemaining(uuid -> {
                Player player = Bukkit.getPlayer(uuid);
                if (!isPlayerValid(player)) {
                    if (deinitialize != null && player != null)
                        deinitialize.accept(player);
                    iterator.remove();
                    Bukkit.broadcastMessage("Removed");
                }
            });
            for (Player player : Bukkit.getOnlinePlayers()) {
                UUID uuid = player.getUniqueId();
                if (isPlayerValid(player) && !rendered.contains(uuid)) {
                    if (initialize != null)
                        initialize.accept(player);
                    rendered.add(uuid);
                    Bukkit.broadcastMessage("Added");
                }
            }
        }
    }

    @Override
    public void setOnInitialize(@NotNull Consumer<Player> action) {
        this.initialize = action;
    }

    @Override
    public void setOnDeinitialize(@NotNull Consumer<Player> action) {
        this.deinitialize = action;
    }

    @Override
    public void initializeAll() {
        runForAllRendered(initialize);
    }

    @Override
    public void deinitializeAll() {
        runForAllRendered(deinitialize);
    }

    @Override
    public void runForAllRendered(@NotNull Consumer<Player> action) {
        for (UUID uuid : rendered) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null)
                action.accept(player);
        }
    }

    private boolean isPlayerValid(@Nullable Player player) {
        return player != null && player.isOnline() && player.getLocation().distanceSquared(location) <= (range * range);
    }

    @Override
    public RangedPlayerCheckableRenderer clone() {
        return new RangedPlayerCheckableRenderer(rendered);
    }

}
