package com.acrylic.version_1_8_nms.json;

import com.acrylic.universal.items.ItemUtils;
import com.acrylic.universal.text.ChatUtils;
import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.json.JSONComponent;
import com.acrylic.universalnms.nbt.NBTCompound;
import com.acrylic.universalnms.nbt.NBTItem;
import net.md_5.bungee.api.chat.*;
import org.bukkit.inventory.ItemStack;

public final class JSONComponentImpl implements JSONComponent {

    private final TextComponent textComponent;

    public JSONComponentImpl(String text) {
        textComponent = new TextComponent(TextComponent.fromLegacyText((ChatUtils.get(text))));
    }

    @Override
    public JSONComponent subText(String... text) {
        final ComponentBuilder componentBuilder = new ComponentBuilder(ChatUtils.get(text[0]));
        int i = 0;
        if (text.length > 1) {
            for (String s : text) {
                i++;
                if (i <= 1)
                    continue;
                componentBuilder.append("\n" + ChatUtils.get(s));
            }
        }
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, componentBuilder.create()));
        return this;
    }

    @Override
    public JSONComponent command(String text) {
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,text));
        return this;
    }

    @Override
    public JSONComponent suggestCommand(String text) {
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,text));
        return this;
    }

    @Override
    public JSONComponent item(ItemStack item) {
        return item(NMSLib.getNMSUtilityFactory().getNewNBTItem(item));
    }

    @Override
    public JSONComponent item(NBTItem nbtItem) {
        if (ItemUtils.isAir(nbtItem.getOriginalItem()))
            return this;
        NBTCompound NBTCompound = nbtItem.getCompound();
        HoverEvent event = new HoverEvent(HoverEvent.Action.SHOW_ITEM, new BaseComponent[]{new TextComponent(NBTCompound.getCompoundString())});
        textComponent.setHoverEvent(event);
        return this;
    }

    @Override
    public JSONComponent link(String link) {
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,link));
        return this;
    }

    @Override
    public TextComponent getTextComponent() {
        return textComponent;
    }
}
