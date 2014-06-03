package com.tomashernandez.alienshooter;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.tomashernandez.alienshooter.game.GameClass;

public class MainActivity extends AndroidApplication {

	@Override	
	public void onCreate (android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useAccelerometer = true;
		initialize(new GameClass(), cfg);		// initialize a new instance of your Menu, but doesn't work as of 5/30
											//(will change to the menu eventually) class
	}
	
}