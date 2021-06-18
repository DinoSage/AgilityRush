package io.github.dinosage.agilityrush.game.systems.physics;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import io.github.dinosage.agilityrush.game.BodyFactory;
import io.github.dinosage.agilityrush.game.GameValues;
import io.github.dinosage.agilityrush.app.AppStorage;
import io.github.dinosage.agilityrush.game.components.BodyComponent;
import io.github.dinosage.agilityrush.game.components.Mappers;
import io.github.dinosage.agilityrush.game.components.ObstacleComponent;
import io.github.dinosage.agilityrush.game.components.ShapeComponent;
import io.github.dinosage.agilityrush.game.listeners.ContactReader;
import io.github.dinosage.agilityrush.util.Command;


public class ObstacleUpdateCommand implements Command {

    //All Variables
    AppStorage app = AppStorage.getInstance();
    World world;
    Engine engine;
    GameValues values;
    BodyFactory factory;
    ContactReader reader;

    ComponentMapper<BodyComponent> bm = Mappers.BodyCom;
    ComponentMapper<ObstacleComponent> om = Mappers.ObsCom;

    public ObstacleUpdateCommand set(PhysicsSystem system){
        world = system.world;
        engine = system.getEngine();
        values = system.values;
        factory = system.factory;
        reader = system.contactReader;
        return this;
    }

    @Override
    public void execute() {
        ImmutableArray<Entity> obstacles = engine.getEntitiesFor(Family.all(ObstacleComponent.class).get());

        //Update distance between last obstacle and spawn point
        if(obstacles.size() != 0) {
            Body obstacleBody = bm.get(values.lastObstacle).body;
            values.distance = app.SPAWNPOS.x - obstacleBody.getPosition().x;
        }

        //Generate obstacle when distance exceeds spawn distance
        if((values.distance >= app.TEMPDIST || obstacles.size() == 0) && !values.wait){
            app.generateSpawnDist();
            values.distance = 0;
            Vector2 size = new Vector2();

            Entity obstacle = new Entity().add(new BodyComponent()).add(new ShapeComponent()).add(new ObstacleComponent());
            BodyComponent obsBody = bm.get(obstacle);
            obsBody.name = "obstacle";

            //Generate Random Number for Obstacle Type
            int num = (int) (Math.random()*8); // 0 - 7

            if(num <= 2){
                size.x = (float) Math.random() * 2 + 2; // Range 3-5
                size.y = (float) Math.random() * 5 + 8; // Range 8-13
                obsBody.body = factory.squareBody(1, app.SPAWNPOS, size, null, 0, 1f, 0f, 0f, null);
            } else {
                size.x = (float) Math.random() * 2 + 2; // Range 3-5

                if(num >= 6){
                    size.y = -1 * app.GROUNDHEIGHT - 6;

                } else {
                    size.y = 50 - app.GROUNDHEIGHT - 6;
                }
                Vector2 pos = new Vector2();
                pos.x = app.SPAWNPOS.x;
                pos.y = app.GROUNDHEIGHT + 5 + size.y/2;

                obsBody.body = factory.squareBody(3, pos, size, null, 0, 1f, 0f, 0f, null);
            }

            obsBody.body.setUserData(obstacle);
            obsBody.size = factory.getLastFixtureSize();

            ObstacleComponent obsCom = om.get(obstacle);
            obsCom.scored = false;

            engine.addEntity(obstacle);
            values.lastObstacle = obstacle;
        }

        //Disposes obstacles that have fallen off
        if (reader.demolished != null) {
            engine.removeEntity(reader.demolished);
            world.destroyBody(bm.get(reader.demolished).body);
            reader.demolished = null;
        }

        //Updates remaining obstacles speed
        for (Entity entity : obstacles) {
            Body obsBody = bm.get(entity).body;
            obsBody.setLinearVelocity(app.OBSSPEEDX, obsBody.getLinearVelocity().y);
        }
    }
}
