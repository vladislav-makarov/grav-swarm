package com.mygdx.gravswarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;


/**
 * Created by Vlad M on 4/18/16.
 */
public class SettingsScreen implements Screen
{
    final GravSwarm game;

    OrthographicCamera camera;
    Vector3 touchPos;

    ShapeRenderer shape_render;

    Sound settingsSelectionSound;

    Texture Settings_header;
    Texture appBackground;
    Texture backButton;

    // Declaration of textures for the Settings Options
    Texture NumberOfMoons;
    Texture BoundaryMode;
    Texture WorkerThreads;
    Texture GravityMagnitude;
    Texture GravityScalar;
    Texture LightIntensity;
    Texture TouchDepth;

    // Declaration of textures for NumberOfMoons options
    Texture NumberOfMoons_option0;
    Texture NumberOfMoons_option1;
    Texture NumberOfMoons_option2;
    Texture NumberOfMoons_option3;
    Texture NumberOfMoons_option4;

    // Declaration of textures for BoundaryMode options
    Texture BoundaryMode_option1;
    Texture BoundaryMode_option2;
    Texture BoundaryMode_option3;
    Texture BoundaryMode_option4;

    // Declaration of textures for WorkerThreads options
    Texture WorkerThreads_option1;
    Texture WorkerThreads_option2;
    Texture WorkerThreads_option3;
    Texture WorkerThreads_option4;

    // Declaration of textures for LightIntensity options
    Texture LightIntensity_option1;
    Texture LightIntensity_option2;
    Texture LightIntensity_option3;
    Texture LightIntensity_option4;

    // Declaration of textures for TouchDepth options
    Texture TouchDepth_option1;
    Texture TouchDepth_option2;
    Texture TouchDepth_option3;
    Texture TouchDepth_option4;

    // *** TEMPORARY. TODO: Menu options 4 & 5
    Texture Under_Dev;


    // Needed for triangle rendering for selected menu options
    int NumberOfMoons_selected;
    Settings.BOUNDARY_MODE BoundaryMode_selected;
    int WorkerThreads_selected;
    float LightIntensity_selected;
    float TouchDepth_selected;


    // defining BackButton's location in the 3D space (value #3 = button's width, value #4 = button's height)
    Rectangle RBackButton = new Rectangle(0, 720-86, 100, 86);

    // defining NumberOfMoons_options locations in the 3D space (value #3 = texture's width, value #4 = texture's height)
    Rectangle RNumberOfMoons_option0 = new Rectangle(40+458+40, (720-86-100), 84, 62);
    Rectangle RNumberOfMoons_option1 = new Rectangle(40+458+40+(84+20), (720-86-100), 107, 62);
    Rectangle RNumberOfMoons_option2 = new Rectangle(40+458+40+(84+20)+(107+20), (720-86-100), 108, 62);
    Rectangle RNumberOfMoons_option3 = new Rectangle(40+458+40+(84+20)+(107+20)+(108+20), (720-86-100), 110, 62);
    Rectangle RNumberOfMoons_option4 = new Rectangle(40+458+40+(84+20)+(107+20)+(108+20)+(110+20), (720-86-100), 109, 62);

    // defining BoundaryMode_options locations in the 3D space (value #3 = texture's width, value #4 = texture's height)
    Rectangle RBoundaryMode_option1 = new Rectangle(40+409+40, (720-86-100)-(80), 113, 62);
    Rectangle RBoundaryMode_option2 = new Rectangle(40+409+40+(113+20), (720-86-100)-(80), 115, 62);
    Rectangle RBoundaryMode_option3 = new Rectangle(40+409+40+(113+20)+(115+20), (720-86-100)-(80), 140, 62);
    Rectangle RBoundaryMode_option4 = new Rectangle(40+409+40+(113+20)+(115+20)+(140+20), (720-86-100)-(80), 191, 62);

