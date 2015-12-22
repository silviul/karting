package ro.redmotor.kartgame.Game.Engine.Interfaces;

import ro.redmotor.kartgame.Game.Track.Track;

/**
 * Created by Gabi on 12/11/2015.
 */
public interface IGameListener
{
    void lapStarted(int lapNo);
    void lapCompleted(int lapNo, double lapTime);
    void vehicleCollided(Track.CollisionResult result);

}
