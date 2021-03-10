package com.acrylic.version_1_8_nms.packets;

import com.acrylic.universalnms.packets.types.TeleportPacket;
import com.acrylic.version_1_8_nms.NMSUtils;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class TeleportPacketImpl
        extends SinglePacketWrapperImpl
        implements TeleportPacket {

    private PacketPlayOutEntityTeleport teleportPacket;

    public void apply(net.minecraft.server.v1_8_R3.Entity entity, Location location) {
        int x = (int) Math.floor(location.getX() * 32.0D),
                y = (int) Math.floor(location.getY() * 32.0D),
                z = (int) Math.floor(location.getZ() * 32.0D);
        byte yaw = (byte)((int)(location.getYaw() * 256.0F / 360.0F)),
                pitch = (byte)((int)(location.getPitch() * 256.0F / 360.0F));
        teleportPacket = new PacketPlayOutEntityTeleport(entity.getId(), x, y, z, yaw, pitch, entity.onGround);
    }

    public void apply(net.minecraft.server.v1_8_R3.Entity entity) {
        teleportPacket = new PacketPlayOutEntityTeleport(entity);
    }

    @Override
    public void apply(@NotNull Entity entity, @NotNull Location location) {
        apply(NMSUtils.convertToNMSEntity(entity), location);
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