    // defining WorkerThreads_options locations in the 3D space (value #3 = texture's width, value #4 = texture's height)
    Rectangle RWorkerThreads_option1 = new Rectangle(40+414+40, (720-86-100)-(80*2), 64, 62);
    Rectangle RWorkerThreads_option2 = new Rectangle(40+414+40+(64+20), (720-86-100)-(80*2), 65, 62);
    Rectangle RWorkerThreads_option3 = new Rectangle(40+414+40+(64+20)+(65+20), (720-86-100)-(80*2),  65, 62);
    Rectangle RWorkerThreads_option4 = new Rectangle(40+414+40+(64+20)+(65+20)+(65+20), (720-86-100)-(80*2),  81, 62);

    // defining LightIntensity_options locations in the 3D space (value #3 = texture's width, value #4 = texture's height)
    Rectangle RLightIntensity_option1 = new Rectangle(40+370+40, (720-86-100)-(80*5), 132, 62);
    Rectangle RLightIntensity_option2 = new Rectangle(40+370+40+(132+20), (720-86-100)-(80*5), 134, 62);
    Rectangle RLightIntensity_option3 = new Rectangle(40+370+40+(132+20)+(134+20), (720-86-100)-(80*5), 132, 62);
    Rectangle RLightIntensity_option4 = new Rectangle(40+370+40+(132+20)+(134+20)+(132+20), (720-86-100)-(80*5), 133, 62);

    // defining TouchDepth_options locations in the 3D space (value #3 = texture's width, value #4 = texture's height)
    Rectangle RTouchDepth_option1 = new Rectangle(40+324+40, (720-86-100)-(80*6), 89, 62);
    Rectangle RTouchDepth_option2 = new Rectangle(40+324+40+(89+20), (720-86-100)-(80*6), 89, 62);
    Rectangle RTouchDepth_option3 = new Rectangle(40+324+40+(89+20)+(89+20), (720-86-100)-(80*6), 89, 62);
    Rectangle RTouchDepth_option4 = new Rectangle(40+324+40+(89+20)+(89+20)+(89+20), (720-86-100)-(80*6), 89, 62);


    public SettingsScreen (final GravSwarm thisGame)
    {
        this.game = thisGame;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);

        shape_render = new ShapeRenderer();

        touchPos = new Vector3();

        settingsSelectionSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/settings_selection.wav"));

        Settings_header = new Texture("settings.png");
        appBackground = new Texture("test_background.png");
        backButton = new Texture("backButton.png");

        // Initialization of textures for the Settings Options
        NumberOfMoons = new Texture("SettingsOptions/1-number_of_moons.png");
        BoundaryMode = new Texture("SettingsOptions/2-boundary_mode.png");
        WorkerThreads = new Texture("SettingsOptions/3-worker_threads.png");
        GravityMagnitude = new Texture("SettingsOptions/4-gravity_magnitude.png");
        GravityScalar = new Texture("SettingsOptions/5-gravity_scalar.png");
        LightIntensity = new Texture("SettingsOptions/6-light_intensity.png");
        TouchDepth = new Texture("SettingsOptions/7-touch_depth.png");

        // Options for NumberOfMoons options
        NumberOfMoons_option0 = new Texture("SettingsOptions/1_Options/1_option0.png");
        NumberOfMoons_option1 = new Texture("SettingsOptions/1_Options/1_option1.png");
        NumberOfMoons_option2 = new Texture("SettingsOptions/1_Options/1_option2.png");
        NumberOfMoons_option3 = new Texture("SettingsOptions/1_Options/1_option3.png");
        NumberOfMoons_option4 = new Texture("SettingsOptions/1_Options/1_option4.png");

        // Options for BoundaryMode options
        BoundaryMode_option1 = new Texture("SettingsOptions/2_Options/2_option1.png");
        BoundaryMode_option2 = new Texture("SettingsOptions/2_Options/2_option2.png");
        BoundaryMode_option3 = new Texture("SettingsOptions/2_Options/2_option3.png");
        BoundaryMode_option4 = new Texture("SettingsOptions/2_Options/2_option4.png");

