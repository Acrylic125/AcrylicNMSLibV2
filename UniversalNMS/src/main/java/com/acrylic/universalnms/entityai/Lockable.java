package com.acrylic.universalnms.entityai;

public interface Lockable {

    boolean isLocked();

    void setLocked(boolean locked);

    default void lockStrategy() {
        setLocked(true);
    }

    default void unlockStrategy() {
        setLocked(false);
    }

}
