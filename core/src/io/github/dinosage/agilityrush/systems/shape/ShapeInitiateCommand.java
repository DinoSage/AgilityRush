package io.github.dinosage.agilityrush.systems.shape;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.dinosage.agilityrush.components.BodyComponent;
import io.github.dinosage.agilityrush.components.Mappers;
import io.github.dinosage.agilityrush.components.ShapeComponent;
import io.github.dinosage.agilityrush.util.Command;
import io.github.dinosage.agilityrush.util.ShapeType;


public class ShapeInitiateCommand implements Command {

    //All Variables
    Engine engine;
    ShapeSystem system;

    ComponentMapper<ShapeComponent> sm = Mappers.ShapeCom;

    public ShapeInitiateCommand set(ShapeSystem system){
        engine = system.engine;
        this.system = system;

        return this;
    }

    @Override
    public void execute() {
        system.renderer = new ShapeRenderer();
        system.camera = system.screen.getPhysics().getCamera();

        ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.all(ShapeComponent.class, BodyComponent.class).get());
        for(Entity entity : entities){
            //Setup Their Shape Component
            ShapeComponent shapeCom = sm.get(entity);
            shapeCom.color = Color.WHITE;
            shapeCom.shape = ShapeType.RECTANGLE;
        }
    }

}
