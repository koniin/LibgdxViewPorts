package com.bloodcake.viewporttests;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ViewPortTests extends Game {
	@Override
	public void create () {
        setScreen(new TwoCamTest());
        //setScreen(new ViewPortTestScreen());
	}
}
