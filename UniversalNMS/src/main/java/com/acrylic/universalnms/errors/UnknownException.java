package com.acrylic.universalnms.errors;

/**
 * This exception is meant as a temporary solution
 * to report any bugs and unintended effects.
 */
@Deprecated
public class UnknownException extends RuntimeException {

    public UnknownException() {
        super();
    }

    public UnknownException(String error) {
        super(error);
    }

    @Override
    public String toString() {
        return super.toString() + "\n"
                + "You may report or make a possible resolution at https://github.com/Acrylic125/AcrylicNMSLibV2/issues";
    }
}
