package com.acrylic.universalnms.errors;

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