        // Options for WorkerThreads options
        WorkerThreads_option1 = new Texture("SettingsOptions/3_Options/3_option1.png");
        WorkerThreads_option2 = new Texture("SettingsOptions/3_Options/3_option2.png");
        WorkerThreads_option3 = new Texture("SettingsOptions/3_Options/3_option3.png");
        WorkerThreads_option4 = new Texture("SettingsOptions/3_Options/3_option4.png");

        // Options for LightIntensity options
        LightIntensity_option1 = new Texture("SettingsOptions/6_Options/6_option1.png");
        LightIntensity_option2 = new Texture("SettingsOptions/6_Options/6_option2.png");
        LightIntensity_option3 = new Texture("SettingsOptions/6_Options/6_option3.png");
        LightIntensity_option4 = new Texture("SettingsOptions/6_Options/6_option4.png");

        // Options for TouchDepth options
        TouchDepth_option1 = new Texture("SettingsOptions/7_Options/7_option1.png");
        TouchDepth_option2 = new Texture("SettingsOptions/7_Options/7_option2.png");
        TouchDepth_option3 = new Texture("SettingsOptions/7_Options/7_option3.png");
        TouchDepth_option4 = new Texture("SettingsOptions/7_Options/7_option4.png");

        // *** TEMPORARY. TODO: Menu options 4 & 5
        Under_Dev = new Texture("under_dev.png");
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
        game.batch.draw(backButton, 0, 720-86);

        // Drawing elements for the Settings Options
        game.batch.draw(NumberOfMoons, 40, 720-86-100);
        game.batch.draw(BoundaryMode, 40, (720-86-100)-(80));
        game.batch.draw(WorkerThreads, 40,(720-86-100)-(80*2));
        game.batch.draw(GravityMagnitude, 40,(720-86-100)-(80*3));
        game.batch.draw(GravityScalar, 40, (720-86-100)-(80*4));
        game.batch.draw(LightIntensity, 40, (720-86-100)-(80*5));
        game.batch.draw(TouchDepth, 40, (720-86-100)-(80*6));

        // Drawing elements for NumberOfMoons options
        game.batch.draw(NumberOfMoons_option0, 40+458+40, 720-86-100);
        game.batch.draw(NumberOfMoons_option1, 40+458+40+(84+20), 720-86-100);
        game.batch.draw(NumberOfMoons_option2, 40+458+40+(84+20)+(107+20), 720-86-100);
        game.batch.draw(NumberOfMoons_option3, 40+458+40+(84+20)+(107+20)+(108+20), 720-86-100);
        game.batch.draw(NumberOfMoons_option4, 40+458+40+(84+20)+(107+20)+(108+20)+(110+20), 720-86-100);

        // Drawing elements for BoundaryMode options
        game.batch.draw(BoundaryMode_option1, 40+409+40, (720-86-100)-(80));
        game.batch.draw(BoundaryMode_option2, 40+409+40+(113+20), (720-86-100)-(80));
        game.batch.draw(BoundaryMode_option3, 40+409+40+(113+20)+(115+20), (720-86-100)-(80));
        game.batch.draw(BoundaryMode_option4, 40+409+40+(113+20)+(115+20)+(140+20), (720-86-100)-(80));

        // Drawing elements for WorkerThreads options
        game.batch.draw(WorkerThreads_option1, 40+414+40, (720-86-100)-(80*2));
        game.batch.draw(WorkerThreads_option2, 40+414+40+(64+20), (720-86-100)-(80*2));
        game.batch.draw(WorkerThreads_option3, 40+414+40+(64+20)+(65+20), (720-86-100)-(80*2));
        game.batch.draw(WorkerThreads_option4, 40+414+40+(64+20)+(65+20)+(65+20), (720-86-100)-(80*2));

        // Drawing elements for LightIntensity options
        game.batch.draw(LightIntensity_option1, 40+370+40, (720-86-100)-(80*5));
        game.batch.draw(LightIntensity_option2, 40+370+40+(132+20), (720-86-100)-(80*5));
        game.batch.draw(LightIntensity_option3, 40+370+40+(132+20)+(134+20), (720-86-100)-(80*5));
        game.batch.draw(LightIntensity_option4, 40+370+40+(132+20)+(134+20)+(132+20), (720-86-100)-(80*5));

