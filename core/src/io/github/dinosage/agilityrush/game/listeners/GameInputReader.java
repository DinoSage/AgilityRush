package io.github.dinosage.agilityrush.game.listeners;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import io.github.dinosage.agilityrush.game.CommandManager;
import io.github.dinosage.agilityrush.game.GameScreen;
import io.github.dinosage.agilityrush.game.GameValues;
import io.github.dinosage.agilityrush.game.systems.physics.PhysicsSystem;

public class GameInputReader implements InputProcessor {

    //Variables
    GameValues values;
    PhysicsSystem physics;
    CommandManager manager;
    boolean processing = false;

    //Values
    Vector2 dragStartPoint = new Vector2();

    public GameInputReader(GameScreen screen){
        this.values = screen.getValues();
        this.manager = screen.getManager();
        this.physics = screen.getPhysics();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(values.jump && !values.pause)
            manager.launchCommand(manager.fall);

        Vector3 temp = physics.getCamera().unproject(new Vector3(screenX, screenY, 0));
        dragStartPoint.set(temp.x, temp.y);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(!processing){
            processing = true;

            //Gets location in world units of touch
            Vector3 temp = physics.getCamera().unproject(new Vector3(screenX, screenY, 0));

            //Create Difference Ray
            Vector2 ray = new Vector2(temp.x, temp.y);
            ray.sub(dragStartPoint);

            //Difference Ray Y Negative, means dragged down, if Positive, means dragged up
            if(ray.y > 5){
                //When Dragged Up
                if(!values.jump && !values.pause) {
                    if(values.slide){
                        manager.launchCommand(manager.jumpSlide);
                    } else {
                        manager.launchCommand(manager.jump);
                    }
                }
            } else if(ray.y < -5){
                if(!values.slide && !values.jump && !values.pause)
                    manager.launchCommand(manager.slide);
            } else {
                System.out.println("Don't Know");
            }

            //Updates dragStartPoint to Continue Dragging
            //dragStartPoint.set(temp.x, temp.y);

            processing = false;
        }
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountx, float amounty) {
        return false;
    }
}
