package io.github.dinosage.agilityrush.systems.hudui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import io.github.dinosage.agilityrush.GameScreen;
import io.github.dinosage.agilityrush.app.AppStorage;
import io.github.dinosage.agilityrush.app.AssetStorage;
import io.github.dinosage.agilityrush.app.GameStart;
import io.github.dinosage.agilityrush.util.Command;

public class ResumeCommand implements Command {

    //All Variables
    AppStorage app = AppStorage.getInstance();
    AssetStorage asset = AssetStorage.getInstance();
    Stage stage;
    GameScreen screen;

    public ResumeCommand set(Stage stage, GameScreen screen){
        this.stage = stage;
        this.screen = screen;

        return this;
    }

    @Override
    public void execute() {
        //Updates Music Based on Setting Change & Resumes
        asset.music.setVolume(app.MusicVolume.getValue());
        if(asset.music != null && app.MusicState.getValue())
            asset.music.play();

        screen.getSliding().getTimer().start();

        //Gets Rid of Created Pause Menu
        Array<Actor> actors = stage.getActors();
        for(Actor actor : actors){
            if(actor.getName() != null && actor.getName().equals("Pause Table")){
                ((Table) actor).clearChildren();
                actor.remove();
            }
        }

        if(app.GAMEOVER){
            screen.getGame().switchScreen(GameStart.HOMESCREEN);
        }
    }
}
