package ro.redmotor.kartgame.Game.Utilities;

/**
 * Created by Gabi on 12/11/2015.
 */
//keep track of time between frames
public class Timer
{
    //store last time sample
    private long lastTime = System.currentTimeMillis();
    private float etime;

    //calculate and return elapsed time since last call
    public float getETime()
    {
        etime = (float)(System.currentTimeMillis() - lastTime) / 1000;
        lastTime = System.currentTimeMillis();

        //return 0.1f;
        return etime;
    }
}