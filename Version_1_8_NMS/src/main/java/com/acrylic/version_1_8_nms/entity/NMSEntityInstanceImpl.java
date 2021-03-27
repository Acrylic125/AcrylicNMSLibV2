package com.acrylic.version_1_8_nms.entity;

import com.acrylic.universal.text.ChatUtils;
import com.acrylic.universalnms.entity.NMSEntityInstance;
import com.acrylic.universalnms.entity.entityconfiguration.EntityConfiguration;
import com.acrylic.universalnms.entityai.EntityAI;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class NMSEntityInstanceImpl
        implements NMSEntityInstance {

    private EntityAI entityAI;
    private int instanceTicks = 0;

    @Override
    public abstract Entity getNMSEntity();

    @Override
    public void setName(String s) {
        if (s != null)
            getNMSEntity().setCustomName(ChatUtils.get(s));
    }

    @Override
    public void setNameVisible(boolean b) {
        getNMSEntity().setCustomNameVisible(b);
    }

    @Override
    public int getTicksLived() {
        return getNMSEntity().ticksLived;
    }

    @Override
    public void setTicksLived(int ticks) {
        getNMSEntity().ticksLived = ticks;
    }

    @Override
    public int getFireTicks() {
        return getNMSEntity().fireTicks;
    }

    @Override
    public void setFireTicks(int ticks) {
        getNMSEntity().fireTicks = ticks;
    }

    @Override
    public boolean isNoClip() {
        return getNMSEntity().noclip;
    }

    @Override
    public void setNoClip(boolean noClip) {
        getNMSEntity().noclip = noClip;
    }

    @Override
    public void removeFromWorld() {
        Entity entity = getNMSEntity();
        entity.getWorld().removeEntity(entity);
    }

    @Override
    public void addToWorld() {
        Entity entity = getNMSEntity();
        entity.getWorld().addEntity(entity);
    }

    @Override
    public void setVelocity(double x, double y, double z) {
        getNMSEntity().move(x, y, z);
    }

    @Override
    public void setVelocity(@NotNull Vector vector) {
        setVelocity(vector.getX(), vector.getY(), vector.getZ());
    }

    @Override
    public float getYaw() {
        return getNMSEntity().yaw;
    }

    @Override
    public void setYaw(float yaw) {
        getNMSEntity().yaw = yaw;
    }

    @Override
    public float getPitch() {
        return getNMSEntity().pitch;
    }

    @Override
    public void setPitch(float pitch) {
        getNMSEntity().pitch = pitch;
    }

    @Override
    public void setYawAndPitch(float yaw, float pitch) {
        Entity entity = getNMSEntity();
        entity.yaw = yaw;
        entity.pitch = pitch;
    }

    @Override
    public void setAnimationDataWatcher(int mask, boolean b) {
        DataWatcher dataWatcher = getNMSEntity().getDataWatcher();
        int fullMask = dataWatcher.getByte(0);
        dataWatcher.watch(0, (byte) ((b) ? (fullMask | mask) : (fullMask & ~mask)));
    }

    @Override
    public void setAI(@Nullable EntityAI entityAI) {
        this.entityAI = entityAI;
    }

    @Nullable
    @Override
    public EntityAI getAI() {
        return entityAI;
    }

    @Override
    public void move(double x, double y, double z) {
        getNMSEntity().move(x, y, z);
    }

    @Override
    public int getInstanceTicks() {
        return instanceTicks;
    }

    @Override
    public void setInstanceTicks(int ticks) {
        this.instanceTicks = ticks;
    }
}
