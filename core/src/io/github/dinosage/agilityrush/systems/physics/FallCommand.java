package io.github.dinosage.agilityrush.systems.physics;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Body;
import io.github.dinosage.agilityrush.app.AppStorage;
import io.github.dinosage.agilityrush.app.AssetStorage;
import io.github.dinosage.agilityrush.components.BodyComponent;
import io.github.dinosage.agilityrush.components.Mappers;
import io.github.dinosage.agilityrush.util.Command;

public class FallCommand implements Command {

    //Variables
    AssetStorage asset = AssetStorage.getInstance();
    AppStorage app = AppStorage.getInstance();
    Entity player;

    ComponentMapper<BodyComponent> bm = Mappers.BodyCom;

    public FallCommand set(PhysicsSystem system){
        player = system.getPlayer();

        return this;
    }

    @Override
    public void execute() {
        //Pushes / Accelerates player Down
        Body playerBody = bm.get(player).body;

        playerBody.setLinearVelocity(0, 0);
        playerBody.applyLinearImpulse(0, -100f, playerBody.getPosition().x, playerBody.getPosition().y, true);

        //Plays Whoosh Sound Effect
        if(app.SoundState.getValue()){
            Sound sound = asset.getSoundByName("Whoosh");
            sound.play(app.SoundVolume.getValue());
        }
    }
}
