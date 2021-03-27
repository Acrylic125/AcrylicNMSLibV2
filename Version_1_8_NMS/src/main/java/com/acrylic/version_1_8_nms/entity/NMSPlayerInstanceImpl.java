package com.acrylic.version_1_8_nms.entity;

import com.acrylic.universalnms.entity.NMSPlayerInstance;
import com.acrylic.universalnms.entity.entityconfiguration.EntityConfiguration;
import com.acrylic.universalnms.entity.entityconfiguration.LivingEntityConfiguration;
import com.acrylic.universalnms.entity.wrapper.NMSLivingEntityWrapper;
import com.acrylic.universalnms.entityai.EntityAI;
import com.acrylic.universalnms.enums.Gamemode;
import com.acrylic.universalnms.renderer.PlayerCheckableRenderer;
import com.acrylic.version_1_8_nms.entity.wrapper.PlayerWrapper;
import com.acrylic.version_1_8_nms.packets.types.EntityOrientationPacketsImpl;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.WorldSettings;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class NMSPlayerInstanceImpl
        extends NMSLivingEntityInstanceImpl
        implements NMSPlayerInstance {

    private final PlayerWrapper playerWrapper;
    private final PlayerPacketHandlerImpl entityPacketHandler;
    private LivingEntityConfiguration configuration = LivingEntityConfiguration.DEFAULT_LIVING_ENTITY;

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

    @Override
    public PlayerPacketHandlerImpl getPacketHandler() {
        return entityPacketHandler;
    }

    @Override
    public void setGamemode(@NotNull Gamemode gamemode) {
        switch (gamemode) {
            case CREATIVE:
                playerWrapper.a(WorldSettings.EnumGamemode.CREATIVE);
                return;
            case SURVIVAL:
                playerWrapper.a(WorldSettings.EnumGamemode.SURVIVAL);
                return;
            case ADVENTURE:
                playerWrapper.a(WorldSettings.EnumGamemode.ADVENTURE);
                return;
            case SPECTATOR:
                playerWrapper.a(WorldSettings.EnumGamemode.SPECTATOR);
        }
    }

    @Override
    public void setSkinWithoutRefreshing(String signature, String texture) {
        PropertyMap propertyMap = playerWrapper.getProfile().getProperties();
        Collection<Property> textures = propertyMap.get("textures");
        if (textures != null)
            textures.clear();
        propertyMap.put("textures", new Property("textures", texture, signature));
    }

    @Override
    public NMSLivingEntityWrapper getEntityWrapper() {
        return playerWrapper;
    }

    @Override
    public EntityPlayer getNMSEntity() {
        return playerWrapper;
    }

    @Override
    public void setYaw(float yaw) {
        super.setYaw(yaw);
        EntityOrientationPacketsImpl entityOrientationPackets = getPacketHandler().getEntityOrientationPackets();
        entityOrientationPackets.apply(getNMSEntity(), getYaw(), getPitch());
        entityOrientationPackets.getSender().sendToAllByRenderer(entityPacketHandler.getRenderer());
    }

    @Override
    public void setPitch(float pitch) {
        super.setPitch(pitch);
        EntityOrientationPacketsImpl entityOrientationPackets = getPacketHandler().getEntityOrientationPackets();
        entityOrientationPackets.apply(getNMSEntity(), getYaw(), getPitch());
        entityOrientationPackets.getSender().sendToAllByRenderer(entityPacketHandler.getRenderer());
    }

    @Override
    public void setYawAndPitch(float yaw, float pitch) {
        super.setYawAndPitch(yaw, pitch);
        EntityOrientationPacketsImpl entityOrientationPackets = getPacketHandler().getEntityOrientationPackets();
        entityOrientationPackets.apply(getNMSEntity(), getYaw(), getPitch());
        entityOrientationPackets.getSender().sendToAllByRenderer(entityPacketHandler.getRenderer());
    }

    @Override
    public void setEntityConfiguration(@NotNull EntityConfiguration entityConfiguration) {
        if (!(entityConfiguration instanceof LivingEntityConfiguration))
            throw new RuntimeException("Living entities can only accept LivingEntityConfigurations.");
        this.configuration = (LivingEntityConfiguration) entityConfiguration;
    }

    @Override
    public EntityConfiguration getEntityConfiguration() {
        return configuration;
    }

}
