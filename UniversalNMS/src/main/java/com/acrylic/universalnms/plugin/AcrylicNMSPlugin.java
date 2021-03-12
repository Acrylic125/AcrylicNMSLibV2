package com.acrylic.universalnms.plugin;

import com.acrylic.universalnms.factory.NMSAbstractFactory;
import com.acrylic.universalnms.nmsentityregistry.AbstractNMSEntityRegistry;
import org.jetbrains.annotations.NotNull;

public interface AcrylicNMSPlugin {

    @NotNull
    AbstractNMSEntityRegistry getNMSEntityRegistry();

    @NotNull
    NMSAbstractFactory getNMSAbstractFactory();

}
