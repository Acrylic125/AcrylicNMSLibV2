package com.acrylic.universalnms.send;

import com.acrylic.universalnms.util.Validation;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ValidatedSingleSender extends SingleSender {

    private Validation validation;
    private String attachedMessage;

    public static Builder validateBuilder(Consumer<Player> action) {
        return new Builder(new ValidatedSingleSender(action));
    }

    public static class Builder {

        private final ValidatedSingleSender singleSender;

        public Builder(@NotNull ValidatedSingleSender singleSender) {
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

        public Builder validation(@Nullable Validation validation) {
            this.singleSender.setValidation(validation);
            return this;
        }

        public Builder attachValidationMessage(@Nullable String validationMessage) {
            this.singleSender.setAttachedValidationMessage(validationMessage);
            return this;
        }

        public ValidatedSingleSender build() {
            return singleSender;
        }

    }

    public ValidatedSingleSender(@NotNull Consumer<Player> primaryAction) {
        super(primaryAction);
    }

    @Override
    public void sendTo(@NotNull Player sendTo) {
        if (validation != null && !validation.validate())
            throw new Validation.ValidationFailedException("The validation failed while trying to send to " + sendTo.getName() + "." + getAppendedAttachMessage());
        super.sendTo(sendTo);
    }

    @Override
    public void sendToAll(@NotNull Collection<? extends Player> sendTo) {
        if (validation != null && !validation.validate())
            throw new Validation.ValidationFailedException("The validation failed while trying to send to ALL " + sendTo + "." + getAppendedAttachMessage());
        super.sendToAll(sendTo);
    }

    @Override
    public void sendToAll(@NotNull Player... sendTo) {
        if (validation != null && !validation.validate())
            throw new Validation.ValidationFailedException("The validation failed while trying to send to ALL " + Arrays.toString(sendTo) + "." + getAppendedAttachMessage());
        super.sendToAll(sendTo);
    }

    @Override
    public void onInitialBatchCall() {
        if (validation != null && !validation.validate())
            throw new Validation.ValidationFailedException("The validation failed on batch call." + getAppendedAttachMessage());
        super.onInitialBatchCall();
    }

    /**
     * The validation is initially done via {@link #onInitialBatchCall()}.
     * Therefore, no validation will be done through this method.
     *
     * @param player The player to send to.
     */
    @Override
    public void batchSend(Player player) {
        super.batchSend(player);
    }

    public void setValidation(@Nullable Validation validation) {
        this.validation = validation;
    }

    public void setAttachedValidationMessage(@Nullable String attachedMessage) {
        this.attachedMessage = attachedMessage;
    }

    private String getAppendedAttachMessage() {
        return ((attachedMessage == null) ? "" : " " + attachedMessage);
    }

}
