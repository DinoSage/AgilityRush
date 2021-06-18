package io.github.dinosage.agilityrush.game.systems.slide;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.utils.Timer;
import io.github.dinosage.agilityrush.game.CommandManager;
import io.github.dinosage.agilityrush.game.GameScreen;
import io.github.dinosage.agilityrush.game.GameValues;

public class SlideSystem extends EntitySystem {

    //Commands
    SlideInitiateCommand initiate = new SlideInitiateCommand();

    //Game + Misc Variables
    GameScreen screen;
    GameValues values;
    CommandManager manager;
    Timer timer;
    Timer.Task slideUndo;

    //Sets up The System
    public SlideSystem(GameScreen screen, int priority){
        super(priority);
        this.screen = screen;
        manager = screen.getManager();
        values = screen.getValues();
        timer = new Timer();
    }


    @Override
    public void addedToEngine(Engine engine){
        initiate.set(this).execute();
    }

    @Override
    public void removedFromEngine(Engine engine){
        values.slideDistance = 0;
        slideUndo = null;
    }

    @Override
    public void update(float deltaTime){

    }

    //Getters & Setters
    public Timer getTimer(){
        return timer;
    }
}
