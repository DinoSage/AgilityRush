package io.github.dinosage.agilityrush.game.systems.physics;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import io.github.dinosage.agilityrush.game.BodyFactory;
import io.github.dinosage.agilityrush.app.AppStorage;
import io.github.dinosage.agilityrush.game.components.AnimationComponent;
import io.github.dinosage.agilityrush.game.components.BodyComponent;
import io.github.dinosage.agilityrush.game.components.Mappers;
import io.github.dinosage.agilityrush.game.components.ShapeComponent;
import io.github.dinosage.agilityrush.game.listeners.ContactReader;
import io.github.dinosage.agilityrush.util.Command;

public class PhysicsInitiateCommand implements Command {

    //Variables
    World world;
    BodyFactory factory;
    OrthographicCamera camera;
    ContactReader reader;
    PhysicsSystem system;
    Engine engine;

    ComponentMapper<BodyComponent> bm = Mappers.BodyCom;

    public PhysicsInitiateCommand set(PhysicsSystem system){
        world = system.world;
        factory = system.factory;
        camera = system.camera;
        engine = system.getEngine();
        reader = system.contactReader;
        this.system = system;

        return this;
    }

    @Override
    public void execute(){
        //Creates World
        world = new World(new Vector2(0, -160), true);
        world.setContactListener(reader);

        //Updates factory's world instance
        factory.setWorld(world);

        //Creates Player & Add Components
        Entity player = new Entity();
        player.add(new BodyComponent()).add(new ShapeComponent()).add(new AnimationComponent());
        BodyComponent playerBodyCom = bm.get(player);
        playerBodyCom.body = factory.squareBody(1, new Vector2(-25f, 0), new Vector2(8, 8), null, 0, 0.015625f, 0f, 0f, null);
        playerBodyCom.name = "player";
        playerBodyCom.size = factory.getLastFixtureSize();
        system.player = player;
        engine.addEntity(player);

        //Set User Data to Entity for Entity Retrieval from ContactListener
        playerBodyCom.body.setUserData(player);

        //Create Platform
        Entity platform = new Entity().add(new BodyComponent()).add(new ShapeComponent());
        BodyComponent platformBody = bm.get(platform);
        platformBody.body = factory.squareBody(3, new Vector2(40, -20), new Vector2(200, 10), null, 0, 10, 0f, 0f, null);
        platformBody.body.setUserData(platform);
        platformBody.name = "platform";
        platformBody.size = factory.getLastFixtureSize();
        engine.addEntity(platform);

        AppStorage.getInstance().GROUNDHEIGHT = platformBody.body.getPosition().y + platformBody.size.y/2;

        //Update Camera Position
        camera.position.set(new Vector3(0, 0, camera.position.z));
        camera.update();

        //Update New Variables Back
        system.world = world;
    }
}
