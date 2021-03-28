package com.acrylic.version_1_16_nms;

import com.acrylic.universal.command.CommandBuilder;
import com.acrylic.universal.command.CommandExecuted;
import com.acrylic.universal.entity.equipment.EntityEquipmentBuilderImpl;
import com.acrylic.universal.text.ChatUtils;
import com.acrylic.universalnms.entity.entityconfiguration.LivingEntityConfiguration;
import com.acrylic.universalnms.entityai.aiimpl.AggressiveAI;
import com.acrylic.universalnms.entityai.aiimpl.TargettableAIImpl;
import com.acrylic.universalnms.entityai.strategyimpl.PathfinderStrategyImpl;
import com.acrylic.universalnms.entityai.strategyimpl.PlayerRandomTargetSelector;
import com.acrylic.universalnms.enums.EntityAnimationEnum;
import com.acrylic.universalnms.enums.Gamemode;
import com.acrylic.universalnms.particles.ParticleBuilder;
import com.acrylic.universalnms.pathfinder.PathfinderGenerator;
import com.acrylic.universalnms.renderer.EntityPlayerCheckableRenderer;
import com.acrylic.version_1_16_nms.entity.NMSPlayerInstanceImpl;
import com.acrylic.version_1_8.items.ItemBuilder;
import net.minecraft.server.v1_16_R3.DamageSource;
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
                    NMSPlayerInstanceImpl armorStandInstance = new NMSPlayerInstanceImpl(player.getLocation(), null, "Trump");
                    armorStandInstance.getPacketHandler().setRenderer(new EntityPlayerCheckableRenderer(armorStandInstance.getBukkitEntity()));
                    armorStandInstance.setAnimations(EntityAnimationEnum.SNEAK);
                    //armorStandInstance.asAnimator();
                    //armorStandInstance.setAnimations(EntityAnimationEnum.HURT, EntityAnimationEnum.SLEEP, EntityAnimationEnum.CRIT);
                    AggressiveAI entityAI = new AggressiveAI(armorStandInstance);
                    entityAI.setPathfinderStrategy(new PathfinderStrategyImpl(entityAI, PathfinderGenerator.A_STAR_PATHFINDER_GENERATOR));
                    //entityAI.setTarget(player);
                    entityAI.setAttackCooldown(2000);
                    entityAI.setTargetSelectorStrategy(new PlayerRandomTargetSelector(entityAI));
                    armorStandInstance.setAI(entityAI);
                    armorStandInstance.setSkin("Acrylic123");
                    armorStandInstance.setEquipment(new EntityEquipmentBuilderImpl().
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
                    armorStandInstance.addToWorld();
                    armorStandInstance.setEntityConfiguration(LivingEntityConfiguration.PERSISTENT_LIVING_ENTITY);
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
