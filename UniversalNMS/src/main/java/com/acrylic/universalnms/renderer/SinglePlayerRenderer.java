package com.acrylic.universalnms.renderer;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class SinglePlayerRenderer implements Renderer<Player> {

    private final Player player;

    public SinglePlayerRenderer(@NotNull Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void runForAllRendered(@NotNull Consumer<Player> action) {
        action.accept(player);
    }
}
