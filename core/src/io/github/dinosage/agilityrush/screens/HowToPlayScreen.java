package io.github.dinosage.agilityrush.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import io.github.dinosage.agilityrush.app.AppStorage;
import io.github.dinosage.agilityrush.app.AssetStorage;
import io.github.dinosage.agilityrush.app.GameStart;
import io.github.dinosage.agilityrush.util.UIScreen;

public class HowToPlayScreen extends UIScreen {

    //Variables
    AppStorage app = AppStorage.getInstance();
    AssetStorage asset = AssetStorage.getInstance();
    GameStart game;

    public HowToPlayScreen(GameStart game) {
        super(500, 300);
        this.game = game;
    }

    @Override
    protected void setup() {
        //Table Setup
        mainTable.setDebug(false);
        mainTable.setFillParent(true);

        //Add Title
        Label title = new Label("How to play?", asset.skin, "title-plain");
        title.setAlignment(Align.center);
        mainTable.add(title).left().grow().spaceLeft(2.5f).spaceRight(2.5f);
        mainTable.row();

        //Add Large Label
        String tutStr1 = "It's simple! You are the player, the white box near the left side of the screen. Your goal: TO AVOID TOUCHING THE WHITE OBSTACLES! " +
                "To achieve this, you have a variety of possible actions like:";

        String actionStr = "\t-> JUMP: Swipe UP while on the ground to jump into the air." +
                            "\n\t-> SLIDE: Swipe DOWN while on the ground to slide under obstacles." +
                            "\n\t-> FALL: Tap on the screen while midair to quickly fall back to the ground.";

        Label tutorial = new Label(tutStr1, asset.skin);
        tutorial.setWrap(true);
        Label actionLabel = new Label(actionStr, asset.skin);
        actionLabel.setWrap(true);

        mainTable.add(tutorial).left().grow().spaceLeft(2.5f).spaceRight(2.5f).row();
        mainTable.add(actionLabel).left().grow().spaceLeft(2.5f).spaceRight(2.5f).row();

        Label toRemember = new Label("Things To Remember", asset.skin);
        toRemember.setAlignment(Align.center);
        mainTable.add(toRemember).left().grow().spaceLeft(2.5f).spaceRight(2.5f).row();

        String remember = "- You can JUMP while SLIDING, resulting in a regular JUMP while returning to regular shape!" +
                "\n- You can also modify the settings to your personal preference!" +
                "\n- You can pause and resume the game by tapping on the arrow near the top right!" +
                "\n- Have fun and enjoy the game! Have some feedback? Email me at dinosage5632@gmail.com!";

        Label rememberLabel = new Label(remember, asset.skin);
        rememberLabel.setWrap(true);

        mainTable.add(rememberLabel).left().grow().spaceLeft(2.5f).spaceRight(2.5f).row();

        TextButton backBtn = new TextButton("Back", asset.skin, "round");

        mainTable.add(backBtn);

        backBtn.addListener(new ClickListener(){ // Back Button
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.switchScreen(GameStart.HOMESCREEN);
            }
        });
    }
    @Override
    public void hide(){
        super.hide();
        mainTable.clear();
        mainTable.remove();
        mainTable = new Table();
        stage.addActor(mainTable);
    }
}
