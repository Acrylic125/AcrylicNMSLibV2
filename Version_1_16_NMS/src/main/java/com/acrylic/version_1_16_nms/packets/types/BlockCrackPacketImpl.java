package com.acrylic.version_1_16_nms.packets.types;

import com.acrylic.universalnms.packets.types.BlockCrackPacket;
import com.acrylic.version_1_16_nms.NMSUtils;
import com.acrylic.version_1_16_nms.packets.SinglePacketWrapperImpl;
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockBreakAnimation;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

public class BlockCrackPacketImpl
        extends SinglePacketWrapperImpl
        implements BlockCrackPacket {

    private PacketPlayOutBlockBreakAnimation packet;

    @Override
    public void apply(@NotNull Block block, int blockID, int damage) {
        this.packet = new PacketPlayOutBlockBreakAnimation(blockID, NMSUtils.getBlockPosition(block), damage);
    }

    @Override
    public PacketPlayOutBlockBreakAnimation getPacket() {
        return packet;
    }

}
