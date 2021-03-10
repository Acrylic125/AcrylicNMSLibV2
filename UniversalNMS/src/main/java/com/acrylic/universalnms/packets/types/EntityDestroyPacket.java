package com.acrylic.universalnms.packets.types;

import com.acrylic.universalnms.packets.SinglePacketWrapper;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public interface EntityDestroyPacket extends SinglePacketWrapper {

    void apply(@NotNull Entity entity);

    default void apply(@NotNull Entity... entities) {
        int[] ids = new int[entities.length];
        for (int i = 0; i < entities.length; i++)
            ids[i] = entities[i].getEntityId();
        apply(ids);
    }

    void apply(@NotNull int[] ids);

}
