package com.acrylic.version_1_16_nms.packets.types;

import com.acrylic.universalnms.packets.types.TeleportPacket;
import com.acrylic.version_1_16_nms.NMSUtils;
import com.acrylic.version_1_16_nms.packets.SinglePacketWrapperImpl;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityTeleport;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class TeleportPacketImpl
        extends SinglePacketWrapperImpl
        implements TeleportPacket {

    private PacketPlayOutEntityTeleport teleportPacket;

    public void apply(net.minecraft.server.v1_16_R3.Entity entity) {
        teleportPacket = new PacketPlayOutEntityTeleport(entity);
    }

    @Override
    public void apply(@NotNull Entity entity, @NotNull Location location) {
        net.minecraft.server.v1_16_R3.Entity nmsEntity = NMSUtils.convertToNMSEntity(entity);
        nmsEntity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        apply(nmsEntity);
    }

    @Override
    public void apply(@NotNull Entity entity) {
        apply(NMSUtils.convertToNMSEntity(entity));
    }

    @Override
    public PacketPlayOutEntityTeleport getPacket() {
        return teleportPacket;
    }
}
