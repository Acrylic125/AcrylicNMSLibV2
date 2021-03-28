package com.acrylic.version_1_16_nms.entity;

import com.acrylic.universal.text.ChatUtils;
import com.acrylic.universalnms.entity.NMSEntityInstance;
import com.acrylic.universalnms.entityai.EntityAI;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class NMSEntityInstanceImpl
        implements NMSEntityInstance {

    private EntityAI entityAI;
    private int instanceTicks = 0;
    private int mask = 0;

    @Override
    public abstract Entity getNMSEntity();

    @Override
    public void setName(String s) {
        if (s != null)
            getNMSEntity().setCustomName(new ChatComponentText(ChatUtils.get(s)));
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
    public boolean hasBeenAddedToWorld() {
        return (mask & 0x01) == 0x01;
    }

    @Override
    public void removeFromWorld() {
        Entity entity = getNMSEntity();
        ((WorldServer) entity.world).removeEntity(entity);
        mask &= ~0x01;
    }

    @Override
    public void addToWorld() {
        Entity entity = getNMSEntity();
        entity.getWorld().addEntity(entity);
        mask |= 0x01;
    }

    @Override
    public void setVelocity(double x, double y, double z) {
        getNMSEntity().move(EnumMoveType.SELF, new Vec3D(x, y, z));
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
        DataWatcherObject<Byte> obj = DataWatcherRegistry.a.a(0);
        dataWatcher.set(obj, (byte) ((b) ? (dataWatcher.get(obj) | mask) : (dataWatcher.get(obj) & ~mask)));
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
        getNMSEntity().move(EnumMoveType.SELF, new Vec3D(x, y, z));
    }

    @Override
    public int getInstanceTicks() {
        return instanceTicks;
    }

    @Override
    public void setInstanceTicks(int ticks) {
        this.instanceTicks = ticks;
    }

    @Override
    public boolean isOnGround() {
        return getNMSEntity().isOnGround();
    }
}
