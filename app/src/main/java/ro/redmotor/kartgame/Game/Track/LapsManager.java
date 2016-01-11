package ro.redmotor.kartgame.game.track;

import ro.redmotor.kartgame.game.track.interfaces.ILapListener;
import ro.redmotor.kartgame.preferences.interfaces.GamePreferences;

/**
 * Created by Gabi on 12/11/2015.
 */
public class LapsManager {
    private ILapListener lapListener;
    private int currentLap;
    private Long currentLapStartTime;
    private Long lastLapTime;
    private GamePreferences gamePreferences;

    public LapsManager(ILapListener lapListener, GamePreferences gamePreferences) {
        this.gamePreferences = gamePreferences;
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

    public void reset() {
        currentLap = 0;
        currentLapStartTime = null;
    }

    public void newDetection(Long detectionTime) {
        long currentTimeInMills = System.currentTimeMillis();

        if (currentLapStartTime != null) {
            //not starting a lap but finishing one
            lastLapTime = (currentTimeInMills - currentLapStartTime);
            long bestLapTime = getBestLapTime();
            if (bestLapTime == -1 || bestLapTime > lastLapTime) {
                gamePreferences.saveBestTime(lastLapTime);
            }
            lapListener.lapFinished(currentLap, lastLapTime);
        }

        //do these in any case (doesn't matter if you start or finish a lap)
        currentLap++;
        currentLapStartTime = currentTimeInMills;
        lapListener.lapStarted(currentLap, currentLapStartTime);

    }

    public Long getBestLapTime() {
        return gamePreferences.getBestTime();
    }
}