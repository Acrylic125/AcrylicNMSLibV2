package com.acrylic.version_1_8_nms.entity;

import com.acrylic.universalnms.entity.EntityPacketHandler;
import com.acrylic.universalnms.packets.types.EntitySpawnPacket;
import com.acrylic.universalnms.packets.types.TeleportPacket;
import com.acrylic.universalnms.renderer.Renderer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EntityPacketHandlerImpl implements EntityPacketHandler {

    private final NMSEntityInstanceImpl entityInstance;
    private Renderer<Player> renderer;

    public EntityPacketHandlerImpl(@NotNull NMSEntityInstanceImpl entityInstance, @NotNull Renderer<Player> renderer) {
        this.entityInstance = entityInstance;
        this.renderer = renderer;
    }

    @NotNull
    @Override
    public NMSEntityInstanceImpl getEntityInstance() {
        return entityInstance;
    }

    @Override
    public void setRenderer(@NotNull Renderer<Player> renderer) {
        this.renderer = renderer;
    }

    @NotNull
    @Override
    public Renderer<Player> getRenderer() {
        return renderer;
    }

    @NotNull
    @Override
    public EntitySpawnPacket getSpawnPacket() {
        return null;
    }

    @NotNull
    @Override
    public TeleportPacket getTeleportPacket() {
        return null;
    }
}
