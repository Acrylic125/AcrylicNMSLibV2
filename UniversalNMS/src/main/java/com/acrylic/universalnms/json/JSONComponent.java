package com.acrylic.universalnms.json;

import com.acrylic.universal.items.ItemUtils;
import com.acrylic.universal.nbt.AbstractNBTCompound;
import com.acrylic.universal.nbt.AbstractNBTItem;
import com.acrylic.universal.text.ChatUtils;
import net.md_5.bungee.api.chat.*;

public final class JSONComponent implements AbstractJSONComponent {

    private final TextComponent textComponent;

    public static JSONComponent of(String text) {
        return new JSONComponent(text);
    }

    private JSONComponent(String text) {
        textComponent = new TextComponent(TextComponent.fromLegacyText((ChatUtils.get(text))));
    }

    @Override
    public AbstractJSONComponent subText(String... text) {
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
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,componentBuilder.create()));
        return this;
    }

    @Override
    public AbstractJSONComponent command(String text) {
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,text));
        return this;
    }

    @Override
    public AbstractJSONComponent suggestCommand(String text) {
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,text));
        return this;
    }

    @Override
    public AbstractJSONComponent item(AbstractNBTItem nbtItem) {
        if (ItemUtils.isAir(nbtItem.getOriginalItem()))
            return this;
        AbstractNBTCompound abstractNBTCompound = nbtItem.getCompound();
        assert abstractNBTCompound != null;
        HoverEvent event = new HoverEvent(HoverEvent.Action.SHOW_ITEM, new BaseComponent[]{new TextComponent(abstractNBTCompound.getCompoundString())});
        textComponent.setHoverEvent(event);
        return this;
    }

    @Override
    public AbstractJSONComponent link(String link) {
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,link));
        return this;
    }

    @Override
    public TextComponent getTextComponent() {
        return textComponent;
    }
}
