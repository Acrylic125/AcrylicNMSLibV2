package com.acrylic.universalnms.util;

import org.jetbrains.annotations.NotNull;

/**
 * The validation interface allows for a validation check.
 *
 * If the {@link #validate()} returns false, {@link ValidationFailedException}
 * will be thrown. Otherwise, the code will proceed.
 */
@FunctionalInterface
public interface Validation {

    boolean validate() throws ValidationFailedException;

    class ValidationFailedException extends RuntimeException {

        public ValidationFailedException() {}

        public ValidationFailedException(@NotNull String exception) {
            super(exception);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

}
