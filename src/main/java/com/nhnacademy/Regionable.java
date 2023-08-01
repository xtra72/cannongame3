package com.nhnacademy;

import java.awt.Rectangle;

public interface Regionable {
    public enum Type {
        TARGET,
        WETLAND,
        WALL
    }

    public Rectangle getRegion();

    Type getType();

    void setType(Type type);
}
