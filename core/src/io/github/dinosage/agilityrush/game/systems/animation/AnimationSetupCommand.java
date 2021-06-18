package io.github.dinosage.agilityrush.game.systems.animation;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import io.github.dinosage.agilityrush.game.components.AnimationComponent;
import io.github.dinosage.agilityrush.game.components.BodyComponent;
import io.github.dinosage.agilityrush.game.components.Mappers;
import io.github.dinosage.agilityrush.game.components.SlideComponent;
import io.github.dinosage.agilityrush.util.Command;

public class AnimationSetupCommand implements Command {

    //All Variables
    Engine engine;

    ComponentMapper<AnimationComponent> am = Mappers.AnimCom;
    ComponentMapper<BodyComponent> bm = Mappers.BodyCom;

    public AnimationSetupCommand set(AnimationSystem system){
        this.engine = system.getEngine();
        return this;
    }

    @Override
    public void execute(){
        //Gets Entities of Animation Family
        Family family = Family.all(AnimationComponent.class, BodyComponent.class, SlideComponent.class).get();
        ImmutableArray<Entity> entities = engine.getEntitiesFor(family);

        //Loops Through Entities
        for(Entity entity : entities){
            if(bm.get(entity).name.equals("player")){
                AnimationComponent animCom = am.get(entity);

            }
        }
    }

}
