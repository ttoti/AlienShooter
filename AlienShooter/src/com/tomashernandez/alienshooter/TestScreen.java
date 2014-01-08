package com.tomashernandez.alienshooter;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class TestScreen implements ApplicationListener {
	private Stage stage;
	private TextButton button;
	@Override
	public void create() {
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		stage = new Stage();
		button = new TextButton("Click me!", skin);
		stage.addActor(button);
		Gdx.input.setInputProcessor(stage);
		
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	@Override
	public void resize(int arg0, int arg1) {
		button.setPosition(
                (button.getWidth())/2, 
                (button.getHeight())/2);
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

}
