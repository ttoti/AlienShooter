package com.tomashernandez.alienshooter.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.tomashernandez.alienshooter.game.*;


public class MainMenu extends ScreenAdapter {
	
	GameClass game;
	OrthographicCamera camera;
	Rectangle playBounds;
	Rectangle aboutBounds;
	Texture bg;
	Vector3 touchPoint;


	public MainMenu(GameClass gam) {
		game = gam;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 480, 800);
		game.font.scale(1f);
		game.font.setColor(Color.WHITE);
		playBounds = new Rectangle(160 - 150, 200 + 18, 300, 36);
		touchPoint = new Vector3();
	}
	@Override
	public void render(float arg0) {
		update();
		draw();
	}
	
	public void update(){
		if (Gdx.input.justTouched()){
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			
			if (playBounds.contains(touchPoint.x, touchPoint.y)){
				game.setScreen(new Game(game));
				return;
			}
		}
	}
	
	public void draw(){
		bg = new Texture(Gdx.files.internal("images/spaceBG.png"));
		Gdx.gl.glClearColor(1f, 1f, 1f, 0.5f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		game.batch.begin();
		game.batch.draw(bg, 0, 0, 480, 800);
		game.batch.end();
		
		game.batch.begin();
		game.font.draw(game.batch, "Welcome to Alien Invaders!", 75, 450);
		game.font.draw(game.batch, "Hold down anywhere to begin!", 60, 400);
		game.batch.end();
	}

	@Override
	public void dispose() {
		bg.dispose();
		
	}
}