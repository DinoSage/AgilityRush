package io.github.dinosage.agilityrush.systems.hudui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import io.github.dinosage.agilityrush.GameValues;
import io.github.dinosage.agilityrush.app.AppStorage;
import io.github.dinosage.agilityrush.app.AssetStorage;
import io.github.dinosage.agilityrush.util.Command;

public class HudInitiateCommand implements Command {

    //All Variables
    
    //Commands
    PauseCommand pause = new PauseCommand();
    ResumeCommand resume = new ResumeCommand();
    
    //Game Values & Misc
    AppStorage app = AppStorage.getInstance();
    AssetStorage asset = AssetStorage.getInstance();
    GameValues values;

    //Stage & HUD/UI Related
    Table table;
    Label label;
    Stage stage;
    OrthographicCamera camera;
    HudUiSystem system;
    

    public HudInitiateCommand set(HudUiSystem system){
        table = system.table;
        label = system.label;
        values = system.values;
        stage = system.stage;
        camera = system.camera;
        this.system = system;

        return this;
    }

    @Override
    public void execute() {
        //Table setup
        table = new Table();
        table.setPosition(0, 0, Align.center);
        table.setFillParent(true);
        table.setDebug(app.DEBUG.getValue());
        this.stage.addActor(table);

        final Skin skin = asset.skin;

        camera.position.set(0f, 0f, 0f);
        //camera.translate(0f, 0f);
        camera.update();

        table.center().top();

        //Create Buffer Button Space to Center Label
        Button btn = new Button(skin, "left");
        btn.setVisible(false);
        table.add(btn);

        //Create the Score Label
        label = new Label("Score: ",skin, "title-plain");
        label.setFontScale(1.5f);
        label.setAlignment(Align.center);
        table.add(label).expandX();

        //Create the Real Pause Button
        final Button pauseBtn = new Button(skin, "right");
        pauseBtn.setName("pause");
        pauseBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                if(!values.pause){
                    pause.set(stage, system.screen, resume, pauseBtn).execute();
                    pauseBtn.setStyle(skin.get("left", Button.ButtonStyle.class));
                } else {
                    resume.set(stage, system.screen).execute();
                    pauseBtn.setStyle(skin.get("right", Button.ButtonStyle.class));
                }
                values.pause = !values.pause;
            }
        });
        table.add(pauseBtn);

        //Update Variables Back
        system.table = table;
        system.label = label;
    }
}
