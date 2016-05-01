package com.mygdx.gravswarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by Vlad M on 4/18/16.
 */
public class SettingsScreen extends Settings implements TextInputListener, Screen
{
    final GravSwarm game;

    Stage stage;
    Skin skin;
    SpriteBatch batch;

    OrthographicCamera camera;
    Vector3 touchPos;
    Texture Settings_header;
    Texture appBackground;

    public SettingsScreen (final GravSwarm thisGame)
    {
        create();

        this.game = thisGame;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);

        touchPos = new Vector3();

        Settings_header = new Texture("settings.png");
        appBackground = new Texture("test_background.png");
    }

    /*=================================================== NEW CODE ======================================================*/
    public void create()
    {
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
        // recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
        skin = new Skin();
        // Generate a 1x1 white texture and store it in the skin named "white".
        Pixmap pixmap = new Pixmap(100, 100, Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();

        skin.add("white", new Texture(pixmap));

        // Store the default libgdx font under the name "default".
        BitmapFont bfont=new BitmapFont();
        //bfont.scale(1);
        skin.add("default",bfont);

        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

        textButtonStyle.font = skin.getFont("default");

        skin.add("default", textButtonStyle);

        // Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
        final TextButton textButton=new TextButton("PLAY",textButtonStyle);
        textButton.setPosition(200, 200);
        stage.addActor(textButton);
        stage.addActor(textButton);
        stage.addActor(textButton);
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

        /*====================== NEW CODE ======================*/
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        /*================== END OF NEW CODE ===================*/

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
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
        Settings_header.dispose();
        appBackground.dispose();
    }

    @Override
    public void input(String text)
    {

    }

    @Override
    public void canceled()
    {

    }
}
