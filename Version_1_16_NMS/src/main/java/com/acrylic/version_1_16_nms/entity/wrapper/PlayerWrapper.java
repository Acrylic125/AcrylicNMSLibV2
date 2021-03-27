package com.acrylic.version_1_16_nms.entity.wrapper;

import com.acrylic.universal.text.ChatUtils;
import com.acrylic.universalnms.entity.NMSLivingEntityInstance;
import com.acrylic.universalnms.entity.wrapper.NMSLivingEntityWrapper;
import com.acrylic.version_1_16_nms.NMSUtils;
import com.acrylic.version_1_16_nms.entity.NMSPlayerInstanceImpl;
import com.mojang.authlib.GameProfile;
import net.citizensnpcs.npc.ai.AStarNavigationStrategy;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class PlayerWrapper
        extends EntityPlayer
        implements NMSLivingEntityWrapper {

    private final NMSPlayerInstanceImpl nmsPlayerInstance;

    public PlayerWrapper(@NotNull NMSPlayerInstanceImpl nmsPlayerInstance, MinecraftServer minecraftserver, WorldServer worldserver, GameProfile gameprofile, PlayerInteractManager playerinteractmanager) {
        super(minecraftserver, worldserver, gameprofile, playerinteractmanager);
        this.nmsPlayerInstance = nmsPlayerInstance;
        getDataWatcher().set(new DataWatcherObject<>(16, DataWatcherRegistry.a), (byte) 127);
        playerConnection = new PlayerConnection(NMSUtils.getMCServer(), new NetworkManager(EnumProtocolDirection.SERVERBOUND), this);
    }

    public PlayerWrapper(@NotNull NMSPlayerInstanceImpl nmsPlayerInstance, @NotNull Location location, @Nullable String name) {
        super(NMSUtils.getMCServer(), NMSUtils.convertToWorldServer(location.getWorld()),
                new GameProfile(UUID.randomUUID(), (name == null) ? null : ChatUtils.get(name)),
                new PlayerInteractManager(NMSUtils.convertToWorldServer(location.getWorld())));
        this.nmsPlayerInstance = nmsPlayerInstance;
        getDataWatcher().set(new DataWatcherObject<>(16, DataWatcherRegistry.a), (byte) 127);
        playerConnection = new PlayerConnection(NMSUtils.getMCServer(), new NetworkManager(EnumProtocolDirection.SERVERBOUND), this);
    }

    @NotNull
    @Override
    public NMSLivingEntityInstance getEntityInstance() {
        return nmsPlayerInstance;
    }

    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);
        onDeath();
    }
}
