package ro.redmotor.kartgame;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import ro.redmotor.kartgame.Drawables.DrawableObject;
import ro.redmotor.kartgame.Drawables.KartDO;
import ro.redmotor.kartgame.Drawables.SteeringControlDO;
import ro.redmotor.kartgame.Drawables.TelemetryDO;
import ro.redmotor.kartgame.Drawables.ThrottleBrakingSeparateControlsDO;
import ro.redmotor.kartgame.Drawables.TrackDO;
import ro.redmotor.kartgame.Game.Engine.Game;
import ro.redmotor.kartgame.Game.Utilities.Point;
import ro.redmotor.kartgame.Sound.SoundPlayer;

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
        Game game = new Game(null, assetLoader, assetLoader);
        DrawableObject kart = new KartDO(screenSize, game, debug);
        DrawableObject track = new TrackDO(screenSize, game, debug);
        DrawableObject wheel = new SteeringControlDO(screenSize, debug);
        DrawableObject pedals = new ThrottleBrakingSeparateControlsDO(screenSize, debug);
        DrawableObject telemetry =  new TelemetryDO(screenSize, debug, game);
        IGameControl steeringControl = (IGameControl) wheel;
        IGameControl throttleBrakeControl = (IGameControl) pedals;
        SoundPlayer soundPlayer = new SoundPlayer();


        GamePanel gamePanel = new GamePanel(context,
                track,
                kart,
                wheel,
                pedals,
                telemetry,
                steeringControl,
                throttleBrakeControl,
                soundPlayer,
                game
                );

        game.setListener(gamePanel);

        return gamePanel;
    }
}
