package com.acrylic.universalnms.renderer;

import com.acrylic.universal.interfaces.Index;
import com.acrylic.universal.interfaces.Terminable;
import com.acrylic.universal.threads.Scheduler;
import com.acrylic.universal.threads.TaskType;
import com.acrylic.universalnms.NMSLib;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class EntityRendererWorker implements Runnable, Terminable, Index {

    protected static AtomicInteger RENDERER_ID_COUNTER = new AtomicInteger(0);

    private final Map<Integer, AbstractEntityRenderer> rendererMap = new HashMap<>();
    private int ticks = 0, ticksPerRender = 20;

    @Override
    public void terminate() {
        rendererMap.clear();
    }

    public void register(@NotNull AbstractEntityRenderer renderer) {
        rendererMap.put(renderer.getID(), renderer);
    }

    public void unregister(@NotNull AbstractEntityRenderer renderer) {
        rendererMap.remove(renderer.getID());
    }

    public boolean checkForRemoval(@NotNull AbstractEntityRenderer renderer) {
        if (shouldRemove(renderer)) {
            unregister(renderer);
            return true;
        }
        return false;
    }

    private boolean shouldRemove(AbstractEntityRenderer renderer) {
        return !renderer.isUsedByAnEntity();
    }

    @Override
    public void run() {
        increaseIndex();
        if (ticks % ticksPerRender == 0) {
            synchronized (rendererMap) {
                for (Iterator<Map.Entry<Integer, AbstractEntityRenderer>> it = rendererMap.entrySet().iterator(); it.hasNext(); ) {
                    AbstractEntityRenderer renderer = it.next().getValue();
                    if (!shouldRemove(renderer)) {
                        renderer.run();
                    } else {
                        it.remove();
                        Bukkit.broadcastMessage("Removed ");
                    }
                }
            }
        }
    }

    @Override
    public int getIndex() {
        return ticks;
    }

    @Override
    public int getIndexIncrement() {
        return 1;
    }

    @Override
    public void setIndex(int i) {
        this.ticks = i;
    }

    @NotNull
    public Map<Integer, AbstractEntityRenderer> getRendererMap() {
        return rendererMap;
    }

    public int getTicksPerRender() {
        return ticksPerRender;
    }

    public void setTicksPerRender(int ticksPerRender) {
        this.ticksPerRender = ticksPerRender;
    }
}
