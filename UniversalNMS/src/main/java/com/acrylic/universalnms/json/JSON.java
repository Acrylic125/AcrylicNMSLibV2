package com.acrylic.universalnms.json;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

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
    public void sendAndDo(@Nullable Consumer<Player> action, @NotNull Player sendTo) {
        if (action != null)
            action.accept(sendTo);
        sendTo.spigot().sendMessage(textComponent);
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
}
