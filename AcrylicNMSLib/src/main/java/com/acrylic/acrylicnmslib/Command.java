package com.acrylic.acrylicnmslib;

import com.acrylic.universal.command.CommandBuilder;
import com.acrylic.universal.command.CommandExecuted;
import com.acrylic.universal.command.CommandUtils;
import com.acrylic.universal.entity.equipment.EntityEquipmentBuilderImpl;
import com.acrylic.universal.text.ChatUtils;
import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.entity.NMSPlayerInstance;
import com.acrylic.universalnms.entity.entityconfiguration.LivingEntityConfiguration;
import com.acrylic.universalnms.entityai.aiimpl.AggressiveAI;
import com.acrylic.universalnms.entityai.strategyimpl.GuardianTargetSelector;
import com.acrylic.universalnms.entityai.strategyimpl.PathfinderStrategyImpl;
import com.acrylic.universalnms.entityai.strategyimpl.PlayerRandomTargetSelector;
import com.acrylic.universalnms.json.JSON;
import com.acrylic.universalnms.json.JSONComponent;
import com.acrylic.universalnms.particles.ParticleBuilder;
import com.acrylic.universalnms.pathfinder.Pathfinder;
import com.acrylic.universalnms.pathfinder.PathfinderGenerator;
import com.acrylic.universalnms.renderer.EntityPlayerCheckableRenderer;
import com.acrylic.version_1_8.items.ItemBuilder;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
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

    public static CommandBuilder getTestCommand() {
        return CommandBuilder.create("test")
                .aliases("test2", "test3")
                .timer(true)
                .filter(CommandExecuted::isExecutedByPlayer)
                .handle(commandExecuted -> {
                    Player player = (Player) commandExecuted.getSender();
                    NMSPlayerInstance nmsPlayerInstance = NMSPlayerInstance.builder(player.getLocation(), "")
                            .skin(commandExecuted.getArg(0))
                            .equipment(new EntityEquipmentBuilderImpl().
                                    setItemInHand(ItemBuilder.of(Material.NETHERITE_SWORD)
                                            .enchant(Enchantment.DAMAGE_ALL, 1))
                                    .setHelmet(ItemBuilder.of(Material.NETHERITE_HELMET)
                                            .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4))
                                    .setChestplate(ItemBuilder.of(Material.NETHERITE_CHESTPLATE)
                                            .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4))
                                    .setLeggings(ItemBuilder.of(Material.NETHERITE_LEGGINGS)
                                            .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4))
                                    .setBoots(ItemBuilder.of(Material.NETHER_BRICK)
                                            .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)))
                            .buildEntityInstance();
                    nmsPlayerInstance.getPacketHandler().setRenderer(new EntityPlayerCheckableRenderer(nmsPlayerInstance.getBukkitEntity()));
                    AggressiveAI entityAI = new AggressiveAI(nmsPlayerInstance);
                    entityAI.setAttackCooldown(400);
                    //entityAI.setPathQuitterStrategy(new SimplePathQuitterStrategyImpl(entityAI));
                    entityAI.setPathfinderStrategy(new PathfinderStrategyImpl(entityAI, PathfinderGenerator.A_STAR_PATHFINDER_GENERATOR));
                    entityAI.setTargetSelectorStrategy(new PlayerRandomTargetSelector(entityAI));
                    nmsPlayerInstance.setAI(entityAI);
                    nmsPlayerInstance.addToWorld();
                    nmsPlayerInstance.setEntityConfiguration(LivingEntityConfiguration.PERSISTENT_LIVING_ENTITY);
                    nmsPlayerInstance.register();

//                    Pathfinder pathfinder =  PathfinderGenerator.A_STAR_PATHFINDER_GENERATOR.generatePathfinder(player.getLocation(), player.getLocation().add(10, 0, 10));
//                    pathfinder.pathfind();
//                    pathfinder.generatePath(5).iterator().forEachRemaining(location -> {
//                        if (location == null)
//                            return;
//                     //   player.sendBlockChange(location, Bukkit.createBlockData(Material.REDSTONE_BLOCK));
//                        ParticleBuilder.builder(EnumWrappers.Particle.FLAME).amount(1).speed(0)
//                                .location(location.add(0, 1, 0))
//                                .build().getSender().sendTo(player);
//                    });
//                    Location location = player.getLocation();
//                    PathTypeResultByHeightImpl pathTypeResultByHeight = new PathTypeResultByHeightImpl(2, 2, 2, location.getWorld(),
//                            (float) location.getX(), (float) location.getY(), (float) location.getZ());
//                    pathTypeResultByHeight.examineWith(new PathExaminerByHeightImpl());
//                    Bukkit.broadcastMessage(pathTypeResultByHeight.getPathType() + " " + pathTypeResultByHeight.getResultY());
                });
    }


}
