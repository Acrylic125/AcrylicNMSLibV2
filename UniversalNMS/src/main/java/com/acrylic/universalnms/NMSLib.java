package com.acrylic.universalnms;

import com.acrylic.universal.Universal;
import com.acrylic.universalnms.factory.NMSAbstractFactory;
import com.acrylic.universalnms.nmsentityregistry.AbstractNMSEntityRegistry;
import com.acrylic.universalnms.plugin.AcrylicNMSPlugin;
import org.jetbrains.annotations.NotNull;

public class NMSLib implements AcrylicNMSPlugin {

    private static NMSLib acrylicNMSPlugin;

    private AbstractNMSEntityRegistry entityRegistry;
    private NMSAbstractFactory nmsAbstractFactory;

    public static void setAcrylicNMSPlugin(NMSLib acrylicNMSPlugin) {
        NMSLib.acrylicNMSPlugin = acrylicNMSPlugin;
    }

    public void setEntityRegistry(@NotNull AbstractNMSEntityRegistry entityRegistry) {
        this.entityRegistry = entityRegistry;
    }

    @NotNull
    @Override
    public AbstractNMSEntityRegistry getNMSEntityRegistry() {
        return entityRegistry;
    }

    public void setNMSAbstractFactory(@NotNull NMSAbstractFactory nmsAbstractFactory) {
        this.nmsAbstractFactory = nmsAbstractFactory;
    }

    @NotNull
    public NMSAbstractFactory getNMSAbstractFactory() {
        return nmsAbstractFactory;
    }

    public static NMSAbstractFactory getFactory() {
        return acrylicNMSPlugin.getNMSAbstractFactory();
    }

    @NotNull
    public static NMSLib getNMSPlugin() {
        return acrylicNMSPlugin;
    }

}
