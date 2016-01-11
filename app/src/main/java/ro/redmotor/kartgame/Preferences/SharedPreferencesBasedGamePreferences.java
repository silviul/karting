package ro.redmotor.kartgame.preferences;

import android.content.SharedPreferences;

import ro.redmotor.kartgame.preferences.interfaces.GamePreferences;

public class SharedPreferencesBasedGamePreferences implements GamePreferences{

    private final SharedPreferences defaultSharedPreferences;
    private final SharedPreferences.Editor defaultSharedPreferencesEditor;

    public SharedPreferencesBasedGamePreferences(SharedPreferences defaultSharedPreferences,
                                                 SharedPreferences.Editor defaultSharedPreferencesEditor) {
        this.defaultSharedPreferences = defaultSharedPreferences;
        this.defaultSharedPreferencesEditor = defaultSharedPreferencesEditor;
    }

    @Override
    public void saveBestTime(long bestLapTime) {
        defaultSharedPreferencesEditor.putLong(PREFKEY_BEST_LAP_TIME, bestLapTime);
        defaultSharedPreferencesEditor.apply();
    }

    /**
     *
     * @return
     * the best time as saved in the preferences if it exists or -1 if it's not in preferences
     */
    @Override
    public long getBestTime() {
        return defaultSharedPreferences.getLong(PREFKEY_BEST_LAP_TIME, -1l);
    }
}
