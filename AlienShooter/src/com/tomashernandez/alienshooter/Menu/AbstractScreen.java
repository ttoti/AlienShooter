package com.tomashernandez.alienshooter.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tomashernandez.alienshooter.AlienInvaders;
import com.tomashernandez.alienshooter.Game;

public class AbstractScreen implements Screen {
	protected final Stage stage;
	protected final AlienInvaders game;
	
	private Skin skin;

	public AbstractScreen(AlienInvaders game2){
		this.game = game2;
		this.stage = new Stage(Game.CAMERA_HEIGHT, Game.CAMERA_WIDTH, false){
			@Override
			public boolean keyDown(int keyCode){
				if(keyCode == Keys.BACK || keyCode == Keys.ESCAPE){
					if ( backClicked() ){
						return true;
					} else {
						Gdx.app.exit();
					}
					
				}
				return true;
			}
		};
		Gdx.input.setInputProcessor(this.stage);
		Gdx.input.setCatchBackKey(true);
	}
	
	protected SpriteBatch getBatch() {
		return (SpriteBatch) this.stage.getSpriteBatch();
	}
	
	protected Skin getSkin(){
		if(skin == null){
			skin = new Skin(Gdx.files.internal("uiskin.json"));
		}
		return skin;
	}
	
	protected boolean backClicked(){
		return false;
	}
	
	protected String getName(){
		return getClass().getSimpleName();
	}
	
	@Override
	public void dispose() {
		this.stage.dispose();
		
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
	public void render(float arg0) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.act( arg0 );
		stage.draw();
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public Stage getStage(){
		return this.stage;
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this.stage);
		
	}

}
