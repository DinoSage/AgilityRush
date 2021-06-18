package io.github.dinosage.agilityrush.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import io.github.dinosage.agilityrush.app.AppStorage;
import io.github.dinosage.agilityrush.app.AssetStorage;
import io.github.dinosage.agilityrush.app.GameStart;

public class ScoreScreen implements Screen {

    //All Variables
    GameStart game;

    ExtendViewport viewport;
    OrthographicCamera camera;
    Stage stage;
    Table table;

    AppStorage app;
    AssetStorage asset;

    public ScoreScreen(GameStart game){
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(400, 200, camera);
        stage = new Stage(viewport);
        camera.translate(0f, 0f);
        camera.update();
        app = AppStorage.getInstance();
        asset = AssetStorage.getInstance();
        //camera.zoom = 0.4f;
        //camera.update();
    }

    @Override
    public void show() {
        setup();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        table.remove();
        table.clear();
        table = null;
    }

    @Override
    public void dispose() {

    }

    public void setup(){
        //Table Setup
        app.calculateHighScore();
        table = new Table();
        stage.addActor(table);
        table.setDebug(app.DEBUG.getValue());
        table.defaults().pad(10f).fill();
        table.setFillParent(true);

        Skin skin = asset.skin;
        //High Score Label that displays the highest score anyone has received
        Label highScoreLabel = new Label("High Score: " + app.HighScore.getValue(), skin, "title-plain");
        highScoreLabel.setAlignment(Align.center);

        //The score the player just got
        Label scoreLabel = new Label("Last Score: " + app.SCORE, skin, "title-plain");
        scoreLabel.setAlignment(Align.center);

        TextButton homeBtn = new TextButton("Home", skin, "round");
        homeBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                app.SCORE = 0;
                app.TEMPSCORE = 0;
                game.switchScreen(GameStart.HOMESCREEN);
            }
        });
        table.add(highScoreLabel);
        table.row();
        table.add(scoreLabel);
        table.row();
        table.add(homeBtn);
        app.color = app.greyColor;
    }
}
