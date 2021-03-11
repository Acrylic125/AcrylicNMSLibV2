package com.acrylic.universalnms.entity.wrapper;

import com.acrylic.universalnms.entity.NMSLivingEntityInstance;
import org.jetbrains.annotations.NotNull;

public interface NMSLivingEntityWrapper extends NMSEntityWrapper {

    @NotNull
    @Override
    NMSLivingEntityInstance getEntityInstance();

}