        // Drawing elements for TouchDepth options
        game.batch.draw(TouchDepth_option1, 40+324+40, (720-86-100)-(80*6));
        game.batch.draw(TouchDepth_option2, 40+324+40+(89+20), (720-86-100)-(80*6));
        game.batch.draw(TouchDepth_option3, 40+324+40+(89+20)+(89+20), (720-86-100)-(80*6));
        game.batch.draw(TouchDepth_option4, 40+324+40+(89+20)+(89+20)+(89+20), (720-86-100)-(80*6));

        // *** TEMPORARY. TODO: Menu options 4 & 5
        game.batch.draw(Under_Dev, 40+483+40, (720-86-100)-(80*3));
        game.batch.draw(Under_Dev, 40+376+40, (720-86-100)-(80*4));


        game.batch.end();


        // Get currently selected settings from Settings class
        NumberOfMoons_selected = game.settings.getINITIAL_MOONS_TO_SPAWN();
        BoundaryMode_selected = game.settings.getBOUNDARY_MODE();
        WorkerThreads_selected = game.settings.getWORKER_THREADS();
        LightIntensity_selected = game.settings.getLIGHT_INTENSITY();
        TouchDepth_selected = game.settings.getTOUCH_PLANE_DEPTH();


        shape_render.setProjectionMatrix(camera.combined);
        shape_render.setColor(Color.GREEN);
        shape_render.begin(ShapeType.Line);

        // Render shapes for NumberOfMoons
        if (NumberOfMoons_selected == 500)
        {
            shape_render.rect(40+458+40, (720-86-100), 84, 62);
            shape_render.rect(40+458+40-1, (720-86-100)-1, 84+2, 62+2);
        }
        else if (NumberOfMoons_selected == 1000)
        {
            shape_render.rect(40+458+40+(84+20), (720-86-100), 107, 62);
            shape_render.rect(40+458+40+(84+20)-1, (720-86-100)-1, 107+2, 62+2);
        }
        else if (NumberOfMoons_selected == 2000)
        {
            shape_render.rect(40+458+40+(84+20)+(107+20), (720-86-100), 108, 62);
            shape_render.rect(40+458+40+(84+20)+(107+20)-1, (720-86-100)-1, 108+2, 62+2);
        }
        else if (NumberOfMoons_selected == 4000)
        {
            shape_render.rect(40+458+40+(84+20)+(107+20)+(108+20), (720-86-100), 110, 62);
            shape_render.rect(40+458+40+(84+20)+(107+20)+(108+20)-1, (720-86-100)-1, 110+2, 62+2);
        }
        else if (NumberOfMoons_selected == 8000)
        {
            shape_render.rect(40+458+40+(84+20)+(107+20)+(108+20)+(110+20), (720-86-100), 109, 62);
            shape_render.rect(40+458+40+(84+20)+(107+20)+(108+20)+(110+20)-1, (720-86-100)-1, 109+2, 62+2);
        }

        // Render shapes for BoundaryMode
        if (BoundaryMode_selected == Settings.BOUNDARY_MODE.none)
        {
            shape_render.rect(40+409+40, (720-86-100)-(80), 113, 62);
            shape_render.rect(40+409+40-1, (720-86-100)-(80)-1, 113+2, 62+2);
        }
        else if (BoundaryMode_selected == Settings.BOUNDARY_MODE.warp)
        {
            shape_render.rect(40+409+40+(113+20), (720-86-100)-(80), 115, 62);
            shape_render.rect(40+409+40+(113+20)-1, (720-86-100)-(80)-1, 115+2, 62+2);
        }
        else if (BoundaryMode_selected == Settings.BOUNDARY_MODE.reflect)
        {
            shape_render.rect(40+409+40+(113+20)+(115+20), (720-86-100)-(80), 140, 62);
            shape_render.rect(40+409+40+(113+20)+(115+20)-1, (720-86-100)-(80)-1, 140+2, 62+2);
        }
        else if (BoundaryMode_selected == Settings.BOUNDARY_MODE.despawn)
        {
            shape_render.rect(40+409+40+(113+20)+(115+20)+(140+20), (720-86-100)-(80), 191, 62);
            shape_render.rect(40+409+40+(113+20)+(115+20)+(140+20)-1, (720-86-100)-(80)-1, 191+2, 62+2);
        }

