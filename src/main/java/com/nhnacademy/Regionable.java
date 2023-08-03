package com.nhnacademy;

import java.awt.Rectangle;

public interface Regionable {
    public enum Type {
        WALL,
        TARGET,
        WETLAND
    }

    public Rectangle getRegion();

    public Type getType();
}
