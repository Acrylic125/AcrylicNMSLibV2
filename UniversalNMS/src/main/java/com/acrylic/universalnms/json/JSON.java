package com.acrylic.universalnms.json;

import com.acrylic.universalnms.send.SingleSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * Example:
 *
 * new JSON().append(JSONComponent.of("&eHello LOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOL").item(p.getItemInHand()))
 *                 .append(JSONComponent.of(" LOL").suggestCommand("/kill ").subText("&7Click to kill."))
 *                 .append(JSONComponent.of(" Idiot"))
 *                 .append(JSONComponent.of(" &6&lMe! "))
 *                 .send(p);
 */
public final class JSON implements AbstractJSON {

    private final TextComponent textComponent;

    private JSON(TextComponent textComponent) {
        this.textComponent = (TextComponent) textComponent.duplicate();
    }

    public JSON(AbstractJSONComponent component) {
        textComponent = new TextComponent(component.getTextComponent());
    }

    public JSON() {
        textComponent = new TextComponent("");
    }

    @Override
    public AbstractJSON append(AbstractJSONComponent component) {
        textComponent.addExtra(component.getTextComponent());
        return this;
    }

    @Override
    public String toJson() {
        return toString();
    }

    @Override
    public AbstractJSON duplicate() {
        return new JSON(textComponent);
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
    public SingleSender send() {
        return SingleSender.builder(player -> player.spigot().sendMessage(textComponent)).build();
    }
}
