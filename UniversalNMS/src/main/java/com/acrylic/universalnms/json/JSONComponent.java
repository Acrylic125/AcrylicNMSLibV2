package com.acrylic.universalnms.json;

import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.nbt.NBTItem;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface JSONComponent {

    static JSONComponent of(@NotNull String text) {
        return NMSLib.getNMSUtilityFactory().getNewJSONComponent(text);
    }

    JSONComponent subText(String... text);

    JSONComponent command(String text);

    JSONComponent suggestCommand(String text);

    JSONComponent item(ItemStack item);

    JSONComponent item(NBTItem nbtItem);

    JSONComponent link(String text);

    TextComponent getTextComponent();

}
