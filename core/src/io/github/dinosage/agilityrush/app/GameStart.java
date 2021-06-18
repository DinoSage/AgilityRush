package io.github.dinosage.agilityrush.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import io.github.dinosage.agilityrush.game.GameScreen;
import io.github.dinosage.agilityrush.screens.*;

public class GameStart extends Game {

	public static final int HOMESCREEN = 1;
	public static final int GAMESCREEN = 2;
	public static final int SCORESCREEN = 3;
	public static final int SETTINGSCREEN = 4;
	public static final int CREDITSSCREEN = 5;
	public static final int HOWTOPLAYSCREEN = 6;

	public static int CURRENT = 0;

	HomeScreen homeScreen;
	GameScreen gameScreen;
	LoadingScreen loadingScreen;
	ScoreScreen scoreScreen;
	SettingsScreen settingScreen;
	CreditsScreen creditsScreen;
	HowToPlayScreen howToPlayScreen;

	AppStorage app;

	@Override
	public void create () {
		app = AppStorage.getInstance();
		app.load();
		//loadingScreen = new LoadingScreen(this);
		//this.setScreen(loadingScreen);
		switchScreen(HOMESCREEN);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(app.color.r, app.color.g, app.color.b, app.color.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}
	
	@Override
	public void dispose () {
	}

	//Method to switch screens / called by other classes
	public void switchScreen(int screen){
		//If switching screens and currently not on loading screen, then go to loading screen for asset loading and unloading
		if(this.screen instanceof LoadingScreen){
			switch(screen){
				case HOMESCREEN:
					CURRENT = HOMESCREEN;
					if(homeScreen == null)
						homeScreen = new HomeScreen(this);
					this.setScreen(homeScreen);
					break;
				case GAMESCREEN:
					CURRENT = GAMESCREEN;
					if(gameScreen == null)
						gameScreen = new GameScreen(this);
					this.setScreen(gameScreen);
					break;
				case SCORESCREEN:
					CURRENT = SCORESCREEN;
					if(scoreScreen == null)
						scoreScreen = new ScoreScreen(this);
					this.setScreen(scoreScreen);
					break;
				case SETTINGSCREEN:
					CURRENT = SETTINGSCREEN;
					if(settingScreen == null)
						settingScreen = new SettingsScreen(this);
					this.setScreen(settingScreen);
					break;
				case CREDITSSCREEN:
					CURRENT = CREDITSSCREEN;
					if(creditsScreen == null)
						creditsScreen = new CreditsScreen(this);
					this.setScreen(creditsScreen);
					break;
				case HOWTOPLAYSCREEN:
					CURRENT = HOWTOPLAYSCREEN;
					if(howToPlayScreen == null)
						howToPlayScreen = new HowToPlayScreen(this);
					this.setScreen(howToPlayScreen);
					break;
			}
		} else {
			if(loadingScreen == null)
				loadingScreen = new LoadingScreen(this);
			loadingScreen.SCREENTOGO = screen;
			this.setScreen(loadingScreen);
		}

	}
}
