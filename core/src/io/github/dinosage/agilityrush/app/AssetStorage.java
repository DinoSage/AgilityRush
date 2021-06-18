package io.github.dinosage.agilityrush.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

//This singleton class keeps track of all assets and manages them
public class AssetStorage {

    //Variables
        //The singleton instance
    private static AssetStorage instance = null;

        //The main asset manager that loads and unloads assets
    public final AssetManager manager;

        //Asset Storage / Manager loading states
    public boolean render = false;
    public boolean loadingDone = false;

    //String variables
    public final Array<String> allMusicPaths = new Array<>();
    public final Array<String> allMusicNames = new Array<>();
    public Array<String> soundPaths = new Array<>();


    //Instance variables of certain / current assets - Main Variables
    public Music music;
    public Skin skin;
    public Array<Sound> sounds = new Array<>();


    //Gets Instance of asset storage
    public static AssetStorage getInstance(){
        if(instance == null)
            instance = new AssetStorage();
        return instance;
    }

    //The private constructor of singleton & setup of class
    private AssetStorage(){
        manager = new AssetManager();
        soundSetup();
        musicSetup();
    }

    //Should always be rendering for screens that implement this
    public void render(){
        manager.update();
        if(manager.isFinished()){
            render = false;
            loadingDone = true;
        }
    }

    //Additional Methods----------------

    void soundSetup(){
        soundPaths.add("Sounds/Game Jump - ZapSplat.mp3");
        soundPaths.add("Sounds/Whoosh Sound - ZapSplat.mp3");
    }

    public Sound getSoundByName(String name){
        for(String path : soundPaths){
            if(path.contains(name)){
                return (Sound) get(path);
            }
        }
        return null;
    }


    void musicSetup(){
        allMusicPaths.add("Music/Into The Game - SilentCrafter.mp3");
        allMusicPaths.add("Music/Lights - SilentCrafter.mp3");
        allMusicPaths.add("Music/Power - SilentCrafter.mp3");

        for(String path : allMusicPaths){
            allMusicNames.add(path.substring(path.indexOf("/")+1, path.indexOf("-")-1));
        }
    }

    public String getMusicName(String musicPath){
        for(String name : allMusicNames){
            if(musicPath.contains(name)){
                return name;
            }
        }
        return null;
    }

    public String getMusicPath(String musicName){
        for(String path : allMusicPaths){
            if(path.contains(musicName)){
                return path;
            }
        }
        return null;
    }

    //Load Methods----------------------

    //Method for loading the assets for "loading"
    public void loadLoading(){
        manager.load("Loading Symbol.png", Texture.class);
        manager.load(AppStorage.skinPath, Skin.class);
        render = true;
    }

    //Method for loading the sprite assets
    public void loadAssets(){
        //TextureAtlas atlas = new TextureAtlas("Adventurer/Adventurer.txt");
        //Animation<TextureRegion> running = new Animation<TextureRegion>(1/60f, getRegions("adventurer-run", "Adventurer/Adventurer.txt"), Animation.PlayMode.LOOP);
        //AssetStorage.getInstance().running = running;
    }

    //Queues the assets to be rendered
    public boolean queue(String file, Class type){
        if(Gdx.files.internal(file).exists()){
            manager.load(file, type);
            return true;
        } else {
            return false;
        }
    }

    //Sets the manager to start loading
    public void load(){
        render = true;
    }

    //Getter method for accessing assets by file path
    public <type> Object get(String file){
        if(manager.isLoaded(file)){
            return manager.get(file);
        } else {
            return null;
        }
    }

    //To unload an asset and save space
    public void unload(String file){
        if(manager.isLoaded(file)){
            manager.unload(file);
        }
    }

    //Private Methods
    private Array<TextureRegion> getRegions(String commonFileName, String atlasName){
        TextureAtlas atlas = new TextureAtlas(atlasName);
        Array<TextureAtlas.AtlasRegion> regions = atlas.getRegions();

        ArrayList<TextureRegion> textures = new ArrayList<>();
        for(TextureAtlas.AtlasRegion region : regions){
            if(region.name.contains(commonFileName)){
                textures.add(new TextureRegion(region.getTexture()));
            }
        }

        TextureRegion[] arr= textures.toArray(new TextureRegion[0]);
        Array<TextureRegion> textureRegions = new Array<>(arr);
        return textureRegions;
    }
}
