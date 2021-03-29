package com.acrylic.universalnms.enums;

public enum ChatMessageType {

    CHAT((byte) 0),
    SYSTEM((byte) 1),
    GAME_INFO((byte) 2);

    private final byte id;

    ChatMessageType(byte id) {
        this.id = id;
    }

    public byte getID() {
        return id;
    }
}
