package com.mygdx.gravswarm;

/**
 * Created by Vlad M on 4/25/16.
 * This is the new settings class
 */
public class Settings
{
    /*==================== Settings class variables ====================*/
    private int INITIAL_MOONS_TO_SPAWN = 2000;

    public enum BOUNDARY_MODE { warp, reflect, despawn, respawn, none }
    private BOUNDARY_MODE B_MODE_VALUE = BOUNDARY_MODE.none;

    private int WORKER_THREADS = 3;
    private float GRAVITY_MAGNITUDE = 1f;
    private float GRAVITY_SCALAR = 1f;
    private float LIGHT_INTENSITY = 50000f;
    private float TOUCH_PLANE_DEPTH = 0.3f;



    /*==================== INITIAL_MOONS_TO_SPAWN get/set fun functions ====================*/
    public int getINITIAL_MOONS_TO_SPAWN()
    {
        return INITIAL_MOONS_TO_SPAWN;
    }

    public void setINITIAL_MOONS_TO_SPAWN(int MOONS)
    {
        this.INITIAL_MOONS_TO_SPAWN = MOONS;
    }

    /*========================= BOUNDARY_MODE get/set fun functions ========================*/
    public BOUNDARY_MODE getBOUNDARY_MODE()
    {
        return B_MODE_VALUE;
    }

    public void setBOUNDARY_MODE(BOUNDARY_MODE VALUE)
    {
        this.B_MODE_VALUE = VALUE;
    }

    /*======================== WORKER_THREADS get/set fun functions ========================*/
    public int getWORKER_THREADS()
    {
        return WORKER_THREADS;
    }

    public void setWORKER_THREADS(int W_THREADS)
    {
        this.WORKER_THREADS =  W_THREADS;
    }

    /*====================== GRAVITY_MAGNITUDE get/set fun functions =======================*/
    public float getGRAVITY_MAGNITUDE()
    {
        return GRAVITY_MAGNITUDE;
    }

    public void setGRAVITY_MAGNITUDE(float G_MAGNITUDE)
    {
        this.GRAVITY_MAGNITUDE = G_MAGNITUDE;
    }

    /*======================== GRAVITY_SCALAR get/set fun functions ========================*/
    public float getGRAVITY_SCALAR()
    {
        return GRAVITY_SCALAR;
    }

    public void setGRAVITY_SCALAR(float G_SCALAR)
    {
        this.GRAVITY_SCALAR = G_SCALAR;
    }

    /*======================= LIGHT_INTENSITY get/set fun functions ========================*/
    public float getLIGHT_INTENSITY()
    {
        return LIGHT_INTENSITY;
    }

    public void setLIGHT_INTENSITY(float L_INTENSITY)
    {
        this.LIGHT_INTENSITY = L_INTENSITY;
    }

    /*====================== TOUCH_PLANE_DEPTH get/set fun functions =======================*/
    public float getTOUCH_PLANE_DEPTH()
    {
        return TOUCH_PLANE_DEPTH;
    }

    public void setTOUCH_PLANE_DEPTH(float TP_DEPTH)
    {
        this.TOUCH_PLANE_DEPTH = TP_DEPTH;
    }
}