package com.mygdx.gravswarm;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Vlad M on 4/16/16.
 */
public class GravSwarm extends Game
{
    SpriteBatch batch;
    BitmapFont font;

    @Override
    public void create()
    {
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render()
    {
        super.render();
    }

    @Override
    public void dispose()
    {
        super.dispose();
        batch.dispose();
        font.dispose();
    }
}
