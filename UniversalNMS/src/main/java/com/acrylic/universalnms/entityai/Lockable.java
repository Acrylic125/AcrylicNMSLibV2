package com.acrylic.universalnms.entityai;

/**
 * This will be moved to AML.
 */
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
