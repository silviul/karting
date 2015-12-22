package ro.redmotor.kartgame.Drawables;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import ro.redmotor.kartgame.Game.Engine.Game;
import ro.redmotor.kartgame.Game.Engine.Interfaces.IGameListener;
import ro.redmotor.kartgame.Game.Track.LapsManager;
import ro.redmotor.kartgame.Game.Track.Track;
import ro.redmotor.kartgame.Game.Utilities.Point;

/**
 * Created by Gabi on 12/20/2015.
 */
public class TelemetryDO extends DrawableObject {


    private Game game;

    public TelemetryDO(Point screenSize, boolean debug, Game game) {
        super(screenSize, debug);
        this.game = game;
    }

    @Override
    public void loadObject(Context context) {

    }

    @Override
    protected void drawObject(Canvas canvas) {
        //draw debug stuff
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        float fontSize = getPixelsPerMeterWidth() / 2;
        p.setTextSize(fontSize);

        Typeface bold = Typeface.create(Typeface.SERIF, Typeface.BOLD);
        p.setTypeface(bold);

        Paint rP = new Paint();
        rP.setColor(Color.BLACK);


        LapsManager lMan = game.getLapsManager();
        String currentLap = String.format("Lap %d: %3.3fs",
                lMan.getCurrentLap(),
                ((float) (System.currentTimeMillis() - lMan.getCurrentLapStartTime())) / 1000);

        //canvas.drawRect(metersWidth(0.5f), metersHeight(0.5f), metersWidth(0.5f) + currentLap.length() * fontSize/2, metersHeight(1.75f) + 2 * fontSize , rP);
        if (lMan.getCurrentLap() > 0) {
            canvas.drawText(currentLap, metersWidth(0.6f), metersHeight(1.1f), p);
        } else {
            canvas.drawText("No Lap",metersWidth(0.6f), metersHeight(1.1f), p);
        }

        String lastLap = "Last: NA";
        if (lMan.getLastLapTime()!=null) {
            lastLap = String.format("Last: %.3fs", (float)lMan.getLastLapTime()/1000);
        }
        canvas.drawText(lastLap, metersWidth(0.6f), metersHeight(1.6f),p);

        String bestTime = "Best: NA";
        if (lMan.getBestLapTime() != null) {
            bestTime = String.format("Best: %.3fs", (float) lMan.getBestLapTime() / 1000);
        }
        canvas.drawText(bestTime, metersWidth(0.6f), metersHeight(2.1f),p);



        canvas.drawText(String.format("%.1f km/h", (float)game.getVehicle().getSpeed() *36/10), metersWidth(7.5f), metersHeight(1.1f),p);

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
