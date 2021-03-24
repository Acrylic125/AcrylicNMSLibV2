package com.acrylic.universalnms.skins;

import com.acrylic.universal.threads.Scheduler;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class SkinMap {

    private final Map<String, Skin> skinMap;

    public SkinMap() {
        this(new HashMap<>());
    }

    public SkinMap(@NotNull Map<String, Skin> skinMap) {
        this.skinMap = skinMap;
    }

    public void asyncGetAndActOnSkin(@NotNull String id, @NotNull Consumer<Skin> action) {
        asyncGetAndActOnSkin(id, action, null);
    }

    public void asyncGetAndActOnSkin(@NotNull String id, @NotNull Consumer<Skin> action, @Nullable Runnable failed) {
        Scheduler.async().runTask().handleThenBuild(() -> {
            Skin skin = getSkin(id);
            if (skin == null) {
                if (failed != null)
                    failed.run();
            } else {
                action.accept(skin);
            }
        });
    }

    @Nullable
    public Skin getSkin(@NotNull String id) {
        Skin skin = getSkinFromMap(id);
        if (skin != null)
            return skin;
        skin = NameSkin.create(id);
        if (skin != null)
            addSkin(skin);
        return skin;
    }

    @Nullable
    public Skin getSkinFromMap(@NotNull String id) {
        return skinMap.get(id);
    }

    public void addSkin(@NotNull Skin skin) {
        skinMap.put(skin.getID(), skin);
    }

}


