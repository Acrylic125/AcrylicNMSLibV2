package com.acrylic.version_1_8_nms.packets.types;

import com.acrylic.universalnms.packets.types.EntitySpawnPacket;
import com.acrylic.version_1_8_nms.NMSUtils;
import com.acrylic.version_1_8_nms.packets.SinglePacketWrapperImpl;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntity;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class EntitySpawnPacketImpl
        extends SinglePacketWrapperImpl
        implements EntitySpawnPacket {

    private PacketPlayOutSpawnEntity packetPlayOutSpawnEntity;

    public void apply(net.minecraft.server.v1_8_R3.Entity entity, int entityTypeID) {
        packetPlayOutSpawnEntity = new PacketPlayOutSpawnEntity(entity, entityTypeID);
    }

    public void apply(net.minecraft.server.v1_8_R3.Entity entity) {
        apply(entity, entity.getBukkitEntity().getType().getTypeId());
    }

    @Override
    public void apply(@NotNull Entity entity) {
        packetPlayOutSpawnEntity = new PacketPlayOutSpawnEntity(NMSUtils.convertToNMSEntity(entity), entity.getType().getTypeId());
    }

    @Override
    public PacketPlayOutSpawnEntity getPacket() {
        return packetPlayOutSpawnEntity;
    }
}
