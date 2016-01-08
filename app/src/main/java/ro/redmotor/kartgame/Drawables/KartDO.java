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

    private Bitmap kart;

    public KartDO(Scene scene, boolean debug) {
        super(scene, debug);
        scene.setKart(this);
    }

    @Override
    public void loadObject(Context context) {
        kart = BitmapFactory.decodeResource(context.getResources(), R.drawable.kart);
        float scaleX = ((float)scene.getScreenSize().getX() / scene.getWidthInM()) / (kart.getWidth()/ (float)scene.getGame().getVehicle().getWidth());

        kart = Bitmap.createScaledBitmap(kart,(int)(kart.getWidth()*scaleX),(int)(kart.getHeight()*scaleX),false);
    }


    @Override
    protected void drawObject(Canvas canvas) {

        float trackScale = (float)scene.getGame().getTrack().getScale();
        float scaleX = trackScale / (kart.getWidth() / (float)scene.getGame().getVehicle().getWidth());
        canvas.scale(scaleX, scaleX);


        float x = (float) scene.getVehiclePosition().getX() * trackScale / scaleX;
        float y = (float)(scene.getGame().getTrack().getTrackHeight() - scene.getVehiclePosition().getY()) * trackScale / scaleX;
        canvas.translate(x, y);
        canvas.rotate((float) (scene.getVehicleAngle() / Math.PI * 180.0f));

        canvas.drawBitmap(kart, -kart.getWidth()/2, -kart.getHeight()/2, null);

    }

}
