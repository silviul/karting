package ro.redmotor.kartgame.Game.Engine;

import ro.redmotor.kartgame.Game.Engine.Interfaces.IGameListener;
import ro.redmotor.kartgame.Game.Track.Track;

/**
 * Created by Gabi on 12/11/2015.
 */
public class NullGameListener implements IGameListener
{
    public void lapCompleted(int lapNo, double lapTime)
    {

    }

    public void lapStarted(int lapNo)
    {

    }

    public void vehicleCollided(Track.CollisionResult result)
    {

    }
}