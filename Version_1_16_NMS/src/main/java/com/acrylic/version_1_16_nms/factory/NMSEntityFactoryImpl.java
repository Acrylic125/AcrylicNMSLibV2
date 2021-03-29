package com.acrylic.version_1_16_nms.factory;

import com.acrylic.universalnms.entity.*;
import com.acrylic.universalnms.factory.NMSEntityFactory;
import com.acrylic.universalnms.renderer.PlayerInitializableRenderer;
import com.acrylic.version_1_16_nms.entity.*;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NMSEntityFactoryImpl implements NMSEntityFactory {

    @Override
    public EntityPacketHandler getNewEntityPacketHandler(@NotNull NMSEntityInstance entityInstance, @Nullable PlayerInitializableRenderer renderer) {
        return new EntityPacketHandlerImpl((NMSEntityInstanceImpl) entityInstance, renderer);
    }

    @Override
    public LivingEntityPacketHandler getNewLivingEntityPacketHandler(@NotNull NMSLivingEntityInstance entityInstance, @Nullable PlayerInitializableRenderer renderer) {
        return new LivingEntityPacketHandlerImpl((NMSLivingEntityInstanceImpl) entityInstance, renderer);
    }

    @Override
    public NMSArmorStandInstance getNewNMSArmorStandInstance(@NotNull Location location, @Nullable PlayerInitializableRenderer renderer) {
        return new NMSArmorStandInstanceImpl(location, renderer);
    }

    @Override
    public NMSGiantInstance getNewNMSGiantInstance(@NotNull Location location, @Nullable PlayerInitializableRenderer renderer) {
        return new NMSGiantInstanceImpl(location, renderer);
    }

    @Override
    public NMSPlayerInstance getNewNMSPlayerInstance(@NotNull Location location, @Nullable PlayerInitializableRenderer renderer, @Nullable String name) {
        return new NMSPlayerInstanceImpl(location, renderer, name);
    }
}
