package ro.redmotor.kartgame.game.engine;

import ro.redmotor.kartgame.game.engine.interfaces.IGameListener;
import ro.redmotor.kartgame.game.track.Track;

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