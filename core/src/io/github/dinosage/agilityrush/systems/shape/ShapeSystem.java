package io.github.dinosage.agilityrush.systems.shape;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import io.github.dinosage.agilityrush.GameScreen;
import io.github.dinosage.agilityrush.app.AppStorage;
import io.github.dinosage.agilityrush.components.BodyComponent;
import io.github.dinosage.agilityrush.components.Mappers;
import io.github.dinosage.agilityrush.components.ObstacleComponent;
import io.github.dinosage.agilityrush.components.ShapeComponent;
import io.github.dinosage.agilityrush.util.ShapeType;

public class ShapeSystem extends EntitySystem {

    //All Variables

    //Commands
    ShapeInitiateCommand initiate = new ShapeInitiateCommand();

    //Game + Misc
    Engine engine;
    GameScreen screen;
    ShapeRenderer renderer;
    OrthographicCamera camera;
    AppStorage app = AppStorage.getInstance();

    ComponentMapper<ShapeComponent> sm = Mappers.ShapeCom;
    ComponentMapper<BodyComponent> bm = Mappers.BodyCom;
    ComponentMapper<ObstacleComponent> om = Mappers.ObsCom;

    public ShapeSystem(GameScreen screen, int priority){
        super(priority);
        this.screen = screen;
    }

    public void addedToEngine(Engine engine){
        this.engine = engine;
        initiate.set(this).execute();
    }

    public void removedFromEngine(Engine engine){
        //Reset Variables
        renderer = null;
        camera = null;
    }

    public void update(float deltaTime){
        renderer.setProjectionMatrix(camera.combined);
        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(Family.all(ShapeComponent.class, BodyComponent.class).get());

        for(Entity entity : entities){
            if(om.get(entity) != null){
                sm.get(entity).color = Color.WHITE;
                sm.get(entity).shape = ShapeType.RECTANGLE;
            }
            if(sm.get(entity).shape == ShapeType.RECTANGLE){
                //Get Body Position
                BodyComponent bodyCom = bm.get(entity);
                renderer.setColor(sm.get(entity).color);

                //Setup Render Values
                Vector2 origin = bodyCom.body.getWorldCenter();
                Vector2 size = bodyCom.size;

                //Render
                if(!app.GAMEOVER){
                    renderer.begin(ShapeRenderer.ShapeType.Filled);
                    renderer.rect(origin.x - size.x/2, origin.y - size.y/2, origin.x, origin.y, size.x, size.y, 1, 1, (float) Math.toDegrees(bodyCom.body.getAngle()));
                    renderer.end();
                }
            }
        }
    }

}
