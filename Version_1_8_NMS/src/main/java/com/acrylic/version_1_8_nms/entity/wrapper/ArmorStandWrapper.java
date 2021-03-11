package com.acrylic.version_1_8_nms.entity.wrapper;

import com.acrylic.universalnms.entity.NMSLivingEntityInstance;
import com.acrylic.universalnms.entity.wrapper.NMSLivingEntityWrapper;
import com.acrylic.universalnms.nmsentityregistry.NMSEntity;
import com.acrylic.version_1_8_nms.entity.NMSArmorStandInstanceImpl;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

@NMSEntity(
        bukkitEntityTYpe = EntityType.ARMOR_STAND,
        name = "ArmorStandWrapper",
        entityClass = EntityArmorStand.class
)
public class ArmorStandWrapper extends EntityArmorStand implements NMSLivingEntityWrapper {

    private final NMSArmorStandInstanceImpl armorStandInstance;

    public ArmorStandWrapper(NMSArmorStandInstanceImpl armorStandInstance, World world) {
        super(world);
        this.armorStandInstance = armorStandInstance;
    }

    public ArmorStandWrapper(NMSArmorStandInstanceImpl armorStandInstance, World world, double d0, double d1, double d2) {
        super(world, d0, d1, d2);
        this.armorStandInstance = armorStandInstance;
    }

    @Override
    public void t_() {
        super.t_();
        tick();
    }

    @NotNull
    @Override
    public NMSArmorStandInstanceImpl getEntityInstance() {
        return armorStandInstance;
    }
}