        // Render shapes for WorkerThreads
        if (WorkerThreads_selected == 1)
        {
            shape_render.rect(40+414+40, (720-86-100)-(80*2), 64, 62);
            shape_render.rect(40+414+40-1, (720-86-100)-(80*2)-1, 64+2, 62+2);
        }
        else if (WorkerThreads_selected == 3)
        {
            shape_render.rect(40+414+40+(64+20), (720-86-100)-(80*2), 65, 62);
            shape_render.rect(40+414+40+(64+20)-1, (720-86-100)-(80*2)-1, 65+2, 62+2);
        }
        else if (WorkerThreads_selected == 5)
        {
            shape_render.rect(40+414+40+(64+20)+(65+20), (720-86-100)-(80*2),  65, 62);
            shape_render.rect(40+414+40+(64+20)+(65+20)-1, (720-86-100)-(80*2)-1,  65+2, 62+2);
        }
        else if (WorkerThreads_selected == 10)
        {
            shape_render.rect(40+414+40+(64+20)+(65+20)+(65+20), (720-86-100)-(80*2),  81, 62);
            shape_render.rect(40+414+40+(64+20)+(65+20)+(65+20)-1, (720-86-100)-(80*2)-1,  81+2, 62+2);
        }

        // Render shapes for LightIntensity
        if (LightIntensity_selected == 25000f)
        {
            shape_render.rect(40+370+40, (720-86-100)-(80*5), 132, 62);
            shape_render.rect(40+370+40-1, (720-86-100)-(80*5)-1, 132+2, 62+2);
        }
        else if (LightIntensity_selected == 40000f)
        {
            shape_render.rect(40+370+40+(132+20), (720-86-100)-(80*5), 134, 62);
            shape_render.rect(40+370+40+(132+20)-1, (720-86-100)-(80*5)-1, 134+2, 62+2);
        }
        else if (LightIntensity_selected == 55000f)
        {
            shape_render.rect(40+370+40+(132+20)+(134+20), (720-86-100)-(80*5), 132, 62);
            shape_render.rect(40+370+40+(132+20)+(134+20)-1, (720-86-100)-(80*5)-1, 132+2, 62+2);
        }
        else if (LightIntensity_selected == 75000f)
        {
            shape_render.rect(40+370+40+(132+20)+(134+20)+(132+20), (720-86-100)-(80*5), 133, 62);
            shape_render.rect(40+370+40+(132+20)+(134+20)+(132+20)-1, (720-86-100)-(80*5)-1, 133+2, 62+2);
        }

        // Render shapes for TouchDepth
        if (TouchDepth_selected == 0.1f)
        {
            shape_render.rect(40+324+40, (720-86-100)-(80*6), 89, 62);
            shape_render.rect(40+324+40-1, (720-86-100)-(80*6)-1, 89+2, 62+2);
        }
        else if (TouchDepth_selected == 0.3f)
        {
            shape_render.rect(40+324+40+(89+20), (720-86-100)-(80*6), 89, 62);
            shape_render.rect(40+324+40+(89+20)-1, (720-86-100)-(80*6)-1, 89+2, 62+2);
        }
        else if (TouchDepth_selected == 0.5f)
        {
            shape_render.rect(40+324+40+(89+20)+(89+20), (720-86-100)-(80*6), 89, 62);
            shape_render.rect(40+324+40+(89+20)+(89+20)-1, (720-86-100)-(80*6)-1, 89+2, 62+2);
        }
        else if (TouchDepth_selected == 0.7f)
        {
            shape_render.rect(40+324+40+(89+20)+(89+20)+(89+20), (720-86-100)-(80*6), 89, 62);
            shape_render.rect(40+324+40+(89+20)+(89+20)+(89+20)-1, (720-86-100)-(80*6)-1, 89+2, 62+2);
        }


        shape_render.end();



        // allows us to return to the main menu
        Gdx.input.setCatchBackKey(true);

        // if the screen is touched, do:
        if (Gdx.input.isTouched())
        {
            Vector3 tmp = new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0);
            camera.unproject(tmp);

            // if on-screen back button is pressed
            if (RBackButton.contains(tmp.x, tmp.y))
            {
                Gdx.input.vibrate(20);
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }


            /*================ NumberOfMoons - screen input handling ================*/
            if (RNumberOfMoons_option0.contains(tmp.x, tmp.y))
            {
                settingsSelectionSound.play();
                Gdx.input.vibrate(20);
                game.settings.setINITIAL_MOONS_TO_SPAWN(500);      // set number of moons to 500
            }
            if (RNumberOfMoons_option1.contains(tmp.x, tmp.y))
            {
                settingsSelectionSound.play();
                Gdx.input.vibrate(20);
                game.settings.setINITIAL_MOONS_TO_SPAWN(1000);     // set number of moons to 1000
            }
            if (RNumberOfMoons_option2.contains(tmp.x, tmp.y))
            {
                settingsSelectionSound.play();
                Gdx.input.vibrate(20);
                game.settings.setINITIAL_MOONS_TO_SPAWN(2000);     // set number of moons to 2000
            }
            if (RNumberOfMoons_option3.contains(tmp.x, tmp.y))
            {
                settingsSelectionSound.play();
                Gdx.input.vibrate(20);
                game.settings.setINITIAL_MOONS_TO_SPAWN(4000);     // set number of moons to 4000
            }
            if (RNumberOfMoons_option4.contains(tmp.x, tmp.y))
            {
                settingsSelectionSound.play();
                Gdx.input.vibrate(20);
                game.settings.setINITIAL_MOONS_TO_SPAWN(8000);     // set number of moons to 8000
            }

            /*================= BoundaryMode - screen input handling ================*/
            if (RBoundaryMode_option1.contains(tmp.x, tmp.y))
            {
                settingsSelectionSound.play();
                Gdx.input.vibrate(20);
                game.settings.setBOUNDARY_MODE(Settings.BOUNDARY_MODE.none);   // set boundary mode to 'none'
            }
            if (RBoundaryMode_option2.contains(tmp.x, tmp.y))
            {
                settingsSelectionSound.play();
                Gdx.input.vibrate(20);
                game.settings.setBOUNDARY_MODE(Settings.BOUNDARY_MODE.warp);   // set boundary mode to 'warp'
            }
            if (RBoundaryMode_option3.contains(tmp.x, tmp.y))
            {
                settingsSelectionSound.play();
                Gdx.input.vibrate(20);
                game.settings.setBOUNDARY_MODE(Settings.BOUNDARY_MODE.reflect);   // set boundary mode to 'reflect'
            }
            if (RBoundaryMode_option4.contains(tmp.x, tmp.y))
            {
                settingsSelectionSound.play();
                Gdx.input.vibrate(20);
                game.settings.setBOUNDARY_MODE(Settings.BOUNDARY_MODE.despawn);   // set boundary mode to 'despawn'
            }

            /*================ WorkerThreads - screen input handling ================*/
            if (RWorkerThreads_option1.contains(tmp.x, tmp.y))
            {
                settingsSelectionSound.play();
                Gdx.input.vibrate(20);
                game.settings.setWORKER_THREADS(1);                       // set worker threads to 1
            }
            if (RWorkerThreads_option2.contains(tmp.x, tmp.y))
            {
                settingsSelectionSound.play();
                Gdx.input.vibrate(20);
                game.settings.setWORKER_THREADS(3);                       // set worker threads to 3
            }
            if (RWorkerThreads_option3.contains(tmp.x, tmp.y))
            {
                settingsSelectionSound.play();
                Gdx.input.vibrate(20);
                game.settings.setWORKER_THREADS(5);                       // set worker threads to 5
            }
            if (RWorkerThreads_option4.contains(tmp.x, tmp.y))
            {
                settingsSelectionSound.play();
                Gdx.input.vibrate(20);
                game.settings.setWORKER_THREADS(10);                      // set worker threads to 10
            }

