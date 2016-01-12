package ro.redmotor.kartgame.game.engine.interfaces;

import ro.redmotor.kartgame.game.track.Track;

/**
 * Created by Gabi on 12/11/2015.
 */
public interface IGameListener
{
    void lapStarted(int lapNo);
    void lapCompleted(int lapNo, double lapTime);
    void vehicleCollided(Track.CollisionResult result);

}
