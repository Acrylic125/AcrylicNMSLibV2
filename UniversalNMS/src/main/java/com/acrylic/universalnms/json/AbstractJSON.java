package com.acrylic.universalnms.json;

import com.acrylic.universalnms.send.Sendable;

public interface AbstractJSON extends Sendable, Cloneable {

    AbstractJSON append(AbstractJSONComponent component);

    String toJson();

    AbstractJSON duplicate();

}
