package io.github.dinosage.agilityrush;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import io.github.dinosage.agilityrush.app.AppStorage;
import io.github.dinosage.agilityrush.app.AssetStorage;
import io.github.dinosage.agilityrush.app.GameStart;
import io.github.dinosage.agilityrush.commands.LauncherSetupCommand;
import io.github.dinosage.agilityrush.listeners.GameInputReader;
import io.github.dinosage.agilityrush.systems.hudui.HudUiSystem;
import io.github.dinosage.agilityrush.systems.physics.PhysicsSystem;
import io.github.dinosage.agilityrush.systems.shape.ShapeSystem;
import io.github.dinosage.agilityrush.systems.slide.SlideSystem;

public class GameScreen implements Screen {

    //All Class Variables
    GameStart game;
    Engine gameEngine;

    //Commands
    LauncherSetupCommand setup = new LauncherSetupCommand();
    CommandManager manager = new CommandManager();

    //Systems
    PhysicsSystem physics;
    HudUiSystem hudUi;
    ShapeSystem shape;
    SlideSystem slide;

    //Miscellaneous
    AssetStorage asset = AssetStorage.getInstance();
    AppStorage app = AppStorage.getInstance();
    InputMultiplexer multiplexer;
    GameValues values = new GameValues();
    GameInputReader reader;

    //Initializes majority of variables
    public GameScreen(GameStart game){
        this.game = game;
        gameEngine = new Engine();

        physics = new PhysicsSystem(this, 1);
        shape = new ShapeSystem(this, 0);
        hudUi = new HudUiSystem(this, 2);
        slide = new SlideSystem(this, 3);

        setup.set(this, manager).execute();

        reader = new GameInputReader(this);
        multiplexer = new InputMultiplexer(hudUi.getStage(), reader);
    }

    //Does UI and Physics Setup
    @Override
    public void show() {
        //Start Setups
        gameEngine.addSystem(physics);
        gameEngine.addSystem(hudUi);
        gameEngine.addSystem(shape);
        gameEngine.addSystem(slide);

        //Add InputProcessors
        Gdx.app.getInput().setInputProcessor(multiplexer);
        app.color = app.blackColor;

        //Start Playing Music
        if(asset.music != null && app.MusicState.getValue()){
            asset.music.play();
            asset.music.setVolume(app.MusicVolume.getValue());
            asset.music.setLooping(true);
        }
    }

    //Renders / Updates both UI and Physics
    @Override
    public void render(float delta) {
        manager.render.set(this, delta).execute();
    }

    //Resizes both viewports
    @Override
    public void resize(int width, int height) {
        physics.getViewport().update(width, height);
        hudUi.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    //Calls reset for both models
    @Override
    public void hide() {
        gameEngine.removeSystem(physics);
        gameEngine.removeSystem(hudUi);
        gameEngine.removeSystem(shape);
        gameEngine.removeSystem(slide);

        gameEngine.removeAllEntities();
    }

    @Override
    public void dispose() {

    }

    //Getters & Setters
    public Engine getGameEngine() {
        return gameEngine;
    }

    public GameValues getValues() {
        return values;
    }

    public GameStart getGame() {
        return game;
    }

    public PhysicsSystem getPhysics() {
        return physics;
    }

    public SlideSystem getSliding() {
        return slide;
    }

    public HudUiSystem getHudUi(){
        return hudUi;
    }

    public CommandManager getManager(){
        return manager;
    }
}
