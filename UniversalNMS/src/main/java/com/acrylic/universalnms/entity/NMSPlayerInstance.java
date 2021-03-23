package com.acrylic.universalnms.entity;

import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.enums.Gamemode;
import com.acrylic.universalnms.skins.Skin;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface NMSPlayerInstance
        extends NMSLivingEntityInstance {

    @NotNull
    @Override
    Player getBukkitEntity();

    @Override
    PlayerPacketHandler getPacketHandler();

    void setGamemode(@NotNull Gamemode gamemode);

    void setSkin(String signature, String texture);

    default void setSkin(@Nullable Skin skin) {
        if (skin == null)
            setSkin(null, null);
        else
            setSkin(skin.getSignature(), skin.getTexture());
    }

    default void setSkin(@Nullable String skinName) {
        if (skinName == null)
            setSkin(null, null);
        else
            NMSLib.getSkinMap().asyncGetAndActOnSkin(skinName, this::setSkin);
    }

}
