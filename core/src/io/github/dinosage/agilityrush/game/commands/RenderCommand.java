package io.github.dinosage.agilityrush.game.commands;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import io.github.dinosage.agilityrush.game.GameScreen;
import io.github.dinosage.agilityrush.game.GameValues;
import io.github.dinosage.agilityrush.app.AppStorage;
import io.github.dinosage.agilityrush.app.AssetStorage;
import io.github.dinosage.agilityrush.app.GameStart;
import io.github.dinosage.agilityrush.game.components.BodyComponent;
import io.github.dinosage.agilityrush.game.components.Mappers;
import io.github.dinosage.agilityrush.util.Command;

public class RenderCommand implements Command {

    //Game Related & Misc
    float delta;
    AppStorage app = AppStorage.getInstance();
    AssetStorage asset = AssetStorage.getInstance();
    GameValues values;
    Engine engine;
    GameStart game;
    GameScreen screen;

    //Component Mappers
    ComponentMapper<BodyComponent> bm = Mappers.BodyCom;

    public RenderCommand set(GameScreen screen, float delta){
        engine = screen.getGameEngine();
        values = screen.getValues();
        game = screen.getGame();
        this.screen = screen;
        this.delta = delta;

        return this;
    }

    @Override
    public void execute() {
        //Render If Situation Met
        engine.update(delta);

        if (app.GAMEOVER) {
            //Stops Music
            if(asset.music.isPlaying()){
                asset.music.stop();
            }
            //Moves to ScoreScreen When Touched Again
            if (Gdx.input.justTouched()) {
                game.switchScreen(GameStart.SCORESCREEN);
            }
        }
    }
}
