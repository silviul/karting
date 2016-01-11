package ro.redmotor.kartgame.game.track.interfaces;

/**
 * Created by Gabi on 12/11/2015.
 */
public interface ILapListener
{
    void lapStarted(int lapNo, long lapStarted);
    void lapFinished(int lapNo, double lapTime);
}
