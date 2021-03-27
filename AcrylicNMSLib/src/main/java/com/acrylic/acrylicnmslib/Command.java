package com.acrylic.acrylicnmslib;

import com.acrylic.universal.command.CommandBuilder;
import com.acrylic.universal.command.CommandExecuted;
import com.acrylic.universal.text.ChatUtils;
import com.acrylic.universal.threads.Scheduler;
import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.particles.ParticleBuilder;
import com.acrylic.universalnms.pathfinder.Pathfinder;
import com.acrylic.universalnms.pathfinder.PathfinderGenerator;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command {

    public static void createCommand() {
        CommandBuilder.create("acrylicnms")
                .permissions("acrylicnms.admin")
                .handle(commandExecuted -> {
                    CommandSender sender = commandExecuted.getSender();
                    ChatUtils.send(sender,
                            "&e&lAcrylic NMS Lib",
                            "&e/acrylicnms <version>"
                    );
                }).arguments(
                com.acrylic.version_1_8_nms.Command.getCommand(),
                com.acrylic.version_1_16_nms.Command.getCommand(),
                getTestCommand()
        ).register(NMSLib.getPlugin());
    }

    public static int counter = 0;

    public static CommandBuilder getTestCommand() {
        return CommandBuilder.create("test")
                .aliases("test2", "test3")
                .timer(true)
                .filter(CommandExecuted::isExecutedByPlayer)
                .handle(commandExecuted -> {
                    Player player = (Player) commandExecuted.getSender();

                    Pathfinder pathfinder =  PathfinderGenerator.A_STAR_PATHFINDER_GENERATOR.generatePathfinder(player.getLocation(), player.getLocation().add(10, 0, 10));
                    pathfinder.pathfind();
                    pathfinder.generatePath(1).iterator().forEachRemaining(location -> {
                        if (location == null)
                            return;
                        player.sendBlockChange(location, Bukkit.createBlockData(Material.REDSTONE_BLOCK));
                        ParticleBuilder.builder(EnumWrappers.Particle.FLAME).amount(1).speed(0)
                                .location(location)
                                .build().getSender().sendTo(player);
                    });
//                    Location location = player.getLocation();
//                    PathTypeResultByHeightImpl pathTypeResultByHeight = new PathTypeResultByHeightImpl(2, 2, 2, location.getWorld(),
//                            (float) location.getX(), (float) location.getY(), (float) location.getZ());
//                    pathTypeResultByHeight.examineWith(new PathExaminerByHeightImpl());
//                    Bukkit.broadcastMessage(pathTypeResultByHeight.getPathType() + " " + pathTypeResultByHeight.getResultY());
                });
    }


}
