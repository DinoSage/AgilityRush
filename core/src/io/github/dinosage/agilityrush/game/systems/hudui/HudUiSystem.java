package io.github.dinosage.agilityrush.game.systems.hudui;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import io.github.dinosage.agilityrush.game.GameScreen;
import io.github.dinosage.agilityrush.game.GameValues;
import io.github.dinosage.agilityrush.app.AppStorage;

public class HudUiSystem extends EntitySystem {

    //Commands
    HudInitiateCommand initiate = new HudInitiateCommand();

    //App Variables
    GameScreen screen;
    GameValues values;

    //HUD Variables
    ExtendViewport viewport;
    Stage stage;
    Table table;
    SpriteBatch batch;
    OrthographicCamera camera;
    Label label;

    AppStorage app = AppStorage.getInstance();

    //Called To Setup PhysicsSystem
    public HudUiSystem(GameScreen screen, int priority){
        super(priority);
        this.screen = screen;
        values = screen.getValues();

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(500f, 250f, camera);
        stage = new Stage(viewport, batch);
        camera.translate(0f, 0f);
    }

    //Called to Setup System
    @Override
    public void addedToEngine(Engine engine){
        initiate.set(this).execute();
    }

    //Called to Reset System
    @Override
    public void removedFromEngine(Engine engine){
        table.remove();
        table.clear();
        table = null;
    }

    @Override
    public void update(float deltaTime){
        //Updates Score, Table Pos (Must Fix), and Changes Label When game Over
        table.setPosition(0, 0, Align.center);
        if(!app.GAMEOVER)
            label.setText("Score: " + app.SCORE);
        else {
            label.setText("GAME OVER");
            label.setColor(Color.RED);
        }

        //Updates & Draws Stage
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    //Getters
    public ExtendViewport getViewport() {
        return viewport;
    }

    public Stage getStage() {
        return stage;
    }

    public HudInitiateCommand getInitiate(){
        return initiate;
    }
}
