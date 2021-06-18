package io.github.dinosage.agilityrush.systems.slide;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import io.github.dinosage.agilityrush.CommandManager;
import io.github.dinosage.agilityrush.GameScreen;
import io.github.dinosage.agilityrush.app.AppStorage;
import io.github.dinosage.agilityrush.components.BodyComponent;
import io.github.dinosage.agilityrush.components.Mappers;
import io.github.dinosage.agilityrush.components.SlideComponent;
import io.github.dinosage.agilityrush.util.Command;

public class JumpingSlideCommand implements Command {

    //All Variables
    CommandManager manager;
    Engine engine;
    AppStorage app = AppStorage.getInstance();

    ComponentMapper<BodyComponent> bm = Mappers.BodyCom;

    public JumpingSlideCommand set(CommandManager manager, GameScreen screen){
        this.manager = manager;
        engine = screen.getGameEngine();

        return this;
    }

    @Override
    public void execute() {
        manager.launchCommand(manager.slide);

        ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.all(SlideComponent.class).get());
        for(Entity entity : entities){
            if(bm.get(entity).name.equals("player")){
                BodyComponent bodyCom = bm.get(entity);
                bodyCom.body.setTransform(bodyCom.body.getPosition().x, app.GROUNDHEIGHT+1+bodyCom.size.y/2, bodyCom.body.getAngle());
            }
        }

        manager.launchCommand((manager.jump));
    }
}
