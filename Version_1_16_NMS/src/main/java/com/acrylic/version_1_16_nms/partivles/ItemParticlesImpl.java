package com.acrylic.version_1_16_nms.partivles;

import com.acrylic.universalnms.particles.ItemParticles;
import com.acrylic.version_1_16_nms.NMSUtils;
import com.comphenix.protocol.wrappers.EnumWrappers;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftMagicNumbers;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemParticlesImpl
        extends AbstractParticlesImpl
        implements ItemParticles {

    private ItemStack item;
    private boolean isValidItem = false;
    private EnumWrappers.Particle particleType;

    @Override
    public void setItem(ItemStack item) {
        if (item != null) {
            this.item = item;
            isValidItem = true;
        } else
            isValidItem = false;
    }

    @Override
    public EnumWrappers.Particle getParticleType() {
        return particleType;
    }

    @Override
    public void setParticleType(@NotNull EnumWrappers.Particle particle) {
        this.particleType = particle;
    }

    @Override
    public void build() {
        if (location == null || !isValidItem)
            throw new IllegalStateException("A valid item, a location and the particle type (It must either be ITEM_CRACK, BLOCK_CRACK, or BLOCK_DUST) must be specified in order to build a particle.");
        ParticleParam particleParam;
        switch (this.particleType) {
            case ITEM_CRACK:
                particleParam = new ParticleParamItem(Particles.ITEM, NMSUtils.convertToNMSItem(item));
                break;
            case BLOCK_CRACK:
                particleParam = new ParticleParamBlock(Particles.BLOCK, CraftMagicNumbers.getBlock(item.getType(), (byte) 0));
                break;
            case FALLING_DUST:
                particleParam = new ParticleParamBlock(Particles.FALLING_DUST, CraftMagicNumbers.getBlock(item.getType(), (byte) 0));
                break;
            default:
                particleParam = null;
                break;
        }
        if (particleParam == null)
            throw new IllegalStateException("A valid item, a location and the particle type (It must either be ITEM_CRACK, BLOCK_CRACK, or BLOCK_DUST) must be specified in order to build a particle.");
        this.packet = (offset == null) ?
                new PacketPlayOutWorldParticles(particleParam,
                        this.longDistance, this.location[0], this.location[1], this.location[2],
                        0, 0, 0,
                        this.speed, this.amount
                )
                :
                new PacketPlayOutWorldParticles(particleParam,
                        this.longDistance, this.location[0], this.location[1], this.location[2],
                        offset[0], offset[1], offset[2],
                        this.speed, this.amount
                );
    }

}
