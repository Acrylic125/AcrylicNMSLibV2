package com.acrylic.universalnms.enums;

/**
 * Developers note: This was originally adapted from
 * https://github.com/CitizensDev/Citizens2/blob/87cadbfb023d81547e2198a792a3ee629d16ad8f/v1_8_R3/src/main/java/net/citizensnpcs/nms/v1_8_R3/util/PlayerAnimationImpl.java#L106
 * (Citizens)
 *
 * This enum has been compressed down and adapted for
 * this use.
 *
 * As of now, there is no support for ARM_SWING_OFFHAND,
 * START_ELYTRA, and STOP_ELYTRA animations as there is no
 * 1.9+ support.
 *
 * @see com.acrylic.universal.packets.EntityAnimationPackets
 */
public enum EntityAnimationEnum {

    ARM_SWING,
    ARM_SWING_OFFHAND,
    CRIT,
    HURT,
    MAGIC_CRIT,
    SLEEP,
    SNEAK,
    START_ELYTRA,
    STOP_ELYTRA,
    STOP_SLEEPING,
    STOP_SNEAKING

}
