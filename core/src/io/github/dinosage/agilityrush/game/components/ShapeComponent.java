package io.github.dinosage.agilityrush.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import io.github.dinosage.agilityrush.util.ShapeType;

public class ShapeComponent implements Component {
    public Color color = new Color(1f, 1f, 1f, 1f);
    public ShapeType shape = null;
}
