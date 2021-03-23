package com.acrylic.version_1_16_nms.entity;

import com.acrylic.universal.gui.items.GUIClickableItem;
import com.acrylic.universalnms.entity.NMSPlayerInstance;
import com.acrylic.universalnms.entity.wrapper.NMSLivingEntityWrapper;
import com.acrylic.universalnms.entityai.EntityAI;
import com.acrylic.universalnms.enums.Gamemode;
import com.acrylic.universalnms.renderer.PlayerCheckableRenderer;
import com.acrylic.universalnms.skins.Skin;
import com.acrylic.version_1_16_nms.entity.wrapper.PlayerWrapper;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

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
    public void setSkin(String signature, String texture) {
        playerWrapper.getProfile().getProperties().put("textures", new Property("textures", texture, signature));
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
