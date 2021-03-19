package com.acrylic.acrylicnmslib;

import com.acrylic.universal.Universal;
import com.acrylic.universal.animations.rotational.HandRotationAnimation;
import com.acrylic.universal.command.CommandBuilder;
import com.acrylic.universal.command.CommandExecuted;
import com.acrylic.universal.entity.ArmorStandInstance;
import com.acrylic.universal.entity.equipment.EntityEquipmentBuilderImpl;
import com.acrylic.universal.text.ChatUtils;
import com.acrylic.universal.threads.Scheduler;
import com.acrylic.universal.utils.LocationConverter;
import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.entity.NMSArmorStandInstance;
import com.acrylic.universalnms.packets.types.BlockCrackPacket;
import com.acrylic.universalnms.packets.types.SoundPacket;
import com.acrylic.universalnms.pathfinder.Pathfinder;
import com.acrylic.universalnms.pathfinder.PathfinderGenerator;
import com.acrylic.universalnms.renderer.EntityPlayerCheckableRenderer;
import com.acrylic.version_1_16_nms.entity.NMSArmorStandInstanceImpl;
import com.acrylic.version_1_8.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;

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

    public static CommandBuilder getTestCommand() {
        return CommandBuilder.create("test")
                .aliases("test2", "test3")
                .filter(CommandExecuted::isExecutedByPlayer)
                .handle(commandExecuted -> {
                    Player player = (Player) commandExecuted.getSender();
                    /**Pathfinder pathfinder =  PathfinderGenerator.JPS_PATHFINDER_GENERATOR.generatePathfinder(player.getLocation(), player.getLocation().add(10, 0, 10));
                    pathfinder.pathfind();
                    pathfinder.generatePath().createTraversal().forEachRemaining(location -> {
                        if (location == null)
                            return;
                        player.sendBlockChange(location, Bukkit.createBlockData(Material.RED_STAINED_GLASS));
                    });**/
                    Location location = player.getLocation();
                    Bukkit.broadcastMessage(" " + NMSLib.getNMSUtilityFactory().getNewBoundingBoxExaminer(location.getWorld(), location.getX(), location.getY(), location.getZ()));
                });
    }


}
