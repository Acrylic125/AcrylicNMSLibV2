package com.acrylic.version_1_8_nms.packets.types;

import com.acrylic.universalnms.packets.types.EntityOrientationPackets;
import com.acrylic.version_1_8_nms.NMSUtils;
import com.acrylic.version_1_8_nms.packets.MultiPacketWrapperImpl;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

import static com.acrylic.universalnms.packets.types.EntityOrientationPackets.floatToByteValue;

public class EntityOrientationPacketsImpl
        extends MultiPacketWrapperImpl
        implements EntityOrientationPackets {

    private final Collection<Packet<?>> packets = new ArrayList<>();

    public void apply(net.minecraft.server.v1_8_R3.Entity entity, float yaw, float pitch) {
        getPackets().clear();
        applyLookOrientation(entity, yaw, pitch);
        applyHeadOrientation(entity, yaw);
    }

    public void applyLookOrientation(net.minecraft.server.v1_8_R3.Entity entity, float yaw, float pitch) {
        packets.add(new PacketPlayOutEntity.PacketPlayOutEntityLook(entity.getId(), floatToByteValue(yaw), floatToByteValue(pitch), true));
    }

    public void applyHeadOrientation(net.minecraft.server.v1_8_R3.Entity entity, float yaw) {
        packets.add(new PacketPlayOutEntityHeadRotation(entity, floatToByteValue(yaw)));
    }

    @Override
    public void apply(@NotNull Entity entity, float yaw, float pitch) {
        apply(NMSUtils.convertToNMSEntity(entity), yaw, pitch);
    }

    @Override
    public void applyLookOrientation(@NotNull Entity entity, float yaw, float pitch) {
        applyLookOrientation(NMSUtils.convertToNMSEntity(entity), yaw, pitch);
    }

    @Override
    public void applyHeadOrientation(@NotNull Entity entity, float yaw) {
        applyHeadOrientation(NMSUtils.convertToNMSEntity(entity), yaw);
    }

    @Override
    public Collection<? extends Packet<?>> getPackets() {
        return packets;
    }
}
