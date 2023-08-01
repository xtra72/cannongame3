package com.nhnacademy;

import java.awt.Rectangle;
import java.util.function.Function;

public interface Bounded {
    public Rectangle getBounds();

    public void setBounds(Rectangle bounds);

    public void setObstacle(Function<Movable, Motion> obstacles);
}
