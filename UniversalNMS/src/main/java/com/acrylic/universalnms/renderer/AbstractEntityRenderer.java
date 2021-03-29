package com.acrylic.universalnms.renderer;

import com.acrylic.universalnms.entity.NMSEntityInstance;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import static com.acrylic.universalnms.renderer.EntityRendererWorker.RENDERER_ID_COUNTER;

public abstract class AbstractEntityRenderer
        implements InitializableRenderer<Player> {

    private final int id = RENDERER_ID_COUNTER.addAndGet(1);
    private final Map<Integer, ActionHolder> entityActionHolders;

    public AbstractEntityRenderer() {
        this(new HashMap<>());
    }

    public AbstractEntityRenderer(@NotNull Map<Integer, ActionHolder> entityActionHolders) {
        this.entityActionHolders = entityActionHolders;
    }

    public void bindEntity(@NotNull NMSEntityInstance entity) {
        entityActionHolders.put(entity.getID(), new ActionHolder());
    }

    public void bindEntity(@NotNull NMSEntityInstance entity, @NotNull ActionHolder actionHolder) {
        entityActionHolders.put(entity.getID(), actionHolder);
    }

    public void unbindEntity(@NotNull NMSEntityInstance entity) {
        entityActionHolders.remove(entity.getID());
    }

    public boolean isUsedByAnEntity() {
        return !entityActionHolders.isEmpty();
    }

    @Override
    public void initializeFor(@NotNull Player player) {
        runInitialization(player);
    }

    public void runInitialization(@NotNull Player player) {
        for (ActionHolder actionHolder : entityActionHolders.values())
            actionHolder.runInitialization(player);
    }

    @Override
    public void terminateFor(@NotNull Player player) {
        runTermination(player);
    }

    public void runTermination(@NotNull Player player) {
        for (ActionHolder actionHolder : entityActionHolders.values())
            actionHolder.runTermination(player);
    }

    public abstract void run();

    public abstract boolean isBeingUsedBySomeone();

    public static class ActionHolder {

        private RendererAction<Player> initialize, termination;

        public ActionHolder() { }

        public ActionHolder(RendererAction<Player> initialize, RendererAction<Player> termination) {
            this.initialize = initialize;
            this.termination = termination;
        }

        public RendererAction<Player> getInitialize() {
            return initialize;
        }

        public void setInitialize(RendererAction<Player> initialize) {
            this.initialize = initialize;
        }

        public RendererAction<Player> getTermination() {
            return termination;
        }

        public void setTermination(RendererAction<Player> termination) {
            this.termination = termination;
        }

        public void runInitialization(Player player) {
            if (initialize != null)
                initialize.run(player);
        }

        public void runTermination(Player player) {
            if (termination != null)
                termination.run(player);
        }

    }

    @Override
    public abstract AbstractEntityRenderer clone();

    public int getID() {
        return id;
    }

    public void onRegister(EntityRendererWorker worker) {}

    public void onUnregister(EntityRendererWorker worker) {}

}
