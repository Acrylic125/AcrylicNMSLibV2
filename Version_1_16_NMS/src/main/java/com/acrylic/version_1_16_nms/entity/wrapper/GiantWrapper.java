package com.acrylic.version_1_16_nms.entity.wrapper;

import com.acrylic.universalnms.entity.NMSLivingEntityInstance;
import com.acrylic.universalnms.entity.wrapper.NMSLivingEntityWrapper;
import com.acrylic.universalnms.nmsentityregistry.NMSEntity;
import com.acrylic.version_1_16_nms.entity.NMSGiantInstanceImpl;
import net.minecraft.server.v1_16_R3.EntityGiantZombie;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.World;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

@NMSEntity(
        bukkitEntityTYpe = EntityType.GIANT,
        name = "GiantWrapper",
        entityClass = EntityGiantZombie.class
)
public class GiantWrapper
        extends EntityGiantZombie
        implements NMSLivingEntityWrapper {

    private final NMSGiantInstanceImpl giantInstance;

    public GiantWrapper(NMSGiantInstanceImpl giantInstance, EntityTypes<? extends EntityGiantZombie> var0, World var1) {
        super(var0, var1);
        this.giantInstance = giantInstance;
    }

    @NotNull
    @Override
    public NMSLivingEntityInstance getEntityInstance() {
        return null;
    }




}
