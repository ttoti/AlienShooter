package com.tomashernandez.alienshooter.Menu;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.tomashernandez.alienshooter.AlienInvaders;
import com.tomashernandez.alienshooter.Game;



public class MenuScreen extends AbstractScreen {
	private static final float BUTTON_WIDTH = 300f;
	private static final float BUTTON_HEIGHT = 60f;
	private static final float BUTTON_SPACING = 10f;

	public MenuScreen(AlienInvaders game) {
		super(game);
	}
	public void resize(int width, int height){
		Skin skin = super.getSkin();
		super.resize (width, height);
		final float buttonX = (width - BUTTON_WIDTH) / 2;
		float currentY = 280f;
		
		//Title
		Label title = new Label("Alien Invaders", skin);
		title.setX(((width - title.getWidth()) / 2 ));
		title.setY((currentY + 100));
		stage.addActor(title);
		
		//Button "start game"
		TextButton startGame = new TextButton("Start game", skin);
		startGame.setX(buttonX);
		startGame.setY(currentY);
		startGame.setWidth(BUTTON_WIDTH);
		startGame.setHeight(BUTTON_HEIGHT);
		stage.addActor(startGame);
		
		//Button "options"
		TextButton optionsButton = new TextButton("Options", skin);
		optionsButton.setX(buttonX);
		optionsButton.setY(currentY -= BUTTON_HEIGHT + BUTTON_SPACING);
		optionsButton.setWidth(BUTTON_WIDTH);
		optionsButton.setHeight(BUTTON_HEIGHT);
		stage.addActor(optionsButton);
		
		
	}

}
