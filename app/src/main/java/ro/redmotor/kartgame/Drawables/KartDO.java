package ro.redmotor.kartgame.Drawables;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import ro.redmotor.kartgame.Game.Engine.Game;
import ro.redmotor.kartgame.Game.Utilities.Point;
import ro.redmotor.kartgame.R;

/**
 * Created by Gabi on 12/14/2015.
 */
public class KartDO extends DrawableObject {

    private Game game;
    private Bitmap kart;

    public KartDO(Point screenSize, Game game, boolean debug) {
        super(screenSize, debug);
        this.game = game;
    }

    @Override
    public void loadObject(Context context) {
        kart = BitmapFactory.decodeResource(context.getResources(), R.drawable.kart);
    }

    @Override
    protected void drawObject(Canvas canvas) {

        float scaleX = (canvas.getWidth()/ getWidthInM()) / (kart.getWidth()/ (float)game.getVehicle().getWidth());
        canvas.scale(scaleX, scaleX);

        float x = metersWidth(getWidthInM() / 2 - (float)game.getVehicle().getWidth() / 2)  / scaleX;
        float y = metersHeight(getHeightInM() - (float)game.getVehicle().getLength()) / scaleX;
        canvas.drawBitmap(kart, x, y, null);


    }
}
