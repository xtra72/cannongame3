package com.nhnacademy;

import java.awt.Rectangle;

public interface Bounded {
    public Rectangle getBounds();

    public void setBounds(Rectangle bounds);

    public void addRegion(Regionable region);

    public void removeRegion(Regionable region);
}
