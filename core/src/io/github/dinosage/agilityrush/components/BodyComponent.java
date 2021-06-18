package io.github.dinosage.agilityrush.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class BodyComponent implements Component {
    public Body body = null;
    public String name = null;
    public Vector2 size = null;
}