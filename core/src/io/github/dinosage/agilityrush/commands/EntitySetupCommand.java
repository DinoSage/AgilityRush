package io.github.dinosage.agilityrush.commands;

import com.badlogic.ashley.core.Engine;
import io.github.dinosage.agilityrush.GameScreen;
import io.github.dinosage.agilityrush.util.Command;

public class EntitySetupCommand implements Command {

    //All Variables
    Engine engine;

    public EntitySetupCommand set(GameScreen screen){


        return this;
    }

    @Override
    public void execute(){

    }
}
