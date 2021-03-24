package com.acrylic.universalnms.entity;

import com.acrylic.universal.entity.ArmorStandInstance;
import com.acrylic.universal.entity.LivingEntityBuilder;
import com.acrylic.universalnms.NMSLib;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

public interface NMSArmorStandInstance
        extends NMSLivingEntityInstance, ArmorStandInstance {

    static Builder builder(Location location) {
        return new Builder(NMSLib.getEntityFactory().getNewNMSArmorStandInstance(location));
    }

    static Builder builder(NMSArmorStandInstance nmsArmorStandInstance) {
        return new Builder(nmsArmorStandInstance);
    }

    class Builder extends LivingEntityBuilder<Builder> {

        private final NMSArmorStandInstance armorStandInstance;

        public Builder(NMSArmorStandInstance armorStandInstance) {
            this.armorStandInstance = armorStandInstance;
        }

        public Builder arms(boolean arms) {
            this.armorStandInstance.setArms(arms);
            return this;
        }

        public Builder basePlate(boolean basePlate) {
            this.armorStandInstance.setBasePlate(basePlate);
            return this;
        }

        public Builder gravity(boolean gravity) {
            this.armorStandInstance.setGravity(gravity);
            return this;
        }

        public Builder small(boolean small) {
            this.armorStandInstance.setSmall(small);
            return this;
        }

        public Builder marker(boolean marker) {
            this.armorStandInstance.setMarker(marker);
            return this;
        }

        public Builder headPose(@NotNull EulerAngle eulerAngle) {
            this.armorStandInstance.setHeadPose(eulerAngle);
            return this;
        }

        public Builder rightArmPose(@NotNull EulerAngle eulerAngle) {
            this.armorStandInstance.setRightArmPose(eulerAngle);
            return this;
        }

        public Builder leftArmPose(@NotNull EulerAngle eulerAngle) {
            this.armorStandInstance.setLeftArmPose(eulerAngle);
            return this;
        }

        public Builder rightLegPose(@NotNull EulerAngle eulerAngle) {
            this.armorStandInstance.setRightLegPose(eulerAngle);
            return this;
        }

        public Builder leftLegPose(@NotNull EulerAngle eulerAngle) {
            this.armorStandInstance.setLeftLegPose(eulerAngle);
            return this;
        }

        public Builder asHologram() {
            this.armorStandInstance.asHologram();
            return this;
        }

        public ArmorStand buildEntity() {
            return this.armorStandInstance.getBukkitEntity();
        }

        public NMSArmorStandInstance buildEntityInstance() {
            return this.armorStandInstance;
        }

        public NMSArmorStandInstance getBuildFrom() {
            return this.armorStandInstance;
        }
    }

}
