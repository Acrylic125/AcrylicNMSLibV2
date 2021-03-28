package com.acrylic.universalnms.json;

import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.send.Sendable;
import org.jetbrains.annotations.NotNull;

public interface JSON extends Sendable, Cloneable {

    static JSON create() {
        return NMSLib.getNMSUtilityFactory().getNewJSON();
    }

    static JSON create(@NotNull JSONComponent jsonComponent) {
        return NMSLib.getNMSUtilityFactory().getNewJSON(jsonComponent);
    }

    JSON append(JSONComponent component);

    String toJson();

    JSON duplicate();

}
