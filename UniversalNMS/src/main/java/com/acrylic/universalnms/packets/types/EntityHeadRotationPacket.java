package com.acrylic.universalnms.packets.types;

import com.acrylic.universalnms.packets.SinglePacketWrapper;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public interface EntityHeadRotationPacket extends SinglePacketWrapper {

    void apply(@NotNull Entity entity);

    void apply(@NotNull Entity entity, float angle);

}
