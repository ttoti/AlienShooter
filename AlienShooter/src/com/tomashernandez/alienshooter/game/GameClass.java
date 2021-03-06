package com.tomashernandez.alienshooter.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tomashernandez.alienshooter.menu.MainMenu;

public class GameClass extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		setScreen(new MainMenu(this));
		
	}
	public void render(){
		super.render();
	}
}
