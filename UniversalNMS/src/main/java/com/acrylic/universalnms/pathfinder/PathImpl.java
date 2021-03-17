package com.acrylic.universalnms.pathfinder;

import math.MathUtilsKt;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class PathImpl implements Path {

    private final Pathfinder pathfinder;
    private final Location[] points;

    public PathImpl(Pathfinder pathfinder, Location[] points) {
        this.pathfinder = pathfinder;
        this.points = points;
        for (Location point : points) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.sendBlockChange(point.add(0, 1, 0), Bukkit.createBlockData(Material.ICE));
            }
         }
    }

    public PathImpl(Pathfinder pathfinder, Collection<Location> points) {
        this(pathfinder, (Location[]) points.toArray());
    }

    @NotNull
    @Override
    public Location[] getLocations() {
        return points;
    }

    @NotNull
    @Override
    public PathTraversal createTraversal() {
        return new PathTraversalImpl(pathfinder.getWorld(), this);
    }

    @Override
    @Nullable
    public Location getLocation(int index) {
        return (index < 0 || index >= points.length) ? null : points[index];
    }
}
