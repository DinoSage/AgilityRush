package io.github.dinosage.agilityrush.game.systems.slide;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Timer;
import io.github.dinosage.agilityrush.game.BodyFactory;
import io.github.dinosage.agilityrush.game.CommandManager;
import io.github.dinosage.agilityrush.game.GameValues;
import io.github.dinosage.agilityrush.game.components.BodyComponent;
import io.github.dinosage.agilityrush.game.components.Mappers;
import io.github.dinosage.agilityrush.game.components.SlideComponent;
import io.github.dinosage.agilityrush.util.Command;

public class SlideInitiateCommand implements Command {

    //All Variables
    Engine engine;
    BodyFactory factory = BodyFactory.getInstance();
    GameValues values;
    CommandManager manager;
    SlideSystem system;

    ComponentMapper<SlideComponent> sm = Mappers.SlideCom;
    ComponentMapper<BodyComponent> bm = Mappers.BodyCom;

    public SlideInitiateCommand set(SlideSystem system){
        engine = system.getEngine();
        values = system.values;
        manager = system.manager;
        this.system = system;

        return this;
    }


    @Override
    public void execute() {
        //Sets Up The SlideComponent For Player
        ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.all(BodyComponent.class).get());
        for(Entity entity : entities){
            if(bm.get(entity).name.equals("player")){
                //Add Sliding Capability
                entity.add(new SlideComponent());

                //Get Components
                SlideComponent slideCom = sm.get(entity);
                BodyComponent bodyCom = bm.get(entity);

                //Set Up Regular Variables
                slideCom.current = bodyCom.body.getFixtureList().get(0);
                slideCom.regularDef = factory.fixtureToFixtureDef(bodyCom, slideCom.current);
                slideCom.regularSize = bodyCom.size;

                //Set Up Sliding Variables
                slideCom.slidingDef = factory.createSlidingFixtureDef(bodyCom, slideCom.current);
                slideCom.slidSize = factory.getLastFixtureSize();
            }
        }

        //Sets Up The Slide Undo
        system.slideUndo = new Timer.Task() {
            @Override
            public void run() {
                if(values.slide){
                    manager.launchCommand(manager.slide);
                }
            }
        };
    }


}
