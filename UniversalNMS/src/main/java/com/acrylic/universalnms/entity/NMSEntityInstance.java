package com.acrylic.universalnms.entity;

import com.acrylic.universal.entity.EntityInstance;
import com.acrylic.universalnms.entityai.EntityAI;
import org.jetbrains.annotations.Nullable;

public interface NMSEntityInstance extends EntityInstance {

    Object getNMSEntity();

    int getTicksLived();

    int getFireTicks();

    void setFireTicks(int ticks);

    @Nullable
    EntityAI getAI();

    EntityDisplayer getDisplayer();

    boolean isNoClip();

    void setNoClip(boolean noClip);

    void addToWorld();

    void tick();

}
