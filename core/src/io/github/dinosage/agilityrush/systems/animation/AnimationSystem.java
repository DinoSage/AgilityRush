package io.github.dinosage.agilityrush.systems.animation;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;

public class AnimationSystem extends EntitySystem {

    //Commands
    AnimationSetupCommand setup = new AnimationSetupCommand();


    @Override
    public void addedToEngine(Engine engine){
        setup.set(this).execute();
    }

    @Override
    public void removedFromEngine(Engine engine){

    }

    @Override
    public void update(float deltaTime){

    }
}
