package com.acrylic.version_1_8_nms.packets;

import com.acrylic.universalnms.packets.types.EntityMetadataPacket;
import com.acrylic.version_1_8_nms.NMSUtils;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class EntityMetadataPacketImpl
        extends SinglePacketWrapperImpl
        implements EntityMetadataPacket {

    private PacketPlayOutEntityMetadata packetPlayOutEntityMetadata;

    public void apply(@NotNull net.minecraft.server.v1_8_R3.Entity entity) {
        apply(entity, false);
    }

    public void apply(@NotNull net.minecraft.server.v1_8_R3.Entity entity, boolean b) {
        this.packetPlayOutEntityMetadata = new PacketPlayOutEntityMetadata(entity.getId(), entity.getDataWatcher(), b);
    }

    @Override
    public void apply(@NotNull Entity entity) {
        apply(NMSUtils.convertToNMSEntity(entity));
    }

    @Override
    public void apply(@NotNull Entity entity, boolean b) {
        apply(NMSUtils.convertToNMSEntity(entity), b);
    }

    @Override
    public PacketPlayOutEntityMetadata getPacket() {
        return packetPlayOutEntityMetadata;
    }
}
