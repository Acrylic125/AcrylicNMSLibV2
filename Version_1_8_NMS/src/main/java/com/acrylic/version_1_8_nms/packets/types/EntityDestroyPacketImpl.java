package com.acrylic.version_1_8_nms.packets.types;

import com.acrylic.universalnms.packets.types.EntityDestroyPacket;
import com.acrylic.version_1_8_nms.NMSUtils;
import com.acrylic.version_1_8_nms.packets.SinglePacketWrapperImpl;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class EntityDestroyPacketImpl
        extends SinglePacketWrapperImpl
        implements EntityDestroyPacket {

    private PacketPlayOutEntityDestroy packetPlayOutEntityDestroy;

    public void apply(net.minecraft.server.v1_8_R3.Entity entity) {
        packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(entity.getId());
    }

    public void apply(net.minecraft.server.v1_8_R3.Entity... entities) {
        int[] ids = new int[entities.length];
        for (int i = 0; i < entities.length; i++)
            ids[i] = entities[i].getId();
        apply(ids);
    }

    @Override
    public void apply(@NotNull Entity entity) {
        apply(NMSUtils.convertToNMSEntity(entity));
    }

    @Override
    public void apply(@NotNull int[] ids) {
        packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(ids);
    }

    @Override
    public PacketPlayOutEntityDestroy getPacket() {
        return packetPlayOutEntityDestroy;
    }
}
