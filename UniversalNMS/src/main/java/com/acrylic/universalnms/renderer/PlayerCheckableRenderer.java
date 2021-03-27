package com.acrylic.universalnms.renderer;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface PlayerCheckableRenderer extends Renderer<Player> {

    void doChecks();

    void setOnInitialize(@NotNull Consumer<Player> action);

    void setOnDeinitialize(@NotNull Consumer<Player> action);

    void initializeAll();

    void deinitializeAll();

    @Override
    PlayerCheckableRenderer clone();

    /**
     * @return  If someone is being rendered for.
     */
    boolean isInUse();

}
