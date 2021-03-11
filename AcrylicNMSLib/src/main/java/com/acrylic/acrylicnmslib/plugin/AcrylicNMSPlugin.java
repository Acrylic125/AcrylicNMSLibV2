package com.acrylic.acrylicnmslib.plugin;

import com.acrylic.universalnms.nmsentityregistry.AbstractNMSEntityRegistry;
import org.jetbrains.annotations.NotNull;

public interface AcrylicNMSPlugin {

    @NotNull
    AbstractNMSEntityRegistry getNMSEntityRegistry();

}
