package io.github.dinosage.agilityrush.game.listeners;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.*;
import io.github.dinosage.agilityrush.game.GameValues;
import io.github.dinosage.agilityrush.app.AppStorage;
import io.github.dinosage.agilityrush.game.components.BodyComponent;
import io.github.dinosage.agilityrush.game.components.Mappers;

public class ContactReader implements ContactListener {

    //Variables
    public Entity demolished = null;
    GameValues values;

    //Component Mappers
    ComponentMapper<BodyComponent> bm = Mappers.BodyCom;

    //Sets up Values
    public ContactReader(GameValues values){
        this.values = values;
    }

    @Override
    public void beginContact(Contact contact) {
        Entity player = getEntityByBodyName(contact, "player");
        Entity platform = getEntityByBodyName(contact, "platform");
        Entity obstacle = getEntityByBodyName(contact, "obstacle");

        if(check(player, platform)){
            values.jump = false;
            values.down = false;
            if(!values.start)
                values.start = true;
        }

        if(check(player, obstacle)){
            AppStorage.getInstance().GAMEOVER = true;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Entity platform = getEntityByBodyName(contact, "platform");
        Entity obstacle = getEntityByBodyName(contact, "obstacle");

        if(check(platform, obstacle)){
            demolished = obstacle;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    //Helper Methods

    //Used to get an Entity from a Fixture
    private Entity getEntityFromFixture(Fixture fixture){
        Body body = fixture.getBody();
        if(body.getUserData() instanceof Entity){
            return (Entity) body.getUserData();
        }
        return null;
    }

    //Checks to see if any objects are null, handy
    private boolean check(Object... objects){
        for(Object object : objects){
            if(object == null){
                return false;
            }
        }
        return true;
    }

    //Gets The Entity which has a Body Component w/ Desired Name
    private Entity getEntityByBodyName(Contact contact, String name){
        Entity eA = getEntityFromFixture(contact.getFixtureA());
        Entity eB = getEntityFromFixture(contact.getFixtureB());
        if(check(eA) && check(bm.get(eA))){
            if(bm.get(eA).name.equals(name)){
                return eA;
            }
        }
        if(check(eB) && check(bm.get(eB))){
            if(bm.get(eB).name.equals(name)){
                return eB;
            }
        }
        return null;
    }
}
