package com.acrylic.universalnms.render;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;
import java.util.function.Consumer;

public class SimpleEntityRenderer extends AbstractEntityRenderer {

    protected final Collection<UUID> cached;

    public SimpleEntityRenderer() {
        this(new HashSet<>());
    }

    public SimpleEntityRenderer(@NotNull Collection<UUID> cached) {
        this.cached = cached;
    }

    @Override
    public synchronized void run() { }

    @Override
    public boolean isBeingUsedBySomeone() {
        return !cached.isEmpty();
    }

    @Override
    public AbstractEntityRenderer clone() {
        return new SimpleEntityRenderer(cached);
    }

    @Override
    public void runForAllRendered(@NotNull Consumer<Player> action) {
        for (UUID uuid : cached) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null)
                action.accept(player);
        }
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
        super.initializeFor(player);
        cached.add(player.getUniqueId());
    }

    @Override
    public void terminateFor(@NotNull Player player) {
        super.terminateFor(player);
        cached.remove(player.getUniqueId());
    }

    public Collection<UUID> getCached() {
        return cached;
    }
}
