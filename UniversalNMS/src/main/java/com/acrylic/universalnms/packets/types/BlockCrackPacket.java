package com.acrylic.universalnms.packets.types;

import com.acrylic.universal.packet.SinglePacketWrapper;
import com.acrylic.universal.utils.BukkitHashCode;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

public interface BlockCrackPacket extends SinglePacketWrapper {

    void apply(@NotNull Block block, int blockID, int damage);

    default void apply(@NotNull Block block, int damage) {
        apply(block, BukkitHashCode.getHashCode(block), damage);
    }

}
