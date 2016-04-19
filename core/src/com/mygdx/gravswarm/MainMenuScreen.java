package com.mygdx.gravswarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Vlad M on 4/16/16.
 */
public class MainMenuScreen implements Screen
{
    final GravSwarm game;
    OrthographicCamera camera;

    Texture appLogo;
    Texture button1_Start;
    Texture button2_Instructions;
    Texture button3_Settings;
    Texture button4_Exit;

    public MainMenuScreen(final GravSwarm thisGame)
    {
        game = thisGame;

        camera = new OrthographicCamera();
        //     camera.setToOrtho(false);
        camera.setToOrtho(false, 1280, 720);

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

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        //game.font.draw(game.batch, "Test", 100, 150);
        game.batch.draw(appLogo, 120, 190);
        game.batch.draw(button1_Start, 600, 450);
        game.batch.draw(button2_Instructions, 600, 360);
        game.batch.draw(button3_Settings, 600, 270);
        game.batch.draw(button4_Exit, 600, 180);

        game.batch.end();


        // defining our buttons location in the 3D space
        Rectangle Start_button = new Rectangle(600, 450, button1_Start.getWidth(), button1_Start.getHeight());
        Rectangle Instructions_button = new Rectangle(600, 360, button2_Instructions.getWidth(), button2_Instructions.getHeight());
        Rectangle Settings_button = new Rectangle(600, 270, button3_Settings.getWidth(), button3_Settings.getHeight());
        Rectangle Exit_button = new Rectangle(600, 180, button4_Exit.getWidth(), button4_Exit.getHeight());

        Gdx.input.setCatchBackKey(true);

        // if the screen is touched, do:
        if (Gdx.input.isTouched())
        {
            Vector3 tmp = new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0);
            camera.unproject(tmp);

            // if the start button is pressed, go to the Game screen
            if (Start_button.contains(tmp.x, tmp.y))
            {
                game.setScreen(new GameScreen(game));
                dispose();
            }
            // if the instructions button is pressed, go to the Instructions screen
            if (Instructions_button.contains(tmp.x, tmp.y))
            {
                game.setScreen(new InstructionsScreen(game));
                dispose();
            }
            // if the settings button is pressed, go to the Settings screen
            if (Settings_button.contains(tmp.x, tmp.y))
            {
                game.setScreen(new SettingsScreen(game));
                dispose();
            }
            // if the exit button is pressed, exit the app
            if (Exit_button.contains(tmp.x, tmp.y))
            {
                dispose();
                System.exit(1);
            }
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
