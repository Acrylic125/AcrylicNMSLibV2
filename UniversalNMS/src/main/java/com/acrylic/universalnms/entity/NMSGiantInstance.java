package com.acrylic.universalnms.entity;

import com.acrylic.universal.entity.GiantEntityInstance;
import com.acrylic.universal.entity.LivingEntityBuilder;
import com.acrylic.universalnms.NMSLib;
import org.bukkit.Location;
import org.bukkit.entity.Giant;

public interface NMSGiantInstance
        extends NMSLivingEntityInstance, GiantEntityInstance {

    static Builder builder(Location location) {
        return new Builder(NMSLib.getEntityFactory().getNewNMSGiantInstance(location));
    }

    static Builder builder(NMSGiantInstance nmsArmorStandInstance) {
        return new Builder(nmsArmorStandInstance);
    }

    class Builder extends LivingEntityBuilder<Builder> {

        private final NMSGiantInstance giantInstance;

        public Builder(NMSGiantInstance giantInstance) {
            this.giantInstance = giantInstance;
        }

        public Giant buildEntity() {
            return this.giantInstance.getBukkitEntity();
        }

        public NMSGiantInstance buildEntityInstance() {
            return this.giantInstance;
        }

        public NMSGiantInstance getBuildFrom() {
            return this.giantInstance;
        }
    }

}
