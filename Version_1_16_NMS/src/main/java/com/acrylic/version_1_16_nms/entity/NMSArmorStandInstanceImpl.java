package com.acrylic.version_1_16_nms.entity;

import com.acrylic.universalnms.entity.NMSArmorStandInstance;
import com.acrylic.universalnms.entity.wrapper.NMSLivingEntityWrapper;
import com.acrylic.universalnms.entityai.EntityAI;
import com.acrylic.universalnms.renderer.Renderer;
import com.acrylic.version_1_16_nms.NMSUtils;
import com.acrylic.version_1_16_nms.entity.wrapper.ArmorStandWrapper;
import net.minecraft.server.v1_16_R3.EntityArmorStand;
import net.minecraft.server.v1_16_R3.Vector3f;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NMSArmorStandInstanceImpl
        extends NMSLivingEntityInstanceImpl
        implements NMSArmorStandInstance {

    private final ArmorStandWrapper armorStand;
    private final LivingEntityPacketHandlerImpl entityPacketHandler;

    public NMSArmorStandInstanceImpl(@NotNull Location location, @Nullable Renderer<Player> renderer) {
        this.armorStand = new ArmorStandWrapper(this, NMSUtils.convertToNMSWorld(location.getWorld()), location.getX(), location.getY(), location.getZ());
        this.entityPacketHandler = new LivingEntityPacketHandlerImpl(this, renderer);
    }

    public NMSArmorStandInstanceImpl(@NotNull ArmorStandWrapper armorStand, @Nullable Renderer<Player> renderer) {
        this.armorStand = armorStand;
        this.entityPacketHandler = new LivingEntityPacketHandlerImpl(this, renderer);
    }

    @NotNull
    @Override
    public ArmorStand getBukkitEntity() {
        return (ArmorStand) armorStand.getBukkitEntity();
    }

    @Override
    public boolean hasArms() {
        return armorStand.hasArms();
    }

    @Override
    public void setArms(boolean b) {
        armorStand.setArms(b);
    }

    @Override
    public boolean hasBasePlate() {
        return armorStand.hasBasePlate();
    }

    @Override
    public void setBasePlate(boolean b) {
        armorStand.setBasePlate(b);
    }

    @Override
    public boolean hasGravity() {
        return !armorStand.isNoGravity();
    }

    @Override
    public void setGravity(boolean b) {
        armorStand.setNoGravity(!b);
    }

    @Override
    public boolean isSmall() {
        return armorStand.isSmall();
    }

    @Override
    public void setSmall(boolean b) {
        armorStand.setSmall(b);
    }

    @Override
    public boolean isMarker() {
        return armorStand.isMarker();
    }

    @Override
    public void setMarker(boolean b) {
        armorStand.setMarker(b);
    }

    public void setRightArmPose(Vector3f vector3f) {
        armorStand.setRightArmPose(vector3f);
    }

    @Override
    public void setRightArmPose(@NotNull EulerAngle eulerAngle) {
        setRightArmPose(new Vector3f((float) Math.toDegrees(eulerAngle.getX()), (float) Math.toDegrees(eulerAngle.getY()), (float) Math.toDegrees(eulerAngle.getZ())));
    }

    public void setLeftArmPose(Vector3f vector3f) {
        armorStand.setLeftArmPose(vector3f);
    }

    @Override
    public void setLeftArmPose(@NotNull EulerAngle eulerAngle) {
        setLeftArmPose(new Vector3f((float) Math.toDegrees(eulerAngle.getX()), (float) Math.toDegrees(eulerAngle.getY()), (float) Math.toDegrees(eulerAngle.getZ())));
    }

    public void setRightLegPose(Vector3f vector3f) {
        armorStand.setRightLegPose(vector3f);
    }

    @Override
    public void setRightLegPose(@NotNull EulerAngle eulerAngle) {
        setRightLegPose(new Vector3f((float) Math.toDegrees(eulerAngle.getX()), (float) Math.toDegrees(eulerAngle.getY()), (float) Math.toDegrees(eulerAngle.getZ())));
    }

    public void setLeftLegPose(Vector3f vector3f) {
        armorStand.setLeftLegPose(vector3f);
    }

    @Override
    public void setLeftLegPose(@NotNull EulerAngle eulerAngle) {
        setLeftLegPose(new Vector3f((float) Math.toDegrees(eulerAngle.getX()), (float) Math.toDegrees(eulerAngle.getY()), (float) Math.toDegrees(eulerAngle.getZ())));
    }

    @Nullable
    @Override
    public EntityAI getAI() {
        return null;
    }

    @Override
    public LivingEntityPacketHandlerImpl getPacketHandler() {
        return entityPacketHandler;
    }

    @Override
    public NMSLivingEntityWrapper getEntityWrapper() {
        return armorStand;
    }

    @Override
    public EntityArmorStand getNMSEntity() {
        return armorStand;
    }
}