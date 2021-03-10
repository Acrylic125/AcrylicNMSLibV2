package com.acrylic.version_1_8_nms.entity.wrapper;

import com.acrylic.universalnms.nmsentityregistry.NMSEntity;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.entity.EntityType;

@NMSEntity(
        bukkitEntityTYpe = EntityType.ARMOR_STAND,
        name = "ArmorStandWrapper",
        entityClass = EntityArmorStand.class
)
public class ArmorStandWrapper extends EntityArmorStand {

    public ArmorStandWrapper(World world) {
        super(world);
    }

    public ArmorStandWrapper(World world, double d0, double d1, double d2) {
        super(world, d0, d1, d2);
    }

    @Override
    public void t_() {
        super.t_();
    }
}
