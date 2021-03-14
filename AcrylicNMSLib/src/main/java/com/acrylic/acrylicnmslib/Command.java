package com.acrylic.acrylicnmslib;

import com.acrylic.universal.animations.rotational.HandRotationAnimation;
import com.acrylic.universal.command.AbstractCommandBuilder;
import com.acrylic.universal.command.AbstractCommandExecuted;
import com.acrylic.universal.command.CommandBuilder;
import com.acrylic.universal.entity.ArmorStandInstance;
import com.acrylic.universal.entity.equipment.EntityEquipmentBuilderImpl;
import com.acrylic.universal.text.ChatUtils;
import com.acrylic.universal.threads.Scheduler;
import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.entity.NMSArmorStandInstance;
import com.acrylic.universalnms.packets.types.BlockCrackPacket;
import com.acrylic.universalnms.packets.types.SoundPacket;
import com.acrylic.universalnms.renderer.EntityPlayerCheckableRenderer;
import com.acrylic.version_1_16_nms.entity.NMSArmorStandInstanceImpl;
import com.acrylic.version_1_8.items.ItemBuilder;
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
                }).arguments(new AbstractCommandBuilder[] {
                com.acrylic.version_1_8_nms.Command.getCommand(),
                com.acrylic.version_1_16_nms.Command.getCommand(),
                getTestCommand()
        }).register(NMSLib.getPlugin());
    }

    public static AbstractCommandBuilder getTestCommand() {
        return CommandBuilder.create("test")
                .filter(AbstractCommandExecuted::isPlayer)
                .handle(commandExecuted -> {
                    Player player = (Player) commandExecuted.getSender();
                    Bukkit.broadcastMessage(NMSLib.getFactory().getNMSUtilsFactory().getNewBoundingBoxExaminer(player) + "");
                    Bukkit.broadcastMessage(NMSLib.getFactory().getNMSUtilsFactory().getNewBoundingBoxExaminer(player.getLocation()) + "");
                });
    }


}
