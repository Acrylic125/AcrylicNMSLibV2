package com.acrylic.universalnms.renderer;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.UUID;

public class EntityPlayerCheckableRenderer extends RangedPlayerCheckableRenderer {

    private final Entity entity;

    public EntityPlayerCheckableRenderer(@NotNull Entity entity) {
        super();
        this.entity = entity;
    }

    public EntityPlayerCheckableRenderer(@NotNull Entity entity, @NotNull Collection<UUID> rendered) {
        super(rendered);
        this.entity = entity;
    }

    @Override
    public synchronized void doChecks() {
        setLocation(entity.getLocation());
        super.doChecks();
    }

    @Override
    public EntityPlayerCheckableRenderer clone() {
        return new EntityPlayerCheckableRenderer(entity, getRendered());
    }
}
