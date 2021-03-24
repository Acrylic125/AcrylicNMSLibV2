package com.acrylic.universalnms.skins;

import com.acrylic.universal.files.bukkit.Configuration;
import com.acrylic.universal.files.fileeditor.FileEditor;
import com.acrylic.universal.threads.Scheduler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class SkinMap {

    public static final String
            CONFIG_ROOT = "skins",
            CONFIG_SIGNATURE = "signature",
            CONFIG_TEXTURE = "texture";

    private final Map<String, Skin> skinMap;

    public SkinMap() {
        this(new HashMap<>());
    }

    public SkinMap(@NotNull Map<String, Skin> skinMap) {
        this.skinMap = skinMap;
    }

    public void loadSkinMapFromConfig(@NotNull Configuration configuration) {
        FileEditor skins = configuration.getFileEditor().getFileEditor(CONFIG_ROOT);
        skins.getContents().forEach((id, obj) -> {
            if (obj instanceof Map) {
                Map<?, ?> skinInfo = (Map<?, ?>) obj;
                Object signature = skinInfo.get(CONFIG_SIGNATURE),
                        texture = skinInfo.get(CONFIG_TEXTURE);
                if (signature != null && texture != null) {
                    SkinImpl skin = new SkinImpl(id, signature.toString(), texture.toString());
                    addSkin(skin);
                }
            }
        });
    }

    public void saveTo(@NotNull Configuration configuration) {
        FileEditor skins = configuration.getFileEditor().getFileEditor(CONFIG_ROOT);
        skinMap.forEach((id, skin) -> {
            Map<String, Object> skinInfo = new HashMap<>(2);
            skinInfo.put(CONFIG_SIGNATURE, skin.getSignature());
            skinInfo.put(CONFIG_TEXTURE, skin.getTexture());
            skins.set(id, skinInfo);
        });
        configuration.saveFile();
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
        skin = SkinImpl.create(id);
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


