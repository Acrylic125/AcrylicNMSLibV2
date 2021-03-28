package com.acrylic.universalnms.renderer;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

@Deprecated
public class AdaptablePlayerCheckableRenderer<R extends PlayerCheckableRenderer>
        extends AdaptableRenderer<R, Player>
        implements PlayerCheckableRenderer {

    private Consumer<Player> initialize, deinitialize;

    public AdaptablePlayerCheckableRenderer(@NotNull R adaptFrom) {
        super(adaptFrom);
    }

    @Override
    public void doChecks() { }

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
        if (initialize != null)
            runForAllRendered(initialize);
    }

    @Override
    public void deinitializeAll() {
        if (deinitialize != null)
            runForAllRendered(deinitialize);
    }

    @Override
    public void initialize(@NotNull Player player) {

    }

    @Override
    public void deinitialize(@NotNull Player player) {

    }

    @Override
    public boolean isInUse() {
        return getAdaptFrom().isInUse();
    }

    @Override
    public AdaptablePlayerCheckableRenderer<R> clone() {
        return new AdaptablePlayerCheckableRenderer<>(getAdaptFrom());
    }
}
