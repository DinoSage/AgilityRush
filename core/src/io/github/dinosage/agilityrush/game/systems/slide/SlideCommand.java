package io.github.dinosage.agilityrush.game.systems.slide;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Timer;
import io.github.dinosage.agilityrush.game.GameValues;
import io.github.dinosage.agilityrush.game.components.BodyComponent;
import io.github.dinosage.agilityrush.game.components.Mappers;
import io.github.dinosage.agilityrush.game.components.ObstacleComponent;
import io.github.dinosage.agilityrush.game.components.SlideComponent;
import io.github.dinosage.agilityrush.util.Command;

public class SlideCommand implements Command {

    //All Variables
    Engine engine;
    GameValues values; //True Equals Is Sliding, False Equals Finished Sliding
    Timer timer;
    Timer.Task slideUndo;

    ComponentMapper<SlideComponent> sm = Mappers.SlideCom;
    ComponentMapper<BodyComponent> bm = Mappers.BodyCom;

    public SlideCommand set(SlideSystem system){
        engine = system.getEngine();
        values = system.screen.getValues();
        slideUndo = system.slideUndo;
        timer = system.timer;

        return this;
    }

    @Override
    public void execute() {
        ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.all(SlideComponent.class).get());
        for(Entity entity : entities){
            //Get Components
            BodyComponent bodyCom = bm.get(entity);
            SlideComponent slideCom = sm.get(entity);

            Fixture temp = slideCom.current;

            //Switch to Sliding if about to slide, otherwise switch to Regular
            if(!values.slide){
                slideCom.current = bodyCom.body.createFixture(slideCom.slidingDef);
                bodyCom.size = slideCom.slidSize;

                //Find Platform Surface Pos
                float yPos = 0;
                Family platformFam = Family.all(BodyComponent.class).exclude(SlideComponent.class, ObstacleComponent.class).get();
                ImmutableArray<Entity> platforms = engine.getEntitiesFor(platformFam);
                for(Entity platform : platforms){
                    if(bm.get(platform).name.equals("platform")){
                        Body platformBody = bm.get(platform).body;
                        yPos = platformBody.getPosition().y + bm.get(platform).size.y/2;
                    }
                }

                //bodyCom.body.getPosition().y = yPos;
                bodyCom.body.setTransform(bodyCom.body.getPosition().x, yPos, bodyCom.body.getAngle());

            } else {
                slideCom.current = bodyCom.body.createFixture(slideCom.regularDef);
                bodyCom.size = slideCom.regularSize;
            }

            //Destroying Current Fixture
            bodyCom.body.destroyFixture(temp);

            //Start Execution of SlideUndo
            timer.clear();
            timer.scheduleTask(slideUndo, 1);

            values.slide = !values.slide;
        }
    }
}
