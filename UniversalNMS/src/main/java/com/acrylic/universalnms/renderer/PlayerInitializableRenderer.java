package com.acrylic.universalnms.renderer;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface PlayerInitializableRenderer extends Renderer<Player> {

    void doChecks();

    void setOnInitialize(@NotNull Consumer<Player> action);

    void setOnDeinitialize(@NotNull Consumer<Player> action);

    void initializeAll();

    void deinitializeAll();

    void initialize(@NotNull Player player);

    void deinitialize(@NotNull Player player);

    @Override
    PlayerInitializableRenderer clone();

    /**
     * @return If someone is being rendered for.
     */
    boolean isInUse();

}
