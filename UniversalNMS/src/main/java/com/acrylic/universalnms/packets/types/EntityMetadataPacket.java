package com.acrylic.universalnms.packets.types;

import com.acrylic.universal.packet.SinglePacketWrapper;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public interface EntityMetadataPacket extends SinglePacketWrapper {

    void apply(@NotNull Entity entity);

}
