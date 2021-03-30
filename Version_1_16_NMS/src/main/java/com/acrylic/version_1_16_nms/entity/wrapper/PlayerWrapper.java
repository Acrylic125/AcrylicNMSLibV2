package com.acrylic.version_1_16_nms.entity.wrapper;

import com.acrylic.universal.text.ChatUtils;
import com.acrylic.universalnms.entity.NMSLivingEntityInstance;
import com.acrylic.universalnms.entity.wrapper.NMSLivingEntityWrapper;
import com.acrylic.version_1_16_nms.NMSUtils;
import com.acrylic.version_1_16_nms.entity.NMSPlayerInstanceImpl;
import com.mojang.authlib.GameProfile;
import net.citizensnpcs.nms.v1_16_R3.entity.EntityHumanNPC;
import net.citizensnpcs.nms.v1_16_R3.util.NMSImpl;
import net.citizensnpcs.npc.ai.AStarNavigationStrategy;
import net.citizensnpcs.trait.Gravity;
import net.citizensnpcs.util.Util;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.event.CraftEventFactory;
import org.bukkit.plugin.SimplePluginManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.UUID;

/**
 * Optimisations adapted from
 * https://github.com/CitizensDev/Citizens2/blob/master/v1_16_R3/src/main/java/net/citizensnpcs/nms/v1_16_R3/entity/EntityHumanNPC.java
 */
public class PlayerWrapper
        extends EntityPlayer
        implements NMSLivingEntityWrapper {

    private final NMSPlayerInstanceImpl nmsPlayerInstance;

    public PlayerWrapper(@NotNull NMSPlayerInstanceImpl nmsPlayerInstance, MinecraftServer minecraftserver, WorldServer worldserver, GameProfile gameprofile, PlayerInteractManager playerinteractmanager) {
        super(minecraftserver, worldserver, gameprofile, playerinteractmanager);
        this.nmsPlayerInstance = nmsPlayerInstance;
        getDataWatcher().set(new DataWatcherObject<>(16, DataWatcherRegistry.a), (byte) 127);
        playerConnection = new EmptyPlayerConnection(NMSUtils.getMCServer(), new NetworkManager(EnumProtocolDirection.CLIENTBOUND), this);
    }

    public PlayerWrapper(@NotNull NMSPlayerInstanceImpl nmsPlayerInstance, @NotNull Location location, @Nullable String name) {
        super(NMSUtils.getMCServer(), NMSUtils.convertToWorldServer(location.getWorld()),
                new GameProfile(UUID.randomUUID(), (name == null) ? null : ChatUtils.get(name)),
                new PlayerInteractManager(NMSUtils.convertToWorldServer(location.getWorld())));
        this.nmsPlayerInstance = nmsPlayerInstance;
        getDataWatcher().set(new DataWatcherObject<>(16, DataWatcherRegistry.a), (byte) 127);
        playerConnection = new EmptyPlayerConnection(NMSUtils.getMCServer(), new NetworkManager(EnumProtocolDirection.CLIENTBOUND), this);
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

    @Override
    public CraftPlayer getBukkitEntity() {
        return super.getBukkitEntity();
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void playerTick() {
        this.entityBaseTick();

        Vec3D mot = this.getMot();
        if (Math.abs(mot.getX()) < 0.004999999888241291D && Math.abs(mot.getY()) < 0.004999999888241291D && Math.abs(mot.getZ()) < 0.004999999888241291D) {
            this.setMot(new Vec3D(0.0D, 0.0D, 0.0D));
        }
        this.ar = this.as;
        if (this.hurtTicks > 0) {
            --this.hurtTicks;
        }

        this.tickPotionEffects();
        this.aM = this.aL;
        this.aB = this.aA;
        this.aD = this.aC;
        this.lastYaw = this.yaw;
        this.lastPitch = this.pitch;
    }

    @Override
    public void tickWeather() {
        super.tickWeather();
    }

    @Override
    protected void doTick() {
        super.doTick();
    }

    @Override
    public void movementTick() {
        super.movementTick();
    }

    @Override
    public void entityBaseTick() {
        super.entityBaseTick();
    }

    @Override
    protected void tickPotionEffects() {
        super.tickPotionEffects();
    }

    @Override
    public boolean doAITick() {
        return super.doAITick();
    }

    @Override
    public void postTick() {
    }

    @Override
    protected void doPortalTick() {
    }
}
