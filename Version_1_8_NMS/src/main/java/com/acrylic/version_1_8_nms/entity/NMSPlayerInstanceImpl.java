package com.acrylic.version_1_8_nms.entity;

import com.acrylic.universalnms.entity.NMSPlayerInstance;
import com.acrylic.universalnms.entity.wrapper.NMSLivingEntityWrapper;
import com.acrylic.universalnms.entityai.EntityAI;
import com.acrylic.universalnms.enums.Gamemode;
import com.acrylic.universalnms.renderer.PlayerCheckableRenderer;
import com.acrylic.version_1_8_nms.entity.wrapper.PlayerWrapper;
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
        getPacketHandler().getEntityOrientationPackets().apply(getNMSEntity(), getYaw(), getPitch());
    }

    @Override
    public void setPitch(float pitch) {
        super.setPitch(pitch);
        getPacketHandler().getEntityOrientationPackets().apply(getNMSEntity(), getYaw(), getPitch());
    }

    @Override
    public void setYawAndPitch(float yaw, float pitch) {
        super.setYawAndPitch(yaw, pitch);
        getPacketHandler().getEntityOrientationPackets().apply(getNMSEntity(), getYaw(), getPitch());
    }

}
