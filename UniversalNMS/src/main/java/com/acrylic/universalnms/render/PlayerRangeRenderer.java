package com.acrylic.universalnms.render;

import com.acrylic.universalnms.entity.NMSEntityInstance;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.*;
import java.util.function.Consumer;

public class PlayerRangeRenderer implements InitializableRenderer<Player> {

    private final WeakReference<Entity> watchFrom;
    private Location location;
    private float range = 32;
    private final Collection<UUID> cached;
    private RendererAction<Player> initialize, termination;

    public PlayerRangeRenderer(@NotNull Location location) {
        this(new HashSet<>(), location);
    }

    public PlayerRangeRenderer(@NotNull NMSEntityInstance watchFrom) {
        this(new HashSet<>(), watchFrom);
    }

    public PlayerRangeRenderer(@NotNull Entity watchFrom) {
        this(new HashSet<>(), watchFrom);
    }

    public PlayerRangeRenderer(@NotNull Collection<UUID> cached, @NotNull Location location) {
        this.cached = cached;
        this.location = location;
        this.watchFrom = null;
    }

    public PlayerRangeRenderer(@NotNull Collection<UUID> cached, @NotNull NMSEntityInstance watchFrom) {
        this.cached = cached;
        this.location = null;
        this.watchFrom = new WeakReference<>(watchFrom.getBukkitEntity());
    }

    public PlayerRangeRenderer(@NotNull Collection<UUID> cached, @NotNull Entity watchFrom) {
        this.cached = cached;
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

    @Override
    public PlayerRangeRenderer clone() {
        PlayerRangeRenderer rangeRenderer;
        if (watchFrom != null)
            rangeRenderer = new PlayerRangeRenderer(cached, Objects.requireNonNull(watchFrom.get()));
        else
            rangeRenderer = new PlayerRangeRenderer(cached, location);
        rangeRenderer.range = range;
        return rangeRenderer;
    }

    @Override
    public void runForAllRendered(@NotNull Consumer<Player> action) {
        for (UUID uuid : cached) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null)
                action.accept(player);
        }
    }

    public void runInitialization(@NotNull Player player) {
        if (initialize != null)
            initialize.run(player);
    }

    public void runTermination(@NotNull Player player) {
        if (termination != null)
            termination.run(player);
    }

    @Override
    public synchronized void reinitialize() {
        runForAllRendered(this::runInitialization);
        cached.clear();
    }

    @Override
    public synchronized void terminate() {
        runForAllRendered(this::runTermination);
        cached.clear();
    }

    @Override
    public void initializeFor(@NotNull Player player) {
        runInitialization(player);
        cached.add(player.getUniqueId());
    }

    @Override
    public void terminateFor(@NotNull Player player) {
        runTermination(player);
        cached.remove(player.getUniqueId());
    }

    @Nullable
    public RendererAction<Player> getInitialize() {
        return initialize;
    }

    public void setInitialize(@Nullable RendererAction<Player> initialize) {
        this.initialize = initialize;
    }

    @Nullable
    public RendererAction<Player> getTermination() {
        return termination;
    }

    public void setTermination(@Nullable RendererAction<Player> termination) {
        this.termination = termination;
    }
}
