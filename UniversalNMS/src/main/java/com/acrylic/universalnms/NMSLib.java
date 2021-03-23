package com.acrylic.universalnms;

import com.acrylic.universalnms.factory.NMSAbstractFactory;
import com.acrylic.universalnms.factory.NMSEntityFactory;
import com.acrylic.universalnms.factory.NMSUtilityFactory;
import com.acrylic.universalnms.factory.PacketFactory;
import com.acrylic.universalnms.nmsentityregistry.AbstractNMSEntityRegistry;
import com.acrylic.universalnms.skins.SkinMap;
import com.acrylic.universalnms.worldexaminer.BlockAnalyzer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class NMSLib {

    private static JavaPlugin plugin;
    private static NMSAbstractFactory nmsAbstractFactory;
    private static BlockAnalyzer blockAnalyzer;
    private static AbstractNMSEntityRegistry entityRegistry;
    private static SkinMap skinMap = new SkinMap();

    public void setEntityRegistry(@NotNull AbstractNMSEntityRegistry entityRegistry) {
        NMSLib.entityRegistry = entityRegistry;
    }

    public static AbstractNMSEntityRegistry getNMSEntityRegistry() {
        return entityRegistry;
    }

    public static void setNMSAbstractFactory(@NotNull NMSAbstractFactory nmsAbstractFactory) {
        NMSLib.nmsAbstractFactory = nmsAbstractFactory;
    }

    public static NMSAbstractFactory getNMSAbstractFactory() {
        return nmsAbstractFactory;
    }

    public static void setBlockAnalyzer(@NotNull BlockAnalyzer blockAnalyzer) {
        NMSLib.blockAnalyzer = blockAnalyzer;
    }

    public static NMSAbstractFactory getFactory() {
        return NMSLib.getNMSAbstractFactory();
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
        return blockAnalyzer;
    }

    public static SkinMap getSkinMap() {
        return skinMap;
    }

    public static void setSkinMap(@NotNull SkinMap skinMap) {
        NMSLib.skinMap = skinMap;
    }
}
