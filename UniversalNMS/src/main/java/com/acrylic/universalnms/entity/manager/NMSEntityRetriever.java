package com.acrylic.universalnms.entity.manager;

import com.acrylic.universalnms.entity.NMSEntityInstance;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class NMSEntityRetriever<T extends NMSEntityInstance> {

    private final Map<Integer, T> cached;

    public NMSEntityRetriever() {
        this(new HashMap<>());
    }

    public NMSEntityRetriever(@NotNull Map<Integer, T> cached) {
        this.cached = cached;
    }

    @NotNull
    public Map<Integer, T> getCached() {
        return cached;
    }

    public void register(@NotNull T entity) {
        cached.put(entity.getBukkitEntity().getEntityId(), entity);
    }

    public void unregister(int id) {
        cached.remove(id);
    }

    public void unregister(@NotNull T entity) {
        unregister(entity.getBukkitEntity().getEntityId());
    }

    @Nullable
    public T get(int id) {
        return cached.get(id);
    }

    public T get(@NotNull Entity entity) {
        return get(entity.getEntityId());
    }

}
