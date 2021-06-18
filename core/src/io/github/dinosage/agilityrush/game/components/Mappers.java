package io.github.dinosage.agilityrush.game.components;

import com.badlogic.ashley.core.ComponentMapper;

public class Mappers {
    public static final ComponentMapper<BodyComponent> BodyCom = ComponentMapper.getFor(BodyComponent.class);
    public static final ComponentMapper<ShapeComponent> ShapeCom = ComponentMapper.getFor(ShapeComponent.class);
    public static final ComponentMapper<AnimationComponent> AnimCom = ComponentMapper.getFor(AnimationComponent.class);
    public static final ComponentMapper<ObstacleComponent> ObsCom = ComponentMapper.getFor(ObstacleComponent.class);
    public static final ComponentMapper<SlideComponent> SlideCom = ComponentMapper.getFor(SlideComponent.class);
}
