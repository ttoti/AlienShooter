package com.tomashernandez.alienshooter.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.tomashernandez.alienshooter.game.*;

public class MainMenu implements Screen {
	
	final GameClass game;
	OrthographicCamera camera;
	

	public MainMenu(final GameClass gam) {
		game = gam;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		game.font.scale(1f);
		game.font.setColor(Color.WHITE);
	}
	@Override
	public void render(float arg0) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 0.5f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.setProjectionMatrix(camera.combined);
		camera.update();
		game.batch.begin();
		game.font.draw(game.batch, "Welcome to Alien Invaders!", 100, 150);
		game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
		game.batch.end();
		
		if(Gdx.input.isTouched()){
			game.setScreen(new Game(game));
			dispose();
		}	
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	public void resume() {
		// TODO Auto-generated method stub
		
	}

	public void show() {
		// TODO Auto-generated method stub
		
	}
}
