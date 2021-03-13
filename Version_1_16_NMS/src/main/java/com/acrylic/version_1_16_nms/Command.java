package com.acrylic.version_1_16_nms;

import com.acrylic.universal.animations.rotational.HandRotationAnimation;
import com.acrylic.universal.command.AbstractCommandBuilder;
import com.acrylic.universal.command.AbstractCommandExecuted;
import com.acrylic.universal.command.CommandBuilder;
import com.acrylic.universal.text.ChatUtils;
import com.acrylic.universal.threads.Scheduler;
import com.acrylic.universalnms.renderer.EntityRendererPlayer;
import com.acrylic.version_1_8.equipment.EntityEquipmentBuilderImpl;
import com.acrylic.version_1_8.items.ItemBuilder;
import com.acrylic.version_1_16_nms.entity.NMSGiantInstanceImpl;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Command {

    public static JavaPlugin plugin;

    public static AbstractCommandBuilder getCommand() {
        return CommandBuilder.create("1.16")
                .handle(commandExecuted -> {
                    ChatUtils.send(commandExecuted.getSender(),
                            "&6&lAcrylic NMS Lib (1.16)",
                            "&e/acrylicnms 1.16 test"
                            );
                }).arguments(new AbstractCommandBuilder[] {
                        getTestArgument()
                });
    }

    public static AbstractCommandBuilder getTestArgument() {
        return CommandBuilder.create("test")
                .filter(AbstractCommandExecuted::isPlayer)
                .handle(commandExecuted -> {
                    Player player = (Player) commandExecuted.getSender();
                    NMSGiantInstanceImpl armorStandInstance = new NMSGiantInstanceImpl(player.getLocation(), null);
                    armorStandInstance.getPacketHandler().setRenderer(new EntityRendererPlayer(armorStandInstance.getBukkitEntity()));
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
                                armorStandInstance.tick();
                                handRotationAnimation.teleportWithHolograms(location);
                            });
                }).arguments(new AbstractCommandBuilder[] {
                        CommandBuilder.create("p")
                                .filter(AbstractCommandExecuted::isPlayer)
                                .setTimerActive(true)
                                .handle(commandExecuted -> {
                                    Player player = (Player) commandExecuted.getSender();

                        })
                });
    }

}