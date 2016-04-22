package com.mygdx.gravswarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Vlad M on 4/18/16.
 */
public class SettingsScreen implements Screen
{
    final GravSwarm game;

    OrthographicCamera camera;
    Vector3 touchPos;
    Texture Settings_header;
    Texture appBackground;

    public SettingsScreen (final GravSwarm thisGame)
    {
        this.game = thisGame;

        camera = new OrthographicCamera();
        //     camera.setToOrtho(false);
        camera.setToOrtho(false, 1280, 720);

        touchPos = new Vector3();

        Settings_header = new Texture("settings.png");
        appBackground = new Texture("test_background.png");
    }

    @Override
    public void show()
    {

    }

    @Override
    public void render (float delta)
    {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(appBackground, 0, 0);
        game.batch.draw(Settings_header, 0, 720-86);
        game.batch.end();

        // allows us to return to the main menu
        Gdx.input.setCatchBackKey(true);

        if (Gdx.input.isKeyPressed(Input.Keys.BACK))
        {
            Gdx.input.vibrate(25);
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height)
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {
        Settings_header.dispose();
        appBackground.dispose();
    }
}
