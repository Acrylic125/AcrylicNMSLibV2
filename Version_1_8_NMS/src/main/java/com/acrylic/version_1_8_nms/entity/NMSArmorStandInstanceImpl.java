package com.acrylic.version_1_8_nms.entity;

import com.acrylic.universal.entity.equipment.EntityEquipmentBuilder;
import com.acrylic.universalnms.entity.EntityPacketHandler;
import com.acrylic.universalnms.entity.NMSArmorStandInstance;
import com.acrylic.universalnms.entityai.EntityAI;
import com.acrylic.version_1_8_nms.NMSUtils;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.Vector3f;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NMSArmorStandInstanceImpl
        extends NMSLivingEntityInstanceImpl
        implements NMSArmorStandInstance {

    private final EntityArmorStand armorStand;

    public NMSArmorStandInstanceImpl(@NotNull Location location) {
        this.armorStand = new EntityArmorStand(NMSUtils.convertToNMSWorld(location.getWorld()), location.getX(), location.getY(), location.getZ());
    }

    public NMSArmorStandInstanceImpl(@NotNull EntityArmorStand armorStand) {
        this.armorStand = armorStand;
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
        return armorStand.hasGravity();
    }

    @Override
    public void setGravity(boolean b) {
        armorStand.setGravity(b);
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
        return armorStand.s();
    }

    @Override
    public void setMarker(boolean b) {
        armorStand.n(b);
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
    public EntityPacketHandler getDisplayer() {
        return null;
    }

    @Override
    public void tick() {

    }

    @Override
    public void setEquipment(@NotNull EntityEquipmentBuilder entityEquipmentBuilder) {

    }

    @Override
    public void setEquipment(@NotNull EntityEquipment entityEquipment) {

    }

    @Override
    public EntityArmorStand getNMSEntity() {
        return armorStand;
    }
}
