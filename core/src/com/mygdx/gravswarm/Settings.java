package com.mygdx.gravswarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Vlad M on 4/25/16.
 * This is the new settings class
 */
public class Settings
{
    /*==================== Settings class variables ====================*/
    public int INITIAL_MOONS_TO_SPAWN = 2000;

    public enum BOUNDARY_MODE { warp, reflect, despawn, respawn, none }
    public BOUNDARY_MODE B_MODE_VALUE;

    public int WORKER_THREADS = 3;
    public float GRAVITY_MAGNITUDE = 1f;
    public float GRAVITY_SCALAR = 1f;
    public float LIGHT_INTENSITY = 50000f;
    public float TOUCH_PLANE_DEPTH = 0.3f;

    private Preferences AppPreferences;
    private static final String PREFS_NAME = "GravSwarmSettings";
    private static final String PREF_MOONS_TO_SPAWN = "MoonsSpawned";
    private static final String PREF_B_MODE_VALUE = "BoundaryModeValue";
    private static final String PREF_WORKER_THREADS = "WorkerThreads";
    private static final String PREF_GRAVITY_MAGNITUDE = "GravityMagnitude";
    private static final String PREF_GRAVITY_SCALAR = "GravityScalar";
    private static final String PREF_LIGHT_INTENSITY = "LightIntensity";
    private static final String PREF_TOUCH_PLANE_DEPTH = "TouchPlaneDepth";


    protected Preferences getPrefs()
    {
        if(AppPreferences == null)
        {
            AppPreferences = Gdx.app.getPreferences(PREFS_NAME);
        }
        return AppPreferences;
    }


    /*==================== INITIAL_MOONS_TO_SPAWN get/set fun functions ====================*/
    public int getINITIAL_MOONS_TO_SPAWN()
    {
        return getPrefs().getInteger(PREF_MOONS_TO_SPAWN, INITIAL_MOONS_TO_SPAWN);
    }

    public void setINITIAL_MOONS_TO_SPAWN(int MOONS)
    {
        getPrefs().getInteger(PREF_MOONS_TO_SPAWN, MOONS);
        getPrefs().flush();
    }

    /*========================= BOUNDARY_MODE get/set fun functions ========================*/
    public BOUNDARY_MODE getBOUNDARY_MODE()
    {
        // not sure how to use getPrefs() here
        // help NEEDED (or walk-around, so we don't use enum)
        return B_MODE_VALUE;
    }

    public void setBOUNDARY_MODE(BOUNDARY_MODE VALUE)
    {
        // not sure how to use getPrefs() here
        // help NEEDED (or walk-around, so we don't use enum)
        this.B_MODE_VALUE = VALUE;
    }

    /*======================== WORKER_THREADS get/set fun functions ========================*/
    public int getWORKER_THREADS()
    {
        return getPrefs().getInteger(PREF_WORKER_THREADS, WORKER_THREADS);
    }

    public void setWORKER_THREADS(int W_THREADS)
    {
        getPrefs().getInteger(PREF_WORKER_THREADS, W_THREADS);
        getPrefs().flush();
    }

    /*====================== GRAVITY_MAGNITUDE get/set fun functions =======================*/
    public float getGRAVITY_MAGNITUDE()
    {
        return getPrefs().getFloat(PREF_GRAVITY_MAGNITUDE, GRAVITY_MAGNITUDE);
    }

    public void setGRAVITY_MAGNITUDE(float G_MAGNITUDE)
    {
        getPrefs().getFloat(PREF_GRAVITY_MAGNITUDE, G_MAGNITUDE);
        getPrefs().flush();
    }

    /*======================== GRAVITY_SCALAR get/set fun functions ========================*/
    public float getGRAVITY_SCALAR()
    {
        return getPrefs().getFloat(PREF_GRAVITY_SCALAR, GRAVITY_SCALAR);
    }

    public void setGRAVITY_SCALAR(float G_SCALAR)
    {
        getPrefs().getFloat(PREF_GRAVITY_SCALAR, G_SCALAR);
        getPrefs().flush();
    }

    /*======================= LIGHT_INTENSITY get/set fun functions ========================*/
    public float getLIGHT_INTENSITY()
    {
        return getPrefs().getFloat(PREF_LIGHT_INTENSITY, LIGHT_INTENSITY);
    }

    public void setLIGHT_INTENSITY(float L_INTENSITY)
    {
        getPrefs().getFloat(PREF_LIGHT_INTENSITY, L_INTENSITY);
        getPrefs().flush();
    }

    /*====================== TOUCH_PLANE_DEPTH get/set fun functions =======================*/
    public float getTOUCH_PLANE_DEPTH()
    {
        return getPrefs().getFloat(PREF_TOUCH_PLANE_DEPTH, TOUCH_PLANE_DEPTH);
    }

    public void setTOUCH_PLANE_DEPTH(float TP_DEPTH)
    {
        getPrefs().getFloat(PREF_TOUCH_PLANE_DEPTH, TP_DEPTH);
        getPrefs().flush();
    }
}
