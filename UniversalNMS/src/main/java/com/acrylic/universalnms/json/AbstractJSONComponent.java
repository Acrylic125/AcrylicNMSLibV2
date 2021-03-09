package com.acrylic.universalnms.json;

import com.acrylic.universalnms.nbt.AbstractNBTItem;
import net.md_5.bungee.api.chat.TextComponent;

public interface AbstractJSONComponent {

    AbstractJSONComponent subText(String... text);

    AbstractJSONComponent command(String text);

    AbstractJSONComponent suggestCommand(String text);

    AbstractJSONComponent item(AbstractNBTItem nbtItem);

    AbstractJSONComponent link(String text);

    TextComponent getTextComponent();

}
