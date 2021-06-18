package io.github.dinosage.agilityrush.commands;

import io.github.dinosage.agilityrush.CommandManager;
import io.github.dinosage.agilityrush.GameScreen;
import io.github.dinosage.agilityrush.util.Command;
import io.github.dinosage.agilityrush.util.CommandLauncher;

public class LauncherSetupCommand implements Command {

    //All Variables
    GameScreen screen;
    CommandManager manager;

    public LauncherSetupCommand set(GameScreen screen, CommandManager manager){
        this.screen = screen;
        this.manager = manager;

        return this;
    }

    @Override
    public void execute() {
        manager.launchers.add(new CommandLauncher(manager.jump){
            @Override
            public void launch(){
                manager.jump.set(screen.getPhysics()).execute();
            }
        });
        manager.launchers.add(new CommandLauncher(manager.fall){
            @Override
            public void launch(){
                manager.fall.set(screen.getPhysics()).execute();
            }
        });
        manager.launchers.add(new CommandLauncher(manager.slide){
            @Override
            public void launch(){
                manager.slide.set(screen.getSliding()).execute();
            }
        });
        manager.launchers.add(new CommandLauncher(manager.jumpSlide){
            @Override
            public void launch(){
                manager.jumpSlide.set(manager, screen).execute();
            }
        });
    }
}
