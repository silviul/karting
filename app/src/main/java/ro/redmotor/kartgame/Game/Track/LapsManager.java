package ro.redmotor.kartgame.Game.Track;

import ro.redmotor.kartgame.Game.Track.Interfaces.ILapListener;

/**
 * Created by Gabi on 12/11/2015.
 */
public class LapsManager
{
    private ILapListener lapListener;
    private int currentLap;
    private Long currentLapStartTime;
    private Long lastLapTime;
    private Long bestLapTime;

    public LapsManager(ILapListener lapListener)
    {
        this.lapListener = lapListener != null ? lapListener : new NullLapListener();
    }

    public int getCurrentLap() {
        return currentLap;
    }

    public Long getCurrentLapStartTime() {
        return currentLapStartTime;
    }

    public Long getLastLapTime() {
        return lastLapTime;
    }

    public void reset()
    {
        currentLap = 0;
        currentLapStartTime = null;
    }

    public void newDetection(Long detectionTime)
    {

        long currentTimeInMills = System.currentTimeMillis();

        if (currentLapStartTime != null)
        {
            //not starting a lap but finishing one
            lastLapTime = (currentTimeInMills - currentLapStartTime);
            if (bestLapTime == null || bestLapTime > lastLapTime) {
                bestLapTime = lastLapTime;
            }
            lapListener.lapFinished(currentLap, lastLapTime);
        }

        //do these in any case (doesn't matter if you start or finish a lap)
        currentLap++;
        currentLapStartTime = currentTimeInMills;
        lapListener.lapStarted(currentLap, currentLapStartTime);

    }

    public Long getBestLapTime() {
        return bestLapTime;
    }
}