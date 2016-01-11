package ro.redmotor.kartgame.game.track;

import ro.redmotor.kartgame.game.track.interfaces.ILapListener;

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
