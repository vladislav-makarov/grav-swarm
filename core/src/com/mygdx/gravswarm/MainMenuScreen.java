package com.mygdx.gravswarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Vlad M on 4/16/16.
 */
public class MainMenuScreen implements Screen
{
    final GravSwarm game;
    //OrthographicCamera camera;

    Texture appLogo;
    Texture button1_Start;
    Texture button2_Instructions;
    Texture button3_Settings;
    Texture button4_Exit;

    public MainMenuScreen(final GravSwarm thisGame)
    {
        game = thisGame;

        //camera = new OrthographicCamera();
        //     camera.setToOrtho(false);
        //camera.setToOrtho(false, 800, 480);

        appLogo = new Texture("logo1.png");
        button1_Start = new Texture("1-start.png");
        button2_Instructions = new Texture("2-instructions.png");
        button3_Settings = new Texture("3-settings.png");
        button4_Exit = new Texture("4-exit.png");
    }

    @Override
    public void show()
    {

    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //camera.update();

        //game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        //game.font.draw(game.batch, "Test", 100, 150);
        game.batch.draw(appLogo, 120, 190);
        game.batch.draw(button1_Start, 600, 450);
        game.batch.draw(button2_Instructions, 600, 360);
        game.batch.draw(button3_Settings, 600, 270);
        game.batch.draw(button4_Exit, 600, 180);

        game.batch.end();

        // if the screen is touched, do:
        // ** this code is temporary, it needs to be changed
        // ** to handle all the specific button presses
        if (Gdx.input.isTouched())
        {
            game.setScreen(new GameScreen(game));
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
    public void resume() {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {
        appLogo.dispose();
        button1_Start.dispose();
        button2_Instructions.dispose();
        button3_Settings.dispose();
        button4_Exit.dispose();
    }
}
