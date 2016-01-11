package ro.redmotor.kartgame.preferences.interfaces;

public interface GamePreferences {

    String PREFKEY_BEST_LAP_TIME = "prefkey_best_time";

    void saveBestTime(long bestLapTime);

    long getBestTime();
}
