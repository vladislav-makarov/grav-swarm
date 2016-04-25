package com.mygdx.gravswarm;

/**
 * Created by Vlad M on 4/25/16.
 * This is the new settings class
 */
public class Settings
{
    public int INITIAL_MOONS_TO_SPAWN = 2000;
    public enum BOUNDARY_MODE {warp,
                               reflect,
                               despawn,
                               respawn,
                               none}
    public int WORKER_THREADS = 3;
    public float GRAVITY_MAGNITUDE = 1f;
    public float GRAVITY_SCALAR = 1f;
    public float LIGHT_INTENSITY = 50000f;
    public float TOUCH_PLANE_DEPTH = 0.3f;
}
