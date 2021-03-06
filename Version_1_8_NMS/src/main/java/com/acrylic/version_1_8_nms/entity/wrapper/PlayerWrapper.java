package com.acrylic.version_1_8_nms.entity.wrapper;

import com.acrylic.universal.text.ChatUtils;
import com.acrylic.universalnms.entity.NMSLivingEntityInstance;
import com.acrylic.universalnms.entity.wrapper.NMSLivingEntityWrapper;
import com.acrylic.version_1_8_nms.NMSUtils;
import com.acrylic.version_1_8_nms.entity.NMSPlayerInstanceImpl;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_8_R3.*;
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
        DataWatcher dataWatcher = getDataWatcher();
        dataWatcher.watch(10, (byte) 127); //Displays the skin second layer.
        playerConnection = new EmptyPlayerConnection(NMSUtils.getMCServer(), new NetworkManager(EnumProtocolDirection.CLIENTBOUND), this);
    }

    public PlayerWrapper(@NotNull NMSPlayerInstanceImpl nmsPlayerInstance, @NotNull Location location, @Nullable String name) {
        super(NMSUtils.getMCServer(), NMSUtils.convertToWorldServer(location.getWorld()),
                new GameProfile(UUID.randomUUID(), (name == null) ? null : ChatUtils.get(name)),
                new PlayerInteractManager(NMSUtils.convertToWorldServer(location.getWorld())));
        this.nmsPlayerInstance = nmsPlayerInstance;
        DataWatcher dataWatcher = getDataWatcher();
        dataWatcher.watch(10, (byte) 127); //Displays the skin second layer.
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
    public void t_() {
        if (this.joining) {
            this.joining = false;
        }

        this.playerInteractManager.a();
        --this.invulnerableTicks;
        if (this.noDamageTicks > 0)
            --this.noDamageTicks;

        Entity entity = this.C();
        if (entity != this) {
            this.setLocation(entity.locX, entity.locY, entity.locZ, entity.yaw, entity.pitch);
            this.server.getPlayerList().d(this);
        }
        //super.t_();
    }
}
