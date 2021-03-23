package com.acrylic.version_1_8_nms.entity;

import com.acrylic.universalnms.entity.NMSPlayerInstance;
import com.acrylic.universalnms.entity.wrapper.NMSLivingEntityWrapper;
import com.acrylic.universalnms.entityai.EntityAI;
import com.acrylic.universalnms.renderer.PlayerCheckableRenderer;
import com.acrylic.version_1_8_nms.entity.wrapper.PlayerWrapper;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NMSPlayerInstanceImpl
        extends NMSLivingEntityInstanceImpl
        implements NMSPlayerInstance {

    private final PlayerWrapper playerWrapper;
    private final PlayerPacketHandlerImpl entityPacketHandler;

    public NMSPlayerInstanceImpl(@NotNull Location location, @Nullable PlayerCheckableRenderer renderer, @Nullable String name) {
        this.playerWrapper = new PlayerWrapper(this, location, name);
        playerWrapper.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        this.entityPacketHandler = new PlayerPacketHandlerImpl(this, renderer);
    }

    @NotNull
    @Override
    public Player getBukkitEntity() {
        return playerWrapper.getBukkitEntity();
    }

    @Nullable
    @Override
    public EntityAI getAI() {
        return null;
    }

    @Override
    public PlayerPacketHandlerImpl getPacketHandler() {
        return entityPacketHandler;
    }

    @Override
    public NMSLivingEntityWrapper getEntityWrapper() {
        return playerWrapper;
    }

    @Override
    public EntityPlayer getNMSEntity() {
        return playerWrapper;
    }

}
