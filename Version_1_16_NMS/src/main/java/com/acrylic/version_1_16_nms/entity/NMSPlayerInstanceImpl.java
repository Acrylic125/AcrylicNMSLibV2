package com.acrylic.version_1_16_nms.entity;

import com.acrylic.universalnms.entity.NMSPlayerInstance;
import com.acrylic.universalnms.entity.entityconfiguration.EntityConfiguration;
import com.acrylic.universalnms.entity.entityconfiguration.LivingEntityConfiguration;
import com.acrylic.universalnms.entity.entityconfiguration.LivingEntityConfigurationImpl;
import com.acrylic.universalnms.entity.wrapper.NMSLivingEntityWrapper;
import com.acrylic.universalnms.entityai.EntityAI;
import com.acrylic.universalnms.enums.Gamemode;
import com.acrylic.universalnms.renderer.PlayerCheckableRenderer;
import com.acrylic.version_1_16_nms.entity.wrapper.PlayerWrapper;
import com.acrylic.version_1_16_nms.packets.types.EntityOrientationPacketsImpl;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import net.minecraft.server.v1_16_R3.ChunkProviderServer;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.EnumGamemode;
import net.minecraft.server.v1_16_R3.PlayerChunkMap;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
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
                playerWrapper.a(EnumGamemode.CREATIVE);
                return;
            case SURVIVAL:
                playerWrapper.a(EnumGamemode.SURVIVAL);
                return;
            case ADVENTURE:
                playerWrapper.a(EnumGamemode.ADVENTURE);
                return;
            case SPECTATOR:
                playerWrapper.a(EnumGamemode.SPECTATOR);
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
    public void addToWorld() {
        int viewDistance = -1;
        PlayerChunkMap chunkMap = null;
        try {
            getBukkitEntity();
            chunkMap = ((ChunkProviderServer) getNMSEntity().world.getChunkProvider()).playerChunkMap;
            viewDistance = (int) PLAYER_CHUNK_MAP_VIEW_DISTANCE_GETTER.invoke(chunkMap);
            PLAYER_CHUNK_MAP_VIEW_DISTANCE_SETTER.invoke(chunkMap, -1);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        super.addToWorld();
        try {
            if (chunkMap != null) {
                PLAYER_CHUNK_MAP_VIEW_DISTANCE_SETTER.invoke(chunkMap, viewDistance);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
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

    private static final MethodHandle PLAYER_CHUNK_MAP_VIEW_DISTANCE_GETTER = getGetter(PlayerChunkMap.class,
            "viewDistance");
    private static final MethodHandle PLAYER_CHUNK_MAP_VIEW_DISTANCE_SETTER = getSetter(PlayerChunkMap.class,
            "viewDistance");

    public static MethodHandle getSetter(Class<?> clazz, String name) {
        try {
            return MethodHandles.lookup().unreflectSetter(getField(clazz, name));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public static MethodHandle getGetter(Class<?> clazz, String name) {
        try {
            return MethodHandles.lookup().unreflectGetter(getField(clazz, name));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }


    public static Field getField(Class<?> clazz, String field) {
        if (clazz == null)
            return null;
        Field f;
        try {
            f = clazz.getDeclaredField(field);
            f.setAccessible(true);
            return f;
        } catch (Exception e) {
            return null;
        }
    }

}
