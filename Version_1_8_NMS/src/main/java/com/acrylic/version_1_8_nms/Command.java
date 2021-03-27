package com.acrylic.version_1_8_nms;

import com.acrylic.universal.animations.rotational.HandRotationAnimation;
import com.acrylic.universal.command.CommandBuilder;
import com.acrylic.universal.command.CommandExecuted;
import com.acrylic.universal.text.ChatUtils;
import com.acrylic.universal.threads.Scheduler;
import com.acrylic.universalnms.entity.NMSEntityInstance;
import com.acrylic.universalnms.renderer.EntityPlayerCheckableRenderer;
import com.acrylic.version_1_8.equipment.EntityEquipmentBuilderImpl;
import com.acrylic.version_1_8.items.ItemBuilder;
import com.acrylic.version_1_8_nms.entity.NMSGiantInstanceImpl;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Command {

    public static JavaPlugin plugin;

    public static CommandBuilder getCommand() {
        return CommandBuilder.create("1.8")
                .handle(commandExecuted -> {
                    ChatUtils.send(commandExecuted.getSender(),
                            "&6&lAcrylic NMS Lib (1.8)",
                            "&e/acrylicnms 1.8 test"
                            );
                }).arguments(
                        getTestArgument()
                );
    }

    public static CommandBuilder getTestArgument() {
        return CommandBuilder.create("test")
                .filter(CommandExecuted::isExecutedByPlayer)
                .handle(commandExecuted -> {
                    Player player = (Player) commandExecuted.getSender();
                    NMSGiantInstanceImpl armorStandInstance = new NMSGiantInstanceImpl(player.getLocation(), null);
                    armorStandInstance.getPacketHandler().setRenderer(new EntityPlayerCheckableRenderer(armorStandInstance.getBukkitEntity()));
                    armorStandInstance.asAnimator();
                    armorStandInstance.upsideDown();
                    armorStandInstance.setEquipment(new EntityEquipmentBuilderImpl().
                            setItemInHand(ItemBuilder.of(Material.ENDER_CHEST))
                    );
                    Location location = player.getLocation();
                    HandRotationAnimation handRotationAnimation = new HandRotationAnimation(armorStandInstance);
                    Scheduler.sync().runRepeatingTask(1, 1)
                            .plugin(plugin)
                            .handleThenBuild(() -> {
                                armorStandInstance.tick(NMSEntityInstance.TickSource.CUSTOM);
                                handRotationAnimation.teleportWithHolograms(location);
                            });
                }).arguments(
                        CommandBuilder.create("p")
                                .filter(CommandExecuted::isExecutedByPlayer)
                                .timer(true)
                                .handle(commandExecuted -> {
                                    Player player = (Player) commandExecuted.getSender();

                        })
                );
    }

}
