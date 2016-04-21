package com.mygdx.gravswarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Vlad M on 4/18/16.
 */
public class InstructionsScreen implements Screen
{
    final GravSwarm game;

    OrthographicCamera camera;
    Vector3 touchPos;
    Texture Instructions_header;
    Texture Instructions_text;
    //BitmapFont font;

    Rectangle testLogo;

    public InstructionsScreen (final GravSwarm thisGame)
    {
        this.game = thisGame;

        camera = new OrthographicCamera();
        //     camera.setToOrtho(false);
        camera.setToOrtho(false, 1280, 720);

        touchPos = new Vector3();

        Instructions_header = new Texture("instructions.png");
        Instructions_text = new Texture("instructions-text.png");

        //font = new BitmapFont();
        //font.setColor(Color.DARK_GRAY);
        //font.getData().setScale(2);
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
        game.batch.draw(Instructions_header, 0, 720-77);
        game.batch.draw(Instructions_text, 1280/2 - 900/2, 80);
        //font.draw(game.batch, " This is test of line #1 \n Hi, I'm line #2 \n nothing here... \n Line #4", 320, 170);

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
        Instructions_header.dispose();
        Instructions_text.dispose();
    }
}
