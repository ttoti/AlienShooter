package com.tomashernandez.alienshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.tomashernandez.alienshooter.Menu.MenuScreen;
public class AlienInvaders extends Game {
	public static float WIDTH = 480;
	public static float HEIGHT = 800;
	
	public AlienInvaders(){
		super();
	}
	@Override
	public void create() {
		Texture.setEnforcePotImages(false);
		showMenu();
		
	}
	public void showMenu(){
		setScreen(new MenuScreen(this));
	}
	public void resize(int width, int height){
		
	}
	public void render(){
		super.render();
	}
	public void pause(){	
	}
	public void resume(){
		
	}
	public void dispose(){
		getScreen().dispose();
	}
	
}
