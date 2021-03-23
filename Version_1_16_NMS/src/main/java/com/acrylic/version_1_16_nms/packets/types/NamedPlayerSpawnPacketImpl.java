package com.acrylic.version_1_16_nms.packets.types;

import com.acrylic.universalnms.packets.types.NamedPlayerSpawnPacket;
import com.acrylic.version_1_16_nms.NMSUtils;
import com.acrylic.version_1_16_nms.packets.SinglePacketWrapperImpl;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.PacketPlayOutNamedEntitySpawn;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NamedPlayerSpawnPacketImpl
        extends SinglePacketWrapperImpl
        implements NamedPlayerSpawnPacket {

    private PacketPlayOutNamedEntitySpawn packetPlayOutSpawnEntity;

    public void apply(EntityPlayer entity) {
        packetPlayOutSpawnEntity = new PacketPlayOutNamedEntitySpawn(entity);
    }

    @Override
    public void apply(@NotNull Entity entity) {
        if (entity instanceof Player)
            apply(NMSUtils.convertToNMSPlayer((Player) entity));
        else
            throw new IllegalArgumentException("The entity provided must be a player.");
    }

    @Override
    public PacketPlayOutNamedEntitySpawn getPacket() {
        return packetPlayOutSpawnEntity;
    }
}
