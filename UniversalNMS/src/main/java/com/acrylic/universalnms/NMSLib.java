package com.acrylic.universalnms;

import com.acrylic.universalnms.factory.NMSAbstractFactory;
import com.acrylic.universalnms.factory.NMSEntityFactory;
import com.acrylic.universalnms.factory.NMSUtilityFactory;
import com.acrylic.universalnms.factory.PacketFactory;
import com.acrylic.universalnms.nmsentityregistry.AbstractNMSEntityRegistry;
import com.acrylic.universalnms.plugin.AcrylicNMSPlugin;
import com.acrylic.universalnms.worldexaminer.BlockAnalyzer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class NMSLib implements AcrylicNMSPlugin {

    private static NMSLib acrylicNMSPlugin;
    private static JavaPlugin plugin;

    private AbstractNMSEntityRegistry entityRegistry;
    private NMSAbstractFactory nmsAbstractFactory;
    private BlockAnalyzer blockAnalyzer;

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

    public void setBlockAnalyzer(@NotNull BlockAnalyzer blockAnalyzer) {
        this.blockAnalyzer = blockAnalyzer;
    }

    @NotNull
    public BlockAnalyzer getNMSLibBlockAnalyzer() {
        return blockAnalyzer;
    }

    public static NMSAbstractFactory getFactory() {
        return acrylicNMSPlugin.getNMSAbstractFactory();
    }

    @NotNull
    public static NMSLib getNMSPlugin() {
        return acrylicNMSPlugin;
    }

    public static void setPlugin(@NotNull JavaPlugin plugin) {
        NMSLib.plugin = plugin;
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    //Static helper methods to cut down on code length
    public static NMSUtilityFactory getNMSUtilityFactory() {
        return getFactory().getNMSUtilsFactory();
    }

    public static PacketFactory getPacketFactory() {
        return getFactory().getPacketFactory();
    }

    public static NMSEntityFactory getEntityFactory() {
        return getFactory().getNMSEntityFactory();
    }

    public static BlockAnalyzer getBlockAnalyzer() {
        return acrylicNMSPlugin.getNMSLibBlockAnalyzer();
    }

}
