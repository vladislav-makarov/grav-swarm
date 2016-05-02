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
public class InstructionsScreen implements Screen
{
    final GravSwarm game;

    OrthographicCamera camera;
    Vector3 touchPos;
    Texture Instructions_header;
    Texture Instructions_text;
    Texture appBackground;
    Texture backButton;

    // defining BackButton's location in the 3D space (value #3 = button's width, value #4 = button's height)
    Rectangle RBackButton = new Rectangle(0, 720-86, 100, 86);


    public InstructionsScreen (final GravSwarm thisGame)
    {
        this.game = thisGame;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);

        touchPos = new Vector3();

        Instructions_header = new Texture("instructions.png");
        Instructions_text = new Texture("instructions-text.png");
        appBackground = new Texture("test_background.png");
        backButton = new Texture("backButton.png");
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
        game.batch.draw(Instructions_header, 0, 720-86);
        game.batch.draw(Instructions_text, 1280/2 - 1062/2, 25);
        game.batch.draw(backButton, 0, 720-86);

        game.batch.end();


        // allows us to return to the main menu
        Gdx.input.setCatchBackKey(true);

        // if the screen is touched, do:
        if (Gdx.input.isTouched())
        {
            Vector3 tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(tmp);

            // if on-screen back button is pressed
            if (RBackButton.contains(tmp.x, tmp.y)) {
                Gdx.input.vibrate(20);
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.BACK))
        {
            Gdx.input.vibrate(20);
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
        appBackground.dispose();
    }
}
