package com.acrylic.universalnms.entity.wrapper;

import com.acrylic.universalnms.entity.NMSEntityInstance;
import org.jetbrains.annotations.NotNull;

public interface NMSEntityWrapper {

    @NotNull
    NMSEntityInstance getEntityInstance();

}
