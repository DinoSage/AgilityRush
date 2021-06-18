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

public class HomeScreen implements Screen {

    //All Class Variables
    GameStart game;

    ExtendViewport viewport;
    //ScreenViewport viewport;
    OrthographicCamera camera;
    Stage stage;
    Table table;
    AppStorage app;
    AssetStorage asset;

    //Initializes Variables
    public HomeScreen(GameStart game){
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(500, 250, camera);
        //viewport = new ScreenViewport(camera);

        stage = new Stage(viewport);
        camera.translate(0f, 0f);
        //camera.zoom = 0.2f;
        //camera.update();
        app = AppStorage.getInstance();
        asset = AssetStorage.getInstance();
    }

    //Calls Setup and sets up screen and its ui parts
    @Override
    public void show() {
        setup();
        Gdx.input.setInputProcessor(stage);
        app.color = app.greyColor;
    }

    //Renders screen and stage (with update)
    @Override
    public void render(float delta) {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    //Resizes screen / viewport
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.translate(0f, 0f);
        camera.update();
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

    //Helper Methods
    private void setup(){
        table = new Table();
        stage.addActor(table);
        table.setDebug(app.DEBUG.getValue());
        table.defaults().expandX().pad(5f);
        table.setFillParent(true);

        Skin skin = asset.skin;

        //Adds the Different UI Elements

        //Adds label
        Label label = new Label("Agility Rush", skin);
        label.setAlignment(Align.center);
        label.setStyle(skin.get("title", Label.LabelStyle.class));
        //label.setFontScale(5);
        table.add(label);
        table.row();

        //Adds Start game button
        TextButton startBtn = new TextButton("Start Game", skin, "round");
        startBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.switchScreen(GameStart.GAMESCREEN);
            }
        });
        table.add(startBtn);
        table.row();

        //Adds How To Play Button
        TextButton creditsButton = new TextButton("How To Play?", skin, "round");
        creditsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.switchScreen(GameStart.HOWTOPLAYSCREEN);
            }
        });

        table.add(creditsButton).row();

        //Adds Settings Button
        TextButton settingBtn = new TextButton("Settings", skin, "round");
        settingBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.switchScreen(GameStart.SETTINGSCREEN);
            }
        });
        table.add(settingBtn);
        //table.row();
    }
}
