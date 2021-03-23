package com.acrylic.version_1_8_nms.packets.types;

import com.acrylic.universalnms.packets.types.EntityHeadRotationPacket;
import com.acrylic.version_1_8_nms.NMSUtils;
import com.acrylic.version_1_8_nms.packets.SinglePacketWrapperImpl;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class EntityHeadRotationPacketImpl
        extends SinglePacketWrapperImpl
        implements EntityHeadRotationPacket {

    private PacketPlayOutEntityHeadRotation packet;

    @Override
    public PacketPlayOutEntityHeadRotation getPacket() {
        return packet;
    }

    @Override
    public void apply(@NotNull Entity entity) {
        apply(NMSUtils.convertToNMSEntity(entity));
    }

    @Override
    public void apply(@NotNull Entity entity, float angle) {
        apply(NMSUtils.convertToNMSEntity(entity), angle);
    }

    public void apply(net.minecraft.server.v1_8_R3.Entity entity) {
        apply(entity, entity.getBukkitEntity().getLocation().getYaw());
    }

    public void apply(net.minecraft.server.v1_8_R3.Entity entity, float angle) {
        packet = new PacketPlayOutEntityHeadRotation(entity, NMSUtils.getByteAngle(angle));
    }

}
