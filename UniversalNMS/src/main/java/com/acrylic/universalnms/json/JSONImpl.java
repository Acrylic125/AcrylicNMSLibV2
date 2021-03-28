package com.acrylic.universalnms.json;

import com.acrylic.universalnms.send.SingleSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;

public final class JSONImpl implements JSON {

    private final TextComponent textComponent;

    private JSONImpl(TextComponent textComponent) {
        this.textComponent = textComponent.duplicate();
    }

    public JSONImpl(JSONComponent component) {
        textComponent = new TextComponent(component.getTextComponent());
    }

    public JSONImpl() {
        textComponent = new TextComponent("");
    }

    @Override
    public JSON append(JSONComponent component) {
        textComponent.addExtra(component.getTextComponent());
        return this;
    }

    @Override
    public String toJson() {
        return toString();
    }

    @Override
    public JSON duplicate() {
        return new JSONImpl(textComponent);
    }

    @Override
    public String toString() {
        return ComponentSerializer.toString(textComponent);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public SingleSender getSender() {
        return SingleSender.builder(player -> player.spigot().sendMessage(textComponent)).build();
    }
}
