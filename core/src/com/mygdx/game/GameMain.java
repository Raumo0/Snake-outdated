package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.mygdx.game.states.GameStateManager;
import com.mygdx.game.states.MenuState;

import java.security.Key;

public class GameMain extends Game {
	public static final int WIDTH = 600;
	public static final int HEIGHT = 450;

	public static final String TITLE = "Flappy Demo";

	private GameStateManager gsm;
	private SpriteBatch batch;

	private Music music;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
//		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
//		music.setLooping(true);
//		music.setVolume(0.1f);
//		music.play();
		Gdx.gl.glClearColor(1, 1, 1, 0);
		gsm.push(new MenuState(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
			System.exit(0);
	}

	@Override
	public void dispose() {
		super.dispose();
		music.dispose();
	}
}