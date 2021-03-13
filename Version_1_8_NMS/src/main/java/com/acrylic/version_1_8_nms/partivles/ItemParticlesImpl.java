package com.acrylic.version_1_8_nms.partivles;

import com.acrylic.universalnms.particles.ItemParticles;
import com.acrylic.version_1_8_nms.NMSUtils;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.inventory.ItemStack;

public class ItemParticlesImpl
        extends ParticlesImpl
        implements ItemParticles {

    private int itemId = 0;
    private int data = 0;
    private boolean isValidItem = false;

    @Override
    public void setItem(ItemStack item) {
        net.minecraft.server.v1_8_R3.ItemStack itemStack = NMSUtils.convertToNMSItem(item);
        if (itemStack != null) {
            itemId = Item.getId(itemStack.getItem());
            data = itemStack.getData();
            isValidItem = true;
        } else
            isValidItem = false;
    }

    @Override
    public void build() {
        if (particleType == null || location == null || !isValidItem)
            throw new IllegalStateException("A valid item, a location and the particle type must be specified in order to build a particle.");
        this.packet = (offset == null) ?
                new PacketPlayOutWorldParticles(particleType,
                        this.longDistance, this.location[0], this.location[1], this.location[2],
                        0, 0, 0,
                        this.speed, this.amount,
                        itemId, data
                )
                :
                new PacketPlayOutWorldParticles(particleType,
                        this.longDistance, this.location[0], this.location[1], this.location[2],
                        offset[0], offset[1], offset[2],
                        this.speed, this.amount,
                        itemId, data
                );
    }

}
