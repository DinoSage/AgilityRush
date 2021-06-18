package io.github.dinosage.agilityrush.screens;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import io.github.dinosage.agilityrush.app.AppStorage;
import io.github.dinosage.agilityrush.app.AssetStorage;
import io.github.dinosage.agilityrush.app.GameStart;
import io.github.dinosage.agilityrush.util.UIScreenOld;

public class LoadingScreen extends UIScreenOld {

    //Variables
    GameStart game;

    AssetStorage asset;
    AppStorage app;

    Image image;
    Table mainTable;

    public int SCREENTOGO = 0;

    public LoadingScreen(GameStart game){
        super(1000, 500);
        this.game = game;
        app = AppStorage.getInstance();
        asset = AssetStorage.getInstance();
        asset.loadLoading();
        asset.manager.finishLoading();
        asset.skin = (Skin) asset.get(AppStorage.skinPath);
    }

    @Override
    public void setup(){
        super.setup();
        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.setDebug(app.DEBUG.getValue());
        add(mainTable);

        Skin skin = asset.skin;
        //Adds Loading Image
        //image = new Image((Texture)asset.get("Loading Symbol.png"));
        //table.add(image).width(150f).height(150f);

        //Adds Progress Bar
        ProgressBar loading = new ProgressBar(0, 1, 0.05f, false, skin);
        loading.setName("loading");
        mainTable.add(loading);

    }

    @Override
    public void render(float delta){
        super.render(delta);
        ProgressBar loading = mainTable.findActor("loading");
        loading.setValue(asset.manager.getProgress());
        asset.render();
        if(!asset.render){
            if(SCREENTOGO == GameStart.GAMESCREEN) toGame(false);
            if(SCREENTOGO == GameStart.SCORESCREEN) toScore(false);
            if(SCREENTOGO == GameStart.HOMESCREEN) toHome(false);
        }
    }

    @Override
    public void show(){
        super.show();
        app.color = app.blackColor;

        if(SCREENTOGO == GameStart.GAMESCREEN){
            toGame(true);
        } else if (SCREENTOGO == GameStart.SCORESCREEN){
            toScore(true);
        } else if (SCREENTOGO == GameStart.HOMESCREEN) {
            toHome(true);
        } else {
            game.switchScreen(SCREENTOGO);
        }
    }

    @Override
    public void hide(){
        super.hide();
        app.color = new Color(0f, 0f, 0f, 1f);
        mainTable.clear();
        mainTable.remove();
        mainTable = null;
        image = null;
    }

    //My Own Methods
    void toGame(boolean state){
        if(state){
            if(!asset.manager.isLoaded(app.MusicSelection.getValue())){
                asset.queue(app.MusicSelection.getValue(), Music.class);
                for(String path : asset.soundPaths){
                    asset.queue(path, Sound.class);
                }
                asset.queue("JumpSoundEffect.wav", Sound.class);
                asset.load();
            }
        } else {
            asset.music = (Music) asset.get(app.MusicSelection.getValue());
            //asset.sound = (Sound) asset.get("JumpSoundEffect.wav");
            game.switchScreen(SCREENTOGO);
        }
    }

    //My Own Methods
    void toScore(boolean state){
        if(state){
            asset.unload(app.MusicSelection.getValue());
            for(String path : asset.soundPaths){
                asset.unload(path);
            }
        } else {
            game.switchScreen(SCREENTOGO);
        }
    }

    void toHome(boolean state){
        if(state){
            if(GameStart.CURRENT == 0){
                asset.loadAssets();
            }
            asset.unload(app.MusicSelection.getValue());
        } else {
            game.switchScreen(SCREENTOGO);
        }
    }
}
