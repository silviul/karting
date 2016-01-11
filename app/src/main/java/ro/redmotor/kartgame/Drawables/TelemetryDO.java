package ro.redmotor.kartgame.Drawables;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import ro.redmotor.kartgame.Game.Engine.Game;
import ro.redmotor.kartgame.Game.Engine.Interfaces.IGameListener;
import ro.redmotor.kartgame.Game.Track.LapsManager;
import ro.redmotor.kartgame.Game.Track.Track;
import ro.redmotor.kartgame.Game.Utilities.Point;
import ro.redmotor.kartgame.R;

/**
 * Created by Gabi on 12/20/2015.
 */
public class TelemetryDO extends DrawableObject {


    public TelemetryDO(Scene scene, boolean debug) {
        super(scene, debug);
        scene.setTelemetry(this);
    }

    @Override
    public void loadObject(Context context) {
    }

    @Override
    protected void drawObject(Canvas canvas) {
        //draw debug stuff
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        float fontSize = scene.getPixelsPerMeterWidth() / 2;
        p.setTextSize(fontSize);

        Typeface bold = Typeface.create(Typeface.DEFAULT, Typeface.BOLD);
        p.setTypeface(bold);

        Paint rP = new Paint();
        rP.setColor(Color.BLACK);


        LapsManager lMan = scene.getGame().getLapsManager();

        if (lMan.getCurrentLap() > 0) {
            String currentLap = String.format("Lap %d: %3.3fs",
                    lMan.getCurrentLap(),
                    ((float) (System.currentTimeMillis() - lMan.getCurrentLapStartTime())) / 1000);
            canvas.drawText(currentLap, scene.metersWidth(0.6f), scene.metersHeight(1.1f), p);
        } else {
            canvas.drawText("No Lap", scene.metersWidth(0.6f), scene.metersHeight(1.1f), p);
        }

        String lastLap = "Last: NA";
        if (lMan.getLastLapTime()!=null) {
            lastLap = String.format("Last: %.3fs", (float)lMan.getLastLapTime()/1000);
        }
        canvas.drawText(lastLap, scene.metersWidth(0.6f), scene.metersHeight(1.6f),p);

        String bestTime = "Best: NA";
        if (lMan.getBestLapTime() != null) {
            bestTime = String.format("Best: %.3fs", (float) lMan.getBestLapTime() / 1000);
        }
        canvas.drawText(bestTime, scene.metersWidth(0.6f), scene.metersHeight(2.1f),p);



        canvas.drawText(String.format("%.1f km/h", (float)scene.getGame().getVehicle().getSpeed() *36/10), scene.metersWidth(7.5f), scene.metersHeight(1.1f),p);

//        canvas.drawText(String.format("Rpm: %.1f / Speed: %.1f / Steering: %.2f / Brake: %.2f / Th: %.2f / Last: %d",
//                game.getVehicle().getPowertrain().getEngine().getRpm(),
//                game.getVehicle().getSpeed() * 3600 / 1000,
//                game.getVehicle().getSteering(),
//                game.getVehicle().getBraking(),
//                game.getVehicle().getThrottle(),
//                game.getLapsManager().getLastLapTime()), 10, 50, p);
//

    }


}
