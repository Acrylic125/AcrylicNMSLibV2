package com.acrylic.universalnms.json;

public interface AbstractJSON extends PlayerSendable, Cloneable {

    AbstractJSON append(AbstractJSONComponent component);

    String toJson();

    AbstractJSON duplicate();

}
