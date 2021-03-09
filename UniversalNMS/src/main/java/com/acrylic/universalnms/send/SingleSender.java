package com.acrylic.universalnms.send;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class SingleSender implements BatchableSender {

    private final Consumer<Player> primaryAction;
    private Consumer<Player> attachedAction;
    private Predicate<Player> condition;

    public static Builder builder(Consumer<Player> action) {
        return new Builder(new SingleSender(action));
    }

    public static class Builder {

        private final SingleSender singleSender;

        public Builder(@NotNull SingleSender singleSender) {
            this.singleSender = singleSender;
        }

        public Builder attachAction(@Nullable Consumer<Player> action) {
            this.singleSender.setAttachedAction(action);
            return this;
        }

        public Builder condition(@Nullable Predicate<Player> condition) {
            this.singleSender.setCondition(condition);
            return this;
        }

        public SingleSender build() {
            return singleSender;
        }

    }

    public SingleSender(@NotNull Consumer<Player> primaryAction) {
        this.primaryAction = primaryAction;
    }

    @NotNull
    public Consumer<Player> getPrimaryAction() {
        return primaryAction;
    }

    @Nullable
    public Consumer<Player> getAttachedAction() {
        return attachedAction;
    }

    public void setAttachedAction(@Nullable Consumer<Player> attachedAction) {
        this.attachedAction = attachedAction;
    }

    @Nullable
    public Predicate<Player> getCondition() {
        return condition;
    }

    public void setCondition(@Nullable Predicate<Player> condition) {
        this.condition = condition;
    }

    @Override
    public void sendTo(@NotNull Player sendTo) {
        if (condition != null && condition.test(sendTo)) {
            primaryAction.accept(sendTo);
            if (attachedAction != null)
                attachedAction.accept(sendTo);
        }
    }

    @Override
    public void onInitialBatchCall() { }

    @Override
    public void batchSend(Player player) {
        sendTo(player);
    }
}
