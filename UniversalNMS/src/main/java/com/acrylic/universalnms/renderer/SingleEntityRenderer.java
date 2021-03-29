package com.acrylic.universalnms.renderer;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class SingleEntityRenderer extends AbstractEntityRenderer {

    private final Player renderFor;

    public SingleEntityRenderer(@NotNull Player renderFor) {
        this.renderFor = renderFor;
    }

    public Player getRenderedFor() {
        return renderFor;
    }

    @Override
    public synchronized void run() { }

    @Override
    public boolean isBeingUsedBySomeone() {
        return true;
    }

    @Override
    public void reinitialize() {
        runInitialization(renderFor);
    }

    @Override
    public void terminate() {
        runTermination(renderFor);
    }

    @Override
    public void runForAllRendered(@NotNull Consumer<Player> action) {
        action.accept(renderFor);
    }

    @Override
    public SingleEntityRenderer clone() {
        return new SingleEntityRenderer(renderFor);
    }
}
