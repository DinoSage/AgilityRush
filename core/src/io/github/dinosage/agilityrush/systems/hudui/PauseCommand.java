package io.github.dinosage.agilityrush.systems.hudui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.dinosage.agilityrush.GameScreen;
import io.github.dinosage.agilityrush.GameValues;
import io.github.dinosage.agilityrush.app.AppStorage;
import io.github.dinosage.agilityrush.app.AssetStorage;
import io.github.dinosage.agilityrush.util.Command;

public class PauseCommand implements Command {

    //All Variables
    AppStorage app = AppStorage.getInstance();
    AssetStorage asset = AssetStorage.getInstance();
    GameValues values;
    ResumeCommand resume;
    Stage stage;
    Button pauseBtn;
    GameScreen screen;

    public PauseCommand set(Stage stage, GameScreen screen, ResumeCommand resume, Button pauseBtn){
        this.stage = stage;
        this.values = screen.getValues();
        this.resume = resume;
        this.pauseBtn = pauseBtn;
        this.screen = screen;

        return this;
    }

    @Override
    public void execute() {
        //Pauses Music
        if(asset.music.isPlaying())
            asset.music.pause();

        //Creates Pause Menu
        final Skin skin = asset.skin;

        Table pauseTable = new Table();
        pauseTable.setFillParent(true);
        pauseTable.setDebug(app.DEBUG.getValue());

        //Sets Up Table & Window
        pauseTable.setName("Pause Table");
        pauseTable.moveBy(-stage.getWidth()/2, - stage.getHeight()/2);

        Window window = new Window("Paused", skin);
        window.setMovable(false);
        window.setName("window");

        //Setups Up Pause Menu Settings
        Table windowTable = new Table();
        //table.setFillParent(true);
        //table.defaults().pad(5f);
        //table.setDebug(app.DEBUG.getValue());

        Table musicTable = new Table();
        musicTable.setDebug(app.DEBUG.getValue());
        musicTable.defaults().expand();

        Table soundTable = new Table();
        soundTable.setDebug(app.DEBUG.getValue());
        soundTable.defaults().expand();

        //Add Music Label
        windowTable.add(new Label("Music", skin));

        //Add Music Checkbox
        Button musicBtn = new Button(skin, "music");
        musicBtn.setChecked(app.MusicState.getValue());
        musicTable.add(musicBtn);

        //Add Music Volume Slider
        final Slider volumeSlider = new Slider(0f, 1f, 0.05f, false, skin);
        volumeSlider.setValue(app.MusicVolume.getValue());
        volumeSlider.setDisabled(!app.MusicState.getValue());
        musicTable.add(volumeSlider);
        windowTable.add(musicTable);

        windowTable.row();

        //Add Sound Label
        windowTable.add(new Label("SFX", skin));

        //Add Sound Checkbox
        final Button soundBtn = new Button(skin, "sound");
        soundBtn.setChecked(app.SoundState.getValue());
        soundTable.add(soundBtn);

        //Add Sound Volume Slider
        final Slider soundSlider = new Slider(0f, 1f, 0.05f, false, skin);
        soundSlider.setValue(app.SoundVolume.getValue());
        soundSlider.setDisabled(!app.SoundState.getValue());
        soundTable.add(soundSlider);
        windowTable.add(soundTable);

        windowTable.row();

        //Add Resume Button
        final TextButton resumeBtn = new TextButton("Resume", skin);
        windowTable.add(resumeBtn).colspan(2);

        windowTable.row();

        //Add Exit Button
        final TextButton exitBtn = new TextButton("Exit", skin);
        windowTable.add(exitBtn).colspan(2);

        //////
        //Are you sure? Dialog
        /*final PopTable youSure = new PopTable(skin);
        youSure.setDebug(app.DEBUG.getValue());
        youSure.hide();
        youSure.attachToActor(window);
        //youSure.setPosition(stage.getWidth()/2, stage.getHeight()/2);

        youSure.add(new Label("Are you sure? Any unsaved progress will be lost!", skin, "error")).colspan(2).center();

        youSure.row();

        //Yes Btn
        TextButton yes = new TextButton("Yes", skin);
        youSure.add(yes).center();

        //No Btn
        TextButton no = new TextButton("No", skin);
        youSure.add(no).center();

        stage.addActor(youSure);

         */
        //////

        //Stops Timer
        screen.getSliding().getTimer().stop();

        //Event Listeners for All UI Elements
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
                volumeSlider.setDisabled(!app.MusicState.getValue());
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

        resumeBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                values.pause = !values.pause;
                pauseBtn.setStyle(skin.get("right", Button.ButtonStyle.class));
                resume.set(stage, screen).execute();
            }
        });

        exitBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.SCORE = 0;
                app.TEMPSCORE = 0;
                app.GAMEOVER = true;
                values.pause = !values.pause;
                resume.set(stage, screen).execute();
            }
        });

        /*yes.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.SCORE = 0;
                app.TEMPSCORE = 0;
                app.GAMEOVER = true;
                values.pause = !values.pause;
                resume.set(stage, screen).execute();
            }
        });*/

        /*no.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                youSure.hide();
            }
        });*/

        window.add(windowTable);

        //Adds The Table & Windows
        pauseTable.add(window);
        stage.addActor(pauseTable);
    }

}
