package com.tomashernandez.alienshooter;

import com.badlogic.gdx.backends.android.AndroidApplication;

public class MainActivity extends AndroidApplication {

	@Override	
	public void onCreate (android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialize(new Game(), true);		// initialize a new instance of your Game
											//(will change to the menu eventually) class
	}
	
}
