package io.github.dinosage.agilityrush;

import com.badlogic.ashley.core.Entity;

public class GameValues {
    public boolean jump = true;
    public boolean down = false;
    public boolean start = false;
    public boolean wait = false;
    public boolean pause = false;
    public boolean slide = false;

    public float distance = 0;
    public float slideDistance = 0;
    public Entity lastObstacle = null;

}
