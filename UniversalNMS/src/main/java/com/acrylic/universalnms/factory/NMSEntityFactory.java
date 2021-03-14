package com.acrylic.universalnms.factory;

import com.acrylic.universalnms.entity.*;
import com.acrylic.universalnms.renderer.PlayerCheckableRenderer;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface NMSEntityFactory {

    EntityPacketHandler getNewEntityPacketHandler(@NotNull NMSEntityInstance entityInstance, @Nullable PlayerCheckableRenderer renderer);

    default EntityPacketHandler getNewEntityPacketHandler(@NotNull NMSEntityInstance entityInstance) {
        return getNewEntityPacketHandler(entityInstance, null);
    }

    LivingEntityPacketHandler getNewLivingEntityPacketHandler(@NotNull NMSLivingEntityInstance entityInstance, @Nullable PlayerCheckableRenderer renderer);

    default LivingEntityPacketHandler getNewLivingEntityPacketHandler(@NotNull NMSLivingEntityInstance entityInstance) {
        return getNewLivingEntityPacketHandler(entityInstance, null);
    }

    NMSArmorStandInstance getNewNMSArmorStandInstance(@NotNull Location location, @Nullable PlayerCheckableRenderer renderer);

    default NMSArmorStandInstance getNewNMSArmorStandInstance(@NotNull Location location) {
        return getNewNMSArmorStandInstance(location, null);
    }

    NMSGiantInstance getNewNMSGiantInstance(@NotNull Location location, @Nullable PlayerCheckableRenderer renderer);

    default NMSGiantInstance getNewNMSGiantInstance(@NotNull Location location) {
        return getNewNMSGiantInstance(location, null);
    }
}
