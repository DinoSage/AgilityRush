package io.github.dinosage.agilityrush.systems.physics;

import com.badlogic.gdx.physics.box2d.World;
import io.github.dinosage.agilityrush.GameValues;
import io.github.dinosage.agilityrush.app.AppStorage;
import io.github.dinosage.agilityrush.util.Command;

public class PhysicsResetCommand implements Command {

    //Variables
    World world;
    GameValues values;
    AppStorage app = AppStorage.getInstance();

    public PhysicsResetCommand set(PhysicsSystem system){
        this.world = system.world;
        this.values = system.values;
        return this;
    }

    @Override
    public void execute() {
        //Destroy World
        world.dispose();
        world = null;

        //Reset Boolean States
        values.jump = true;
        values.down = false;
        values.start = false;
        values.wait = false;

        //Other Variables
        values.distance = 0;
        app.GAMEOVER = false;
        app.INCREMENT = 0;
        app.SPAWNDIST = app.RESETDIST;
    }
}
