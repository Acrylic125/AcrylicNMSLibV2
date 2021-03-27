package com.acrylic.version_1_16_nms.entity;

import com.acrylic.universalnms.entity.LivingEntityPacketHandler;
import com.acrylic.universalnms.entity.NMSGiantInstance;
import com.acrylic.universalnms.entity.entityconfiguration.EntityConfiguration;
import com.acrylic.universalnms.entity.entityconfiguration.LivingEntityConfiguration;
import com.acrylic.universalnms.entity.wrapper.NMSLivingEntityWrapper;
import com.acrylic.universalnms.entityai.EntityAI;
import com.acrylic.universalnms.renderer.PlayerCheckableRenderer;
import com.acrylic.universalnms.renderer.Renderer;
import com.acrylic.version_1_16_nms.NMSUtils;
import com.acrylic.version_1_16_nms.entity.wrapper.GiantWrapper;
import net.minecraft.server.v1_16_R3.EntityGiantZombie;
import net.minecraft.server.v1_16_R3.EntityTypes;
import org.bukkit.Location;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NMSGiantInstanceImpl
        extends NMSLivingEntityInstanceImpl
        implements NMSGiantInstance {

    private final GiantWrapper giant;
    private final LivingEntityPacketHandlerImpl entityPacketHandler;
    private LivingEntityConfiguration configuration = LivingEntityConfiguration.DEFAULT_LIVING_ENTITY;

    public NMSGiantInstanceImpl(@NotNull Location location, @Nullable PlayerCheckableRenderer renderer) {
        this.giant = new GiantWrapper(this, EntityTypes.GIANT, NMSUtils.convertToNMSWorld(location.getWorld()));
        giant.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        this.entityPacketHandler = new LivingEntityPacketHandlerImpl(this, renderer);
    }

    public NMSGiantInstanceImpl(@NotNull GiantWrapper giant, @Nullable PlayerCheckableRenderer renderer) {
        this.giant = giant;
        this.entityPacketHandler = new LivingEntityPacketHandlerImpl(this, renderer);
    }

    @NotNull
    @Override
    public Giant getBukkitEntity() {
        return (Giant) giant.getBukkitEntity();
    }

    @Override
    public LivingEntityPacketHandler getPacketHandler() {
        return entityPacketHandler;
    }

    @Override
    public NMSLivingEntityWrapper getEntityWrapper() {
        return giant;
    }

    @Override
    public EntityGiantZombie getNMSEntity() {
        return giant;
    }

    @Override
    public void setEntityConfiguration(@NotNull EntityConfiguration entityConfiguration) {
        if (!(entityConfiguration instanceof LivingEntityConfiguration))
            throw new RuntimeException("Living entities can only accept LivingEntityConfigurations.");
        this.configuration = (LivingEntityConfiguration) entityConfiguration;
    }

    @Override
    public LivingEntityConfiguration getEntityConfiguration() {
        return configuration;
    }
}
