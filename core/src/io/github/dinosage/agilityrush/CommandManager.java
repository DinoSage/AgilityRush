package io.github.dinosage.agilityrush;

import com.badlogic.gdx.utils.Array;
import io.github.dinosage.agilityrush.commands.RenderCommand;
import io.github.dinosage.agilityrush.systems.physics.FallCommand;
import io.github.dinosage.agilityrush.systems.physics.JumpCommand;
import io.github.dinosage.agilityrush.systems.slide.JumpingSlideCommand;
import io.github.dinosage.agilityrush.systems.slide.SlideCommand;
import io.github.dinosage.agilityrush.util.Command;
import io.github.dinosage.agilityrush.util.CommandLauncher;

public class CommandManager {

    public Array<CommandLauncher> launchers = new Array<>();

    //Commands Managed

    //Physics
    public JumpCommand jump = new JumpCommand();
    public FallCommand fall = new FallCommand();

    //
    public SlideCommand slide = new SlideCommand();
    public JumpingSlideCommand jumpSlide = new JumpingSlideCommand();

    //Game
    public RenderCommand render = new RenderCommand();


    //Launches Command and Returns if Launched Successfully
    public boolean launchCommand(Command command){
        for(CommandLauncher launcher : launchers){
            if(launcher.getCommand() == command){
                launcher.launch();
                return true;
            }
        }
        return false;
    }
}
