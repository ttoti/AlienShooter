package com.tomashernandez.alienshooter.game;

import java.util.Iterator;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.tomashernandez.alienshooter.background.*;


public class Game implements ApplicationListener{

 	public static final int CAMERA_WIDTH = 480;
	public static final int CAMERA_HEIGHT = 800;
	public static final int GAME_PAUSED = 0;
	
    Input vibrate;
	Background background;
	Music backgroundMusic;
	Sound liveUpSound, levelUpSound, hitSound, shootSound;
	Texture ship, alien, bullet;
	Rectangle rect_ship, rect_alien, rect_bullet;
	Array<Bullet2D> bullets;
	Array<Rectangle> aliens;
	OrthographicCamera camera;
	SpriteBatch batch;
	BitmapFont font, goFont;
	TextureRegion bgRegion;
	long startTime, lastShotTime, lastAlienTime;
	int score, lives, difficultyMultiplier, levelUp, bulletsPresent;
	private boolean gameOver;
	int gamestatus;
	
	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.scale(1f);
		font.setColor(Color.WHITE);
		goFont = new BitmapFont();
		goFont.scale(2f);
		goFont.setColor(Color.WHITE);
		defaultValues();
		createSprites();
		startSounds();
	}

	//Disposes when not being used
	@Override
	public void dispose() {
		ship.dispose();
		bullet.dispose();
		alien.dispose();
		hitSound.dispose();
		background.dispose();
		levelUpSound.dispose();
		shootSound.dispose();
		backgroundMusic.dispose();
		liveUpSound.dispose();
	}
	
	public  void pauseGame(){
		gamestatus = GAME_PAUSED;
	}
	
	@Override
	public void pause() {
		pauseGame();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1f, 1f, 1f, 0.5f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		//Renders the background
		background.render(1);
		camera.update();
		backgroundMusic.play();
		
		if(gameOver){
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			goFont.draw(batch, "Game Over!", CAMERA_WIDTH / 2 - goFont.getBounds("Game Over!").width / 2, CAMERA_HEIGHT / 2 + 100);
			font.draw(batch, "Score : " + score, CAMERA_WIDTH / 2 - font.getBounds("Score : XX").width / 2, CAMERA_HEIGHT / 2);
			goFont.draw(batch,"Tap to restart", CAMERA_WIDTH / 2 - goFont.getBounds("Tap to restart").width / 2, CAMERA_HEIGHT / 2 - 100 );
			batch.end();
			
			//When screen is touched when game over screen appears
			if(Gdx.input.isTouched() ) {
				defaultValues();
			}			
		}else{
			// Render 
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			font.draw(batch, "Level: " + difficultyMultiplier, 17, CAMERA_HEIGHT - 10);
			font.draw(batch, "Score: " + score, 10, CAMERA_HEIGHT - 40);
			font.draw(batch, "Lives: " + lives, CAMERA_WIDTH - 120, CAMERA_HEIGHT - 10);
			for(Rectangle b : aliens){
				batch.draw(alien, b.x, b.y);
			}
			for(Bullet2D b : bullets){
				batch.draw(bullet, b.rect.x, b.rect.y);
			}
			batch.draw(ship, rect_ship.x, rect_ship.y);
			batch.end();
			
			Iterator<Bullet2D> iter = bullets.iterator();
			while(iter.hasNext()){
				
				//When bullet goes past camera boundaries
				Bullet2D b = iter.next();
				b.rect.y += Gdx.graphics.getDeltaTime() * b.slope.y;
				b.rect.x += Gdx.graphics.getDeltaTime() * b.slope.x;
				if(b.rect.y + 16 < 0){
					iter.remove();
					bulletsPresent--;
				}else{
					//When bullet hits an alien
					Iterator<Rectangle> iterb = aliens.iterator();
					while(iterb.hasNext()){
						Rectangle baddie = iterb.next();
						if(baddie.overlaps(b.rect)){
							hitSound.play();
							iterb.remove();
							if (bulletsPresent > 0) {
								iter.remove();
								bulletsPresent--;
							}
							score++;
							levelUp++;
							
							//In charge of multiplier and lives
							
							//If the score is over 40, it creates a little more balance in the game
							//So it doesn't spam with level ups. The lives go up every 10 kills
							//That's where the is a modulus 10
							if(score >= 40 && (levelUp % 10) == 0){
								lives++;
								liveUpSound.play();
							}
							//When 
							else if(levelUp == 10 &&  score <= 30){
								lives++;
								liveUpSound.play();
								difficultyMultiplier++;
								levelUpSound.play();
								levelUp = 0;
							//When levelUp hits 40, after score of 30
							}
							if(levelUp == 40){
								difficultyMultiplier++;
								levelUpSound.play();
								levelUp = 0;
							}
						}
					}
				}
			}
			
			Iterator<Rectangle> iterb = aliens.iterator();
			while(iterb.hasNext()){
				Rectangle b = iterb.next();
				//Direction aliens move as well as the aliens move faster
				b.y -= Gdx.graphics.getDeltaTime() * (200 + ( (TimeUtils.millis() - startTime )/ 1000) );
				//When alien goes past you
				if(b.y + 128 < rect_ship.y){
					iterb.remove();
					lives--;
					//Vibrate is in milliseconds
					Gdx.input.vibrate(250);
					if(lives <= 0)
						gameOver = true;
				}
			}
			//Spawn Aliens
			if(TimeUtils.nanoTime() - lastAlienTime > 3000000000l){
				for(int x = 0; x < difficultyMultiplier; x++){
					spawnAlien();
				}
			}
			
			//Input
			if(Gdx.input.isTouched() ) {
				Vector3 touchPos = new Vector3();
				touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
				camera.unproject(touchPos);
				if(TimeUtils.nanoTime() - lastShotTime > 200000000l){				
					spawnBullet(touchPos.x, touchPos.y);
				}
			}
		}
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	private void spawnBullet(float dirX, float dirY ){
		Bullet2D b = new Bullet2D();
		
		Rectangle rect_b = new Rectangle();
		rect_b.width = 16;
		rect_b.height = 16;		
		rect_b.x = (CAMERA_WIDTH / 2) - (rect_b.width / 2);
		rect_b.y = (100) - (rect_ship.height);
		b.rect = rect_b;
		
		Vector2 slope = new Vector2((dirX - rect_b.x), (dirY - rect_b.y));
		slope.clamp(500, 500);
		
		b.slope = slope;
		
		bullets.add(b);
		bulletsPresent++;
		shootSound.play();
		lastShotTime = TimeUtils.nanoTime();
	}
	//Where the alien spawns as well as the size of it
	private void spawnAlien(){
		Rectangle b = new Rectangle();
		b.x = MathUtils.random(0, CAMERA_WIDTH - 64);
		b.y = MathUtils.random(CAMERA_HEIGHT - 64, CAMERA_HEIGHT);
		b.width = 100;
		b.height = 100;		
		aliens.add(b);		
		lastAlienTime = TimeUtils.nanoTime();	
	}
	//Creates the images on the screen
	public void createSprites(){
		
		//ship sprite
		ship = new Texture(Gdx.files.internal("images/ship.png"));
		rect_ship = new Rectangle();
		rect_ship.width = 64;
		rect_ship.height = 64;
		rect_ship.x = (CAMERA_WIDTH / 2) - (rect_ship.width / 2) - 25;
		rect_ship.y = (CAMERA_HEIGHT) - (rect_ship.height) - 750;				
		
		//Bullet sprite
		bullet = new Texture(Gdx.files.internal("images/bullet.png"));		
		
		//Alien sprite 
		alien = new Texture(Gdx.files.internal("images/alien.png"));
		
		//Background
		Texture bgTexture = new Texture(Gdx.files.internal("images/spaceBG.png"));
		bgRegion = new TextureRegion(bgTexture);
		//First vector2 is parallax ratio. I only have the value in Y cause it makes sense to the game.
		//Second vector2 is the start position
		background = new Background(new ParallaxLayer[]{
		new ParallaxLayer(bgRegion, new Vector2(0, .0055f + ((TimeUtils.millis() - startTime )/ 1000)), new Vector2(0, 0))
		}, 480, 800, new Vector2(0, 350));
	}
	
	//In charge of declaring sounds
	public void startSounds(){
		//Sounds
		hitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/shoot.ogg"));
		
		levelUpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/levelUp.wav"));
		levelUpSound.setVolume(0, 0.75f);
				
		liveUpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/liveUp.ogg"));
				
		shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/shootSound.mp3"));
				
		//Background music
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/backgroundMusic.mp3"));
		backgroundMusic.setLooping(true);
		backgroundMusic.setVolume(0.6f);
			
		
	}
	
	public void defaultValues(){
		levelUp = 0;
		difficultyMultiplier = 1;
		score = 0;
		lives = 5;
		gameOver = false;
		startTime = TimeUtils.millis();
		aliens = new Array<Rectangle>();
		bullets = new Array<Bullet2D>();
		bulletsPresent = 0;
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
}