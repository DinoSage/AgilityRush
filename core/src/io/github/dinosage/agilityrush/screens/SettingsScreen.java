package io.github.dinosage.agilityrush.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import io.github.dinosage.agilityrush.app.AppStorage;
import io.github.dinosage.agilityrush.app.AssetStorage;
import io.github.dinosage.agilityrush.app.GameStart;
import io.github.dinosage.agilityrush.util.UIScreenOld;

public class SettingsScreen extends UIScreenOld {

    //Variables
    GameStart game;
    AssetStorage storage;
    Table mainTable;

    AppStorage app;
    AssetStorage asset;
    

    public SettingsScreen(GameStart game) {
        super(1000, 500);
        this.game = game;
        app = AppStorage.getInstance();
        asset = AssetStorage.getInstance();
        camera.zoom = 0.7f;
        camera.update();
    }

    @Override
    public void setup(){
        super.setup();
        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.setDebug(app.DEBUG.getValue());
        //mainTable.defaults().expand();
        mainTable.defaults().pad(10f);
        add(mainTable);

        //Additional Tables within main table
        Table musicTable = new Table();
        musicTable.setDebug(app.DEBUG.getValue());
        musicTable.defaults().expand();

        Table soundTable = new Table();
        soundTable.setDebug(app.DEBUG.getValue());
        soundTable.defaults().expand();

        //Skin
        Skin skin = asset.skin;
        app.color = app.greyColor;

    //UI Elements to Add
        //Add Settings Title
        mainTable.add(new Label("Settings", skin, "title-plain")).colspan(2);

        mainTable.row();

        //Add Debug Label
        /*mainTable.add(new Label("Debug", skin));

        //Add Debug Checkbox
        CheckBox debugBox = new CheckBox("", skin, "switch");
        debugBox.setChecked(app.DEBUG.getValue());
        mainTable.add(debugBox);

        mainTable.row();*/

        //Add Music Label
        mainTable.add(new Label("Music", skin));

        //Add Music Checkbox
        Button musicBtn = new Button(skin, "music");
        musicBtn.setChecked(app.MusicState.getValue());
        musicTable.add(musicBtn);

        //Add Music Volume Slider
        final Slider volumeSlider = new Slider(0f, 1f, 0.05f, false, skin);
        volumeSlider.setValue(app.MusicVolume.getValue());
        volumeSlider.setDisabled(!app.MusicState.getValue());
        musicTable.add(volumeSlider);

        musicTable.row();

        //Add Music Dropdown
        final SelectBox<String> musicDropdown = new SelectBox<>(skin);
        musicDropdown.setItems(asset.allMusicNames);
        musicDropdown.setSelected(asset.getMusicName(app.MusicSelection.getValue()));
        musicTable.add(musicDropdown).align(Align.center).colspan(2);
        musicDropdown.setDisabled(!app.MusicState.getValue());
        mainTable.add(musicTable);

        mainTable.row();

        //Add Sound Label
        mainTable.add(new Label("SFX", skin));

        //Add Sound Checkbox
        final Button soundBtn = new Button(skin, "sound");
        soundBtn.setChecked(app.SoundState.getValue());
        soundTable.add(soundBtn);

        //Add Sound Volume Slider
        final Slider soundSlider = new Slider(0f, 1f, 0.05f, false, skin);
        soundSlider.setValue(app.SoundVolume.getValue());
        soundSlider.setDisabled(!app.SoundState.getValue());
        soundTable.add(soundSlider);
        mainTable.add(soundTable);

        mainTable.row();

        //Adds Credits Button
        TextButton creditsButton = new TextButton("Credits", skin, "round");
        mainTable.add(creditsButton).colspan(2).pad(0f);

        mainTable.row();

        //Add Back Button
        TextButton backBtn = new TextButton("Back", skin, "round");
        mainTable.add(backBtn).colspan(2);

    //Event Listeners for All UI Elements
        /*debugBox.addListener(new ClickListener(){ // Debug Checkbox
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.DEBUG.setValue(!app.DEBUG.getValue());
                app.writeDebug();
            }
        });*/

        volumeSlider.addListener(new ChangeListener() { // Music Volume Slider
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                app.MusicVolume.setValue(volumeSlider.getValue());
                app.writeMusicVolume();
            }
        });

        musicBtn.addListener(new ClickListener(){ // Music Checkbox
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.MusicState.setValue(!app.MusicState.getValue());
                app.writeMusicState();
                if(!app.MusicState.getValue()){
                    volumeSlider.setDisabled(true);
                    musicDropdown.setDisabled(true);
                } else {
                    volumeSlider.setDisabled(false);
                    musicDropdown.setDisabled(false);
                }
            }
        });

        musicDropdown.addListener(new ChangeListener() { // Music SelectBox / Dropdown
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                for(String path : asset.allMusicPaths){
                    if(path.contains(musicDropdown.getSelected())){
                        app.MusicSelection.setValue(path);
                        app.writeMusicChoice();
                        break;
                    }
                }
            }
        });

        soundBtn.addListener(new ClickListener(){ // Sound Checkbox
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.SoundState.setValue(!app.SoundState.getValue());
                app.writeSoundState();
                soundSlider.setDisabled(!app.SoundState.getValue());
            }
        });

        soundSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                app.SoundVolume.setValue(soundSlider.getValue());
                app.writeSoundVolume();
            }
        });

        backBtn.addListener(new ClickListener(){ // Back Button
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.switchScreen(GameStart.HOMESCREEN);
            }
        });


        creditsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.switchScreen(GameStart.CREDITSSCREEN);
            }
        });
    }

    @Override
    public void hide(){
        super.hide();
        mainTable.clear();
        mainTable.remove();
        mainTable = null;
    }
}