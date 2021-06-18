package io.github.dinosage.agilityrush.util;

public abstract class CommandLauncher {

    Command command;

    public CommandLauncher(Command command){
        this.command = command;
    }

    public abstract void launch();

    public Command getCommand(){
        return command;
    }
}
