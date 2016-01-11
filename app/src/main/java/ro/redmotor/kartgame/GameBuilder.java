package ro.redmotor.kartgame;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.util.ArrayList;

import ro.redmotor.kartgame.cameras.DelayedFixedKartCamera;
import ro.redmotor.kartgame.cameras.FixedKartCamera;
import ro.redmotor.kartgame.cameras.FixedTrackCamera;
import ro.redmotor.kartgame.cameras.ICamera;
import ro.redmotor.kartgame.drawables.CamControlDO;
import ro.redmotor.kartgame.drawables.DrawableObject;
import ro.redmotor.kartgame.drawables.GhostDO;
import ro.redmotor.kartgame.drawables.KartDO;
import ro.redmotor.kartgame.drawables.Scene;
import ro.redmotor.kartgame.drawables.SteeringControlDO;
import ro.redmotor.kartgame.drawables.TelemetryDO;
import ro.redmotor.kartgame.drawables.ThrottleBrakingSeparateControlsDO;
import ro.redmotor.kartgame.drawables.TrackDO;
import ro.redmotor.kartgame.game.engine.Game;
import ro.redmotor.kartgame.game.utilities.Point;
import ro.redmotor.kartgame.preferences.SharedPreferencesBasedGamePreferences;
import ro.redmotor.kartgame.preferences.interfaces.GamePreferences;
import ro.redmotor.kartgame.sound.SoundPlayer;

/**
 * Created by Gabi on 12/17/2015.
 */
public class GameBuilder {

    private Context context;

    public GameBuilder(Context context) {
        this.context = context;
    }

    public GamePanel buildGamePanel(boolean debug) {
        //get screen size
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        Point screenSize = new Point(metrics.widthPixels,metrics.heightPixels);


        AssetLoader assetLoader = new AssetLoader(context);
        Game game = new Game(null, assetLoader, assetLoader, gamePreferences());
        Scene scene = new Scene(screenSize, game);
        DrawableObject kart = new KartDO(scene, debug);
        DrawableObject track = new TrackDO(scene, debug);
        DrawableObject wheel = new SteeringControlDO(scene, debug);
        DrawableObject pedals = new ThrottleBrakingSeparateControlsDO(scene, debug);
        DrawableObject telemetry =  new TelemetryDO(scene, debug);
        DrawableObject ghost = new GhostDO(scene, debug);
        DrawableObject cam = new CamControlDO(scene,debug);
        IGameControl steeringControl = (IGameControl) wheel;
        IGameControl throttleBrakeControl = (IGameControl) pedals;
        IGameControl camControl = (IGameControl) cam;
        SoundPlayer soundPlayer = new SoundPlayer();

        GamePanel gamePanel = new GamePanel(context,
                scene,
                steeringControl,
                throttleBrakeControl,
                camControl,
                soundPlayer,
                game
                );


        ArrayList<ICamera> cameras = new ArrayList();
        cameras.add(new FixedKartCamera(scene));
        cameras.add(new FixedTrackCamera(scene));
        cameras.add(new DelayedFixedKartCamera(scene));

        scene.setCameras(cameras);
        game.setListener(gamePanel);

        return gamePanel;
    }

    private GamePreferences gamePreferences() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor defaultSharedPreferencesEditor = defaultSharedPreferences.edit();
        GamePreferences result = new SharedPreferencesBasedGamePreferences(defaultSharedPreferences, defaultSharedPreferencesEditor);
        return result;
    }
}
