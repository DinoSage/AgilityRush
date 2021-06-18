package io.github.dinosage.agilityrush.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.dinosage.agilityrush.util.KeyValue;

public class AppStorage {

    //Keeps Track of Values / Constants & App Preferences
    private static AppStorage instance = null;

    //Miscellaneous
    public Color color = new Color(0f, 0f, 0f, 1);
    public Color blackColor = new Color(0f, 0f, 0f, 1);
    public Color greyColor = new Color(0.058f, 0.058f, 0.058f, 1f);
    public Color purpleLinkColor = new Color(0.674f, 0.415f, 0.815f, 1f);//0.67f, 0.06f, 0.82f, 1f

    //Score Related Variables
    public int SCORE = 0; //The current score of the user during the game
    public int TEMPSCORE = 0;
    public int INCREMENT = 0; //Each time the obs increment has been reached and faster obstacles must be generated.
    public int OBSINCREM = 10; //The number of obstacles needed to pass to increment speed

    //Boolean Variables General
    public boolean PAUSE = true;
    public boolean GAMEOVER = false;
    public boolean SKIN = false; //True for Shade, False for Glassy

    //Obstacle Constants and Numbers
    public Vector2 SPAWNPOS = new Vector2(125f, -5f);
    public float OBSSPEEDX = -40f;
    public final float MAXSPEEDX = -50f;

    public float SPAWNDIST = 100f;
    public final float FLOORDIST = 40f;
    public float TEMPDIST = SPAWNDIST;
    public final float RESETDIST= 100f;
    private final float DECREASERATE = 0.80f;

    public float GROUNDHEIGHT = 0;

    //Sprite Values
    public Animation<TextureRegion> running = null;


    //Variables related to preferences
    public KeyValue<Integer> HighScore = new KeyValue<>("highscore", 0);
    public KeyValue<Boolean> DEBUG = new KeyValue<>("debug", false);
    public KeyValue<Boolean> MusicState = new KeyValue<>("musicstate", true);
    public KeyValue<Float> MusicVolume = new KeyValue<>("musicvolume", 1f);
    public KeyValue<String> MusicSelection = new KeyValue<>("musicselection", "Music/Into The Game - SilentCrafter.mp3");
    public KeyValue<Boolean> SoundState = new KeyValue<>("soundstate", true);
    public KeyValue<Float> SoundVolume = new KeyValue<>("soundvolume", 1f);

    //String paths / strings in general
    public static final String skinPath = "shade-ui/uiskin.json";

    //Gets instance of app storage
    public static AppStorage getInstance(){
        if(instance == null){
            instance = new AppStorage();
        }
        return instance;
    }

    //Empty constructor for singleton class
    private AppStorage(){
    }

    /////////////////////////////////////////////////////////////////////////////
    //Preferences Half of App Storage

    //File path / name for the preferences
    static final String DATASTORAGE = "basicGdxPrefs";

    //The Preferences
    Preferences prefs;


    //Gets / Sets Preferences
    public void getPreferences(){
        prefs = Gdx.app.getPreferences(DATASTORAGE);
    }

    //Methods To Pull & Write Data from Preferences
    //High Score Preference
    void readHighScore(){
        if(prefs.contains(HighScore.getKey())){
            int value = prefs.getInteger(HighScore.getKey());
            HighScore.setValue(value);
        } else {
            writeHighScore();
        }
    }
    public void writeHighScore(){
        prefs.putInteger(HighScore.getKey(), HighScore.getValue());
        prefs.flush();
    }

    //Debug Preference
    void readDebug(){
        if(prefs.contains(DEBUG.getKey())){
            boolean value = prefs.getBoolean(DEBUG.getKey());
            DEBUG.setValue(value);
        } else {
            writeDebug();
        }
    }
    public void writeDebug(){
        prefs.putBoolean(DEBUG.getKey(), DEBUG.getValue());
        prefs.flush();
    }

    //Music State
    void readMusicState(){
        if(prefs.contains(MusicState.getKey())){
            boolean value = prefs.getBoolean(MusicState.getKey());
            MusicState.setValue(value);
        } else {
            writeMusicState();
        }
    }
    public void writeMusicState(){
        prefs.putBoolean(MusicState.getKey(), MusicState.getValue());
        prefs.flush();
    }

    //Music volume
    void readMusicVolume(){
        if(prefs.contains(MusicSelection.getKey())){
            float value = prefs.getFloat(MusicVolume.getKey());
            MusicVolume.setValue(value);
        } else {
            writeMusicVolume();
        }
    }
    public void writeMusicVolume(){
        prefs.putFloat(MusicVolume.getKey(), MusicVolume.getValue());
        prefs.flush();
    }

    //Music Selections
    void readMusicChoice(){
        if(prefs.contains(MusicSelection.getKey())){
            String value = prefs.getString(MusicSelection.getKey());
            MusicSelection.setValue(value);
        } else {
            writeMusicChoice();
        }
    }
    public void writeMusicChoice(){
        prefs.putString(MusicSelection.getKey(), MusicSelection.getValue());
        prefs.flush();
    }

    //Sound State
    void readSoundState(){
        if(prefs.contains(SoundState.getKey())){
            boolean value = prefs.getBoolean(SoundState.getKey());
            SoundState.setValue(value);
        } else {
            writeSoundState();
        }
    }
    public void writeSoundState(){
        prefs.putBoolean(SoundState.getKey(), SoundState.getValue());
        prefs.flush();
    }

    //Sound Volume
    void readSoundVolume(){
        if(prefs.contains(SoundVolume.getKey())){
            float value = prefs.getFloat(SoundVolume.getKey());
            SoundVolume.setValue(value);
        } else {
            writeSoundVolume();
        }
    }
    public void writeSoundVolume(){
        prefs.putFloat(SoundVolume.getKey(), SoundVolume.getValue());
        prefs.flush();
    }

    //Miscellaneous Methods

    //Updates the High Score Value
    public void load(){
        getPreferences();
        readHighScore();
        readDebug();
        readMusicState();
        readMusicVolume();
        readMusicChoice();
        readSoundState();
        readSoundVolume();
    }

    public void calculateHighScore(){
        if(SCORE > HighScore.getValue()){
            HighScore.setValue(SCORE);
            writeHighScore();
        }
    }

    public void generateSpawnDist(){
        TEMPDIST = (int) (Math.random()*20+(SPAWNDIST-10));
    }

    public void decreaseDist(){
        if(Math.round(SPAWNDIST*DECREASERATE) > FLOORDIST){
            SPAWNDIST = Math.round(SPAWNDIST*DECREASERATE);
        } else {
            SPAWNDIST = FLOORDIST;
        }
    }
}
