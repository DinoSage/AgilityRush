package io.github.dinosage.agilityrush.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class SlideComponent implements Component {
    public Fixture current = null;

    public FixtureDef slidingDef = null;
    public Vector2 slidSize = null;

    public FixtureDef regularDef = null;
    public Vector2 regularSize = null;
}