            /*================ LightIntensity - screen input handling ===============*/
            if (RLightIntensity_option1.contains(tmp.x, tmp.y))
            {
                settingsSelectionSound.play();
                Gdx.input.vibrate(20);
                game.settings.setLIGHT_INTENSITY((float) 25000);    // set light intensity to 25000
            }
            if (RLightIntensity_option2.contains(tmp.x, tmp.y))
            {
                settingsSelectionSound.play();
                Gdx.input.vibrate(20);
                game.settings.setLIGHT_INTENSITY((float) 40000);    // set light intensity to 40000
            }
            if (RLightIntensity_option3.contains(tmp.x, tmp.y))
            {
                settingsSelectionSound.play();
                Gdx.input.vibrate(20);
                game.settings.setLIGHT_INTENSITY((float) 55000);    // set light intensity to 55000
            }
            if (RLightIntensity_option4.contains(tmp.x, tmp.y))
            {
                settingsSelectionSound.play();
                Gdx.input.vibrate(20);
                game.settings.setLIGHT_INTENSITY((float) 75000);    // set light intensity to 75000
            }

            /*================== TouchDepth - screen input handling =================*/
            if (RTouchDepth_option1.contains(tmp.x, tmp.y))
            {
                settingsSelectionSound.play();
                Gdx.input.vibrate(20);
                game.settings.setTOUCH_PLANE_DEPTH((float) 0.1);         // set touch depth to 0.1
            }
            if (RTouchDepth_option2.contains(tmp.x, tmp.y))
            {
                settingsSelectionSound.play();
                Gdx.input.vibrate(20);
                game.settings.setTOUCH_PLANE_DEPTH((float) 0.3);         // set touch depth to 0.3
            }
            if (RTouchDepth_option3.contains(tmp.x, tmp.y))
            {
                settingsSelectionSound.play();
                Gdx.input.vibrate(20);
                game.settings.setTOUCH_PLANE_DEPTH((float) 0.5);         // set touch depth to 0.5
            }
            if (RTouchDepth_option4.contains(tmp.x, tmp.y))
            {
                settingsSelectionSound.play();
                Gdx.input.vibrate(20);
                game.settings.setTOUCH_PLANE_DEPTH((float) 0.7);         // set touch depth to 0.7
            }
        }

        // if on-phone back button is pressed
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
        settingsSelectionSound.dispose();

        Settings_header.dispose();
        appBackground.dispose();

        NumberOfMoons.dispose();
        BoundaryMode.dispose();
        WorkerThreads.dispose();
        GravityMagnitude.dispose();
        GravityScalar.dispose();
        LightIntensity.dispose();
        TouchDepth.dispose();

        NumberOfMoons_option0.dispose();
        NumberOfMoons_option1.dispose();
        NumberOfMoons_option2.dispose();
        NumberOfMoons_option3.dispose();
        NumberOfMoons_option4.dispose();

        BoundaryMode_option1.dispose();
        BoundaryMode_option2.dispose();
        BoundaryMode_option3.dispose();
        BoundaryMode_option4.dispose();

        WorkerThreads_option1.dispose();
        WorkerThreads_option2.dispose();
        WorkerThreads_option3.dispose();
        WorkerThreads_option4.dispose();

        LightIntensity_option1.dispose();
        LightIntensity_option2.dispose();
        LightIntensity_option3.dispose();
        LightIntensity_option4.dispose();

        TouchDepth_option1.dispose();
        TouchDepth_option2.dispose();
        TouchDepth_option3.dispose();
        TouchDepth_option4.dispose();

        Under_Dev.dispose();

        shape_render.dispose();
    }
}