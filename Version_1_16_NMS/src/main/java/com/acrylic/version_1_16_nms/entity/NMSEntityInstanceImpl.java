package com.acrylic.version_1_16_nms.entity;

import com.acrylic.universal.text.ChatUtils;
import com.acrylic.universalnms.entity.NMSEntityInstance;
import net.minecraft.server.v1_16_R3.Entity;
import net.minecraft.server.v1_16_R3.EnumMoveType;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.Vec3D;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public abstract class NMSEntityInstanceImpl
        implements NMSEntityInstance {

    @Override
    public abstract Entity getNMSEntity();

    @Override
    public void setName(String s) {
        if (s != null)
            getNMSEntity().setCustomName(IChatBaseComponent.ChatSerializer.a(ChatUtils.get(s)));
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
      //  entity.getWorld().removeEntity(entity);
    }

    @Override
    public void addToWorld() {
        Entity entity = getNMSEntity();
        entity.getWorld().addEntity(entity);
    }

    @Override
    public void setVelocity(double x, double y, double z) {
        getNMSEntity().move(EnumMoveType.SELF, new Vec3D(x, y, z));
    }

    @Override
    public void setVelocity(@NotNull Vector vector) {
        setVelocity(vector.getX(), vector.getY(), vector.getZ());
    }
}
