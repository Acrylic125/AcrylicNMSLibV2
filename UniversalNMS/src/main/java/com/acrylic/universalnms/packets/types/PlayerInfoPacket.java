package com.acrylic.universalnms.packets.types;

import com.acrylic.universalnms.packets.SinglePacketWrapper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface PlayerInfoPacket extends SinglePacketWrapper {

    enum Info {
        ADD_PLAYER,
        UPDATE_GAME_MODE,
        UPDATE_LATENCY,
        UPDATE_DISPLAY_NAME,
        REMOVE_PLAYER;
    }

    void apply(@NotNull Info info, @NotNull Player player);

    void apply(@NotNull Info info, @NotNull Player... players);

}
