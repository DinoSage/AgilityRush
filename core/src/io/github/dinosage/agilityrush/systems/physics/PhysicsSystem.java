package io.github.dinosage.agilityrush.systems.physics;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import io.github.dinosage.agilityrush.BodyFactory;
import io.github.dinosage.agilityrush.GameScreen;
import io.github.dinosage.agilityrush.GameValues;
import io.github.dinosage.agilityrush.app.AppStorage;
import io.github.dinosage.agilityrush.components.BodyComponent;
import io.github.dinosage.agilityrush.components.Mappers;
import io.github.dinosage.agilityrush.components.ObstacleComponent;
import io.github.dinosage.agilityrush.listeners.ContactReader;

public class PhysicsSystem extends EntitySystem {

    //Constants
    ComponentMapper<BodyComponent> bm = Mappers.BodyCom;
    ComponentMapper<ObstacleComponent> om = Mappers.ObsCom;

    final float timeStep = 1 / 60f;
    final int VELOCITYITERATIONS = 8;
    final int POSITIONITERATIONS = 3;

    //Commands
    PhysicsInitiateCommand initiate = new PhysicsInitiateCommand();
    PhysicsResetCommand reset = new PhysicsResetCommand();
    ObstacleUpdateCommand obstacle = new ObstacleUpdateCommand();

    //App Variables
    AppStorage app = AppStorage.getInstance();
    GameScreen screen;
    ExtendViewport viewport;
    OrthographicCamera camera;
    BodyFactory factory;
    GameValues values;

    //Box2D Variables
    World world;
    Box2DDebugRenderer renderer;
    ContactReader contactReader;
    Entity player;

    //Called To Setup PhysicsSystem
    public PhysicsSystem(GameScreen screen, int priority){
        super(priority);
        this.screen = screen;
        values = screen.getValues();
        contactReader = new ContactReader(values);
        renderer = new Box2DDebugRenderer();
        factory = BodyFactory.getInstance();

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(100, 50, camera);
        viewport.setMaxWorldHeight(50f);
    }

    //Called to Setup System
    @Override
    public void addedToEngine(Engine engine){
        initiate.set(this).execute();
    }

    //Called to Reset System
    @Override
    public void removedFromEngine(Engine engine){
        reset.set(this).execute();
    }

    @Override
    public void update(float deltaTime) {
        if(!app.GAMEOVER && !values.pause){
            if (values.start) {
                obstacle.set(this).execute();
            }
            difficultyUpdate();
            scoreUpdate();

            //World Update
            world.step(timeStep, VELOCITYITERATIONS, POSITIONITERATIONS);
        }
        //Debug Render
        if (!app.GAMEOVER) {
            if (app.DEBUG.getValue()) {
                renderer.render(world, camera.combined);
            }
        }
    }

    //Update Methods

    //Checks Remaining As Well As Changes Difficulty
    private void difficultyUpdate(){

        //Updates remaining obstacles present
        int count = 0;
        ImmutableArray<Entity> obstacles = getEngine().getEntitiesFor(Family.all(ObstacleComponent.class).get());

        for(Entity entity : obstacles){
            ObstacleComponent obsCom = om.get(entity);
            if(!obsCom.scored){
                count++;
            }
        }
        ObstacleComponent.remaining = count;

        //Updates when obstacles remaining adds up to finishing the wave
        if((app.TEMPSCORE + ObstacleComponent.remaining) / app.OBSINCREM > 0 && !values.wait){
            values.wait = true;
        }

        //Wait for previous obstacles to finish before next wave, if wait is managed / set to true
        if(values.wait && ObstacleComponent.remaining == 0){
            app.TEMPSCORE = 0;
            if(app.PAUSE) values.wait = false;
            app.decreaseDist();
        }
    }

    //Updates score based on physics
    private void scoreUpdate() {
        ImmutableArray<Entity> obstacles = getEngine().getEntitiesFor(Family.all(ObstacleComponent.class).get());

        for(Entity entity : obstacles){
            ObstacleComponent obsCom = om.get(entity);
            BodyComponent bodyCom = bm.get(entity);

            if(!obsCom.scored){
                Body playerBody = bm.get(player).body;
                if (playerBody.getPosition().x > bodyCom.body.getPosition().x) {
                    app.SCORE++;
                    app.TEMPSCORE++;
                    obsCom.scored = true;
                }
            }
        }
    }

    //Getters
    public Entity getPlayer() {
        return player;
    }

    public GameValues getValues() {
        return values;
    }

    public ExtendViewport getViewport() {
        return viewport;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
