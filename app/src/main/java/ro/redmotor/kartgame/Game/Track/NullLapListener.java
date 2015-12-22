package ro.redmotor.kartgame.Game.Track;

import ro.redmotor.kartgame.Game.Track.Interfaces.ILapListener;

/**
 * Created by Gabi on 12/11/2015.
 */
public class NullLapListener implements ILapListener
{
    public void lapFinished(int lapNo, double lapTime)
    {
    }


    public void lapStarted(int lapNo, long lapStarted)
    {
    }

}
