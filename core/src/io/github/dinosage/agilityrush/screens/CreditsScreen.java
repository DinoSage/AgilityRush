package io.github.dinosage.agilityrush.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.dinosage.agilityrush.app.AppStorage;
import io.github.dinosage.agilityrush.app.AssetStorage;
import io.github.dinosage.agilityrush.app.GameStart;
import io.github.dinosage.agilityrush.util.UIScreenOld;

public class CreditsScreen extends UIScreenOld {

    //Variables
    GameStart game;
    AssetStorage storage;
    Table mainTable;

    AppStorage app;
    AssetStorage asset;

    public CreditsScreen(GameStart game) {
        super(500, 250);
        this.game = game;
        app = AppStorage.getInstance();
        asset = AssetStorage.getInstance();
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
        app.color = app.greyColor;
        if(app.SKIN){
            shadeSkin();
        } else {
            glassySkin(asset.skin);
        }
    }

    @Override
    public void hide(){
        super.hide();
        mainTable.clear();
        mainTable.remove();
        mainTable = null;
    }

    //UI For Shade
    private void shadeSkin(){

    }

    //UI For Glassy
    private void glassySkin(Skin skin){
        //The Different Labels
        mainTable.add(new Label("CREDITS", skin, "title-plain"));

        mainTable.row();

        //ScrollPane Setup
        Table scrollTable = new Table();
        scrollTable.setDebug(app.DEBUG.getValue());
        scrollTable.defaults().pad(10f);
        ScrollPane scroller = new ScrollPane(scrollTable, skin);
        scroller.setFlickScroll(true);

        mainTable.add(scroller).fill().expandX();

        //Credits Start

        //Ansh Credits
        Table dinoTable = new Table();
        dinoTable.setDebug(app.DEBUG.getValue());
        dinoTable.defaults().pad(5f);

        dinoTable.add(new Label("Game made by DinoSage", skin));
        dinoTable.row();

        Label dinoItchLink = new Label("DinoSage's Itch Page", skin);
        dinoItchLink.setColor(app.purpleLinkColor);
        dinoItchLink.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                //Gdx.net.openURI("");
                //Put Link To Itch
            }
        });

        dinoTable.add(dinoItchLink);
        scrollTable.add(dinoTable);

        scrollTable.row();


        //SilentCrafter Credits
        Table silentCrafter = new Table();
        silentCrafter.setDebug(app.DEBUG.getValue());
        silentCrafter.defaults().pad(5f);

        silentCrafter.add(new Label("Music By SilentCrafter", skin)).pad(10f);
        silentCrafter.row();

        silentCrafter.add(new Label("Music Titles: Into The Game, Lights, & Power", skin));
        silentCrafter.row();

        Label silentCrafterChannel = new Label("Click Here to go to SilentCrafter's Youtube Channel!", skin);
        silentCrafterChannel.setColor(app.purpleLinkColor);
        silentCrafterChannel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.net.openURI("https://www.youtube.com/channel/UCuwFIeJUww7GQZ8IH1F7SwA");
            }
        });
        silentCrafter.add(silentCrafterChannel);
        silentCrafter.row();

        Label silentCrafterDiscord = new Label("Click Here to go to SilentCrafter's Discord Server!", skin);
        silentCrafterDiscord.setColor(app.purpleLinkColor);
        silentCrafterDiscord.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.net.openURI("https://discord.gg/GxEsWzPk");
            }
        });
        silentCrafter.add(silentCrafterDiscord);
        scrollTable.add(silentCrafter);

        scrollTable.row();

        //ZapSplat Credits
        Table zapsplatTable = new Table();
        zapsplatTable.setDebug(app.DEBUG.getValue());
        zapsplatTable.defaults().pad(5f);

        zapsplatTable.add(new Label("Sound effects obtained from", skin));
        Label zapsplatLink = new Label("https://www.zapsplat.com/", skin);
        zapsplatLink.setColor(app.purpleLinkColor);
        zapsplatLink.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.net.openURI("https://www.zapsplat.com/");
            }
        });

        zapsplatTable.add(zapsplatLink);
        scrollTable.add(zapsplatTable);

        scrollTable.row();

        //Shade Ui Credits
        Table shadUiTable = new Table();
        shadUiTable.setDebug(app.DEBUG.getValue());
        shadUiTable.defaults().pad(5f);

        shadUiTable.add(new Label("Created by Raymond \"Raeleus\" Buckley", skin));
        Label shadUiLink = new Label("Visit ray3k.com for games, tutorials, and much more!", skin);
        shadUiTable.row();
        shadUiLink.setColor(app.purpleLinkColor);
        shadUiLink.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.net.openURI("https://ray3k.wordpress.com/");
            }
        });

        shadUiTable.add(shadUiLink);
        scrollTable.add(shadUiTable);

        scrollTable.row();

        //Credits End
        mainTable.row();

        //Add Back Button
        TextButton backBtn = new TextButton("Back", skin, "round");
        mainTable.add(backBtn).colspan(2);

        backBtn.addListener(new ClickListener(){ // Back Button
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.switchScreen(GameStart.SETTINGSCREEN);
            }
        });
    }
}
