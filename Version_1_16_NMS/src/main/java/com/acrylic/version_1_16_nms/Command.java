package com.acrylic.version_1_16_nms;

import com.acrylic.universal.command.CommandBuilder;
import com.acrylic.universal.command.CommandExecuted;
import com.acrylic.universal.entity.equipment.EntityEquipmentBuilderImpl;
import com.acrylic.universal.text.ChatUtils;
import com.acrylic.universalnms.entity.entityconfiguration.LivingEntityConfiguration;
import com.acrylic.universalnms.entityai.aiimpl.AggressiveAI;
import com.acrylic.universalnms.entityai.strategyimpl.*;
import com.acrylic.universalnms.pathfinder.PathfinderGenerator;
import com.acrylic.universalnms.renderer.RangedEntityRenderer;
import com.acrylic.version_1_16_nms.entity.NMSPlayerInstanceImpl;
import com.acrylic.version_1_8.items.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
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
                    NMSPlayerInstanceImpl npc = new NMSPlayerInstanceImpl(player.getLocation(), null, "Trump");
                    npc.getPacketHandler().setRenderer(new RangedEntityRenderer(npc.getBukkitEntity()));
                    //npc.asAnimator();
                    //npc.setAnimations(EntityAnimationEnum.HURT, EntityAnimationEnum.SLEEP, EntityAnimationEnum.CRIT);
                    AggressiveAI entityAI = new AggressiveAI(npc);
                    entityAI.setPathfinderStrategy(new PathfinderStrategyImpl(entityAI, PathfinderGenerator.A_STAR_PATHFINDER_GENERATOR));
                    //entityAI.setTarget(player);
                    //entityAI.setPathQuitterStrategy(new SimplePathQuitterStrategyImpl(entityAI));
                    entityAI.setAttackCooldown(400);
                    entityAI.setTargetSelectorStrategy(new PlayerRandomTargetSelector(entityAI));
                    npc.setAI(entityAI);
                    npc.setSkin("Acrylic123");
                    npc.setEquipment(new EntityEquipmentBuilderImpl().
                            setItemInHand(ItemBuilder.of(Material.NETHERITE_SWORD)
                                    .enchant(Enchantment.DAMAGE_ALL, 1)
                                    .enchant(Enchantment.FIRE_ASPECT, 1)
                                    .enchant(Enchantment.KNOCKBACK, 3))
                            .setHelmet(ItemBuilder.of(Material.NETHERITE_HELMET)
                                    .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4))
                            .setChestplate(ItemBuilder.of(Material.NETHERITE_CHESTPLATE)
                                    .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4))
                            .setLeggings(ItemBuilder.of(Material.NETHERITE_LEGGINGS)
                                    .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4))
                            .setBoots(ItemBuilder.of(Material.NETHER_BRICK)
                                    .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4))
                    );
                    npc.addToWorld();
                    npc.setEntityConfiguration(LivingEntityConfiguration.PERSISTENT_LIVING_ENTITY);
                    npc.register();

                }).arguments(
                        CommandBuilder.create("p")
                                .filter(CommandExecuted::isExecutedByPlayer)
                                .timer(true)
                                .handle(commandExecuted -> {
                                    Player player = (Player) commandExecuted.getSender();
                                    Location loc = player.getLocation();

                                })
                );
    }

}
