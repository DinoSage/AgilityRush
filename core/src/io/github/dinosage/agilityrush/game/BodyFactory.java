package io.github.dinosage.agilityrush.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.github.dinosage.agilityrush.game.components.BodyComponent;

public class BodyFactory {
    //The Singleton Instance
    private static BodyFactory instance = null;
    private static final int DYNAMIC = 1;
    private static final int STATIC = 2;
    private static final int KINEMATIC = 3;

    //Variables
    World world;
    LastFixture lastFixture = new LastFixture();

    private BodyFactory(){

    }

    //Singleton Class Method
    public static BodyFactory getInstance(){
        if(instance == null){
            instance = new BodyFactory();
        }
        return instance;
    }

    //Body Creation Methods

    // pos = World Position of Body
    // Enter null for center to be automatically created
    public Body squareBody(int bodyType, Vector2 posTemp, Vector2 size, Vector2 center, float angle, float density, float friction, float restitution, Object data) {
        //Create Body and Shape
        Body body = createBody(bodyType);
        PolygonShape box = new PolygonShape();
        Vector2 pos = new Vector2(posTemp);
        if (center == null) {
            box.setAsBox(size.x / 2, size.y / 2);
        } else {
            box.setAsBox(size.x / 2, size.y / 2, center, 0);
        }

        //Set Fixture Details
        FixtureDef def = new FixtureDef();
        def.shape = box;
        def.density = density;
        def.friction = friction;
        def.restitution = restitution;

        //Create Fixture and alter Body
        Fixture fix = body.createFixture(def);
        body.setTransform(new Vector2(pos), angle);
        body.setUserData(data);

        //Setup Last Body
        lastFixture.origin = body.getWorldCenter();
        lastFixture.size = size;

        //Dispose Shape and return Body
        box.dispose();
        return body;
    }

    //Creates a body depending on what constant / type
    //The options are: Dynamic Body, Static Body, or Kinematic Body
    public Body createBody(int type){
        BodyDef def = new BodyDef();
        switch(type){
            case DYNAMIC:
                def.type = BodyDef.BodyType.DynamicBody;
                break;
            case STATIC:
                def.type = BodyDef.BodyType.StaticBody;
                break;
            case KINEMATIC:
                def.type = BodyDef.BodyType.KinematicBody;
                break;
        }
        return world.createBody(def);
    }

    //Helper Methods
    public FixtureDef fixtureToFixtureDef(BodyComponent bm, Fixture fix){
        FixtureDef def = new FixtureDef();

        PolygonShape box = new PolygonShape();
        box.setAsBox(bm.size.x/2, bm.size.y/2, new Vector2(0, 0), bm.body.getAngle());

        def.shape = box;

        def.density = fix.getDensity();
        def.restitution = fix.getRestitution();
        def.friction = fix.getFriction();

        return def;
    }

    public FixtureDef createSlidingFixtureDef(BodyComponent bodyCom, Fixture old){
        FixtureDef def = new FixtureDef();

        //Set Up Shape
        PolygonShape box = new PolygonShape();
        Vector2 newSize = new Vector2();
        newSize.set(bodyCom.size.x, bodyCom.size.y/3);

        box.setAsBox(newSize.x/2, newSize.y/2, new Vector2(0, 0), bodyCom.body.getAngle());
        def.shape = box;

        //Set Up Density
        float volume = newSize.x * newSize.y;
        def.density = bodyCom.body.getMass()/volume;

        //Set Up Other Variables
        def.friction = old.getFriction();
        def.restitution = old.getRestitution();

        //Update LastClass
        lastFixture.origin = null;
        lastFixture.size = newSize;

        return def;
    }


    //World Getter & Setter
    public void setWorld(World world){
        this.world = world;
    }

    public World getWorld(){
        return world;
    }

    public Vector2 getLastFixtureSize(){
        return lastFixture.size;
    }

}

class LastFixture {
    public Vector2 size;
    public Vector2 origin;
}
