package com.acrylic.version_1_16_nms;

import com.acrylic.universal.command.CommandBuilder;
import com.acrylic.universal.command.CommandExecuted;
import com.acrylic.universal.entity.equipment.EntityEquipmentBuilderImpl;
import com.acrylic.universal.text.ChatUtils;
import com.acrylic.universal.threads.Scheduler;
import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.entity.NMSEntityInstance;
import com.acrylic.universalnms.entity.entityconfiguration.EntityConfiguration;
import com.acrylic.universalnms.entityai.impl.TargettableAIImpl;
import com.acrylic.universalnms.entityai.strategies.PathfinderStrategyImpl;
import com.acrylic.universalnms.particles.ParticleBuilder;
import com.acrylic.universalnms.pathfinder.PathfinderGenerator;
import com.acrylic.universalnms.renderer.EntityPlayerCheckableRenderer;
import com.acrylic.version_1_16_nms.entity.NMSPlayerInstanceImpl;
import com.acrylic.version_1_8.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Command {

    public static CommandBuilder getCommand() {
        return CommandBuilder.create("1.16")
                .handle(commandExecuted -> {
                    ChatUtils.send(commandExecuted.getSender(),
                            "&6&lAcrylic NMS Lib (1.16)",
                            "&e/acrylicnms 1.16 test"
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
                    NMSPlayerInstanceImpl armorStandInstance = new NMSPlayerInstanceImpl(player.getLocation(), null, "Trump");
                    armorStandInstance.getPacketHandler().setRenderer(new EntityPlayerCheckableRenderer(armorStandInstance.getBukkitEntity()));
                    //armorStandInstance.asAnimator();
                    TargettableAIImpl entityAI = new TargettableAIImpl(armorStandInstance);
                    entityAI.setPathfinderStrategy(new PathfinderStrategyImpl(entityAI, PathfinderGenerator.A_STAR_PATHFINDER_GENERATOR));
                    entityAI.setTarget(player);
                    armorStandInstance.setAI(entityAI);
                    armorStandInstance.setSkin("Acrylic123");
                    armorStandInstance.setEquipment(new EntityEquipmentBuilderImpl().
                            setItemInHand(ItemBuilder.of(Material.DIAMOND_PICKAXE))
                    );
                    armorStandInstance.addToWorld();
                    //armorStandInstance.setEntityConfiguration(EntityConfiguration.P);
                    armorStandInstance.register();

                }).arguments(
                        CommandBuilder.create("p")
                                .filter(CommandExecuted::isExecutedByPlayer)
                                .timer(true)
                                .handle(commandExecuted -> {
                                    Player player = (Player) commandExecuted.getSender();
                            ParticleBuilder.blockDustParticleBuilder()
                                    .location(player.getLocation())
                                    .speed(0.15f)
                                    .item(ItemBuilder.of(Material.GOLD_BLOCK).build())
                                    .offset(1, 1, 1)
                                    .amount(100)
                                    .build()
                                    .getSender()
                                    .sendTo(player);

                        })
                );
    }

}
