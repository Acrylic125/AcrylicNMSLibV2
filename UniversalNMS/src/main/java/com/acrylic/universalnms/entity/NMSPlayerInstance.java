package com.acrylic.universalnms.entity;

import com.acrylic.universalnms.enums.Gamemode;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface NMSPlayerInstance extends NMSLivingEntityInstance {

    @NotNull
    @Override
    Player getBukkitEntity();

    @Override
    PlayerPacketHandler getPacketHandler();

    void setGamemode(@NotNull Gamemode gamemode);

}
