package io.github.dinosage.agilityrush.systems.physics;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Body;
import io.github.dinosage.agilityrush.GameValues;
import io.github.dinosage.agilityrush.app.AppStorage;
import io.github.dinosage.agilityrush.app.AssetStorage;
import io.github.dinosage.agilityrush.components.BodyComponent;
import io.github.dinosage.agilityrush.components.Mappers;
import io.github.dinosage.agilityrush.util.Command;

public class JumpCommand implements Command {

    //Variables
    PhysicsSystem house;
    AssetStorage asset = AssetStorage.getInstance();
    AppStorage app = AppStorage.getInstance();
    Entity player;
    GameValues values;

    ComponentMapper<BodyComponent> bm = Mappers.BodyCom;

    public JumpCommand set(PhysicsSystem system){
        player = system.getPlayer();
        values = system.getValues();

        return this;
    }

    @Override
    public void execute() {
        //Makes the Player Jump Up
        Body playerBody = bm.get(player).body;

        playerBody.applyLinearImpulse(0, (float) Math.sqrt(6400), playerBody.getPosition().x, playerBody.getPosition().y, true);
        values.jump = true;

        //Plays the Jump Sound Effect
        if(app.SoundState.getValue()){
            Sound sound = asset.getSoundByName("Jump");
            sound.play(app.SoundVolume.getValue());
        }
    }
}
