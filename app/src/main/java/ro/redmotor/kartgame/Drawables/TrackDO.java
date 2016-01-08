package ro.redmotor.kartgame.Drawables;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;

import ro.redmotor.kartgame.Game.Engine.Game;
import ro.redmotor.kartgame.Game.Utilities.Line2;
import ro.redmotor.kartgame.Game.Utilities.Point;
import ro.redmotor.kartgame.R;

/**
 * Created by Gabi on 12/14/2015.
 * Reached some sort of agreement now, on how to do it
 * it was obvious in the end
 *
 */
public class TrackDO extends DrawableObject {

    private Bitmap trackBitmap;

    public TrackDO(Scene scene, boolean debug) {
        super(scene, debug);
        scene.setTrack(this);
    }

    @Override
    public void loadObject(Context context) {
        trackBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.redmotor);
    }


    @Override
    protected void drawObject(Canvas canvas) {
        canvas.drawBitmap(trackBitmap, 0, 0, null);
    }

//    private long i,sum;
//
//    protected void drawObject2(Canvas canvas) {
//
//        long startTime = System.currentTimeMillis();
//
//        float trackScale = (float)game.getTrack().getScale();
//        float scaleFactor = getPixelsPerMeterWidth() / trackScale;
//
//        double xPos = game.getVehicle().getPosition().getX() * trackScale;
//        double yPos = (game.getTrack().getTrackHeight() - game.getVehicle().getPosition().getY()) * trackScale;
//
//        Line2 line = new Line2(new Point(0,0), new Point(xPos, yPos));
//        line = line.rotate(game.getVehicle().getAngle());
//
//        canvas.translate((-(float) line.getEndPoint().getX()) * scaleFactor
//                        + (float) (screenSize.getX() / 2) // add kart screen offset
//                        - metersWidth((float) game.getVehicle().getWidth() / 2), // add half a kart length
//                -(float) line.getEndPoint().getY() * scaleFactor
//                        + (float) screenSize.getY());
//
//        double angle = -game.getVehicle().getAngle() / Math.PI * 180.0f;
//
//        canvas.scale(scaleFactor, scaleFactor);
//        canvas.rotate((float) angle);
//
////        //compute drawing window
////        Point vehiclePosition = game.getVehicle().getPosition();
////        Point topLeft = getWorldPoint(new Point(vehiclePosition.getX() - getWidthInM() / 2,
////                 (game.getTrack().getTrackHeight() - vehiclePosition.getY()) - getHeightInM()));
////        Point topRight = getWorldPoint(new Point(vehiclePosition.getX() + 1.2 * (getWidthInM() / 2),
////                 (game.getTrack().getTrackHeight() - vehiclePosition.getY()) - getHeightInM()));
////        Point bottomRight = getWorldPoint(new Point(vehiclePosition.getX() + 1.2 * (getWidthInM() / 2),
////                 (game.getTrack().getTrackHeight() - vehiclePosition.getY()) - 0));
////        Point bottomLeft = getWorldPoint(new Point(vehiclePosition.getX() - getWidthInM() / 2,
////                 (game.getTrack().getTrackHeight() - vehiclePosition.getY()) - 0));
////
////
////        double minX = topLeft.getX() > topRight.getX() ? topRight.getX() : topLeft.getX();
////        minX = minX > bottomRight.getX() ? bottomRight.getX() : minX;
////        minX = minX > bottomLeft.getX() ? bottomLeft.getX() : minX;
////        minX = minX < 0 ? 0 : minX;
////
////        double minY = topLeft.getY() > topRight.getY() ? topRight.getY() : topLeft.getY();
////        minY = minY > bottomRight.getY() ? bottomRight.getY() : minY;
////        minY = minY > bottomLeft.getY() ? bottomLeft.getY() : minY;
////        minY = minY < 0 ? 0 : minY;
////
////        double maxX = topLeft.getX() > topRight.getX() ? topLeft.getX() : topRight.getX();
////        maxX = maxX > bottomRight.getX() ? maxX : bottomRight.getX();
////        maxX = maxX > bottomLeft.getX() ? maxX : bottomLeft.getX();
////
////
////        double maxY = topLeft.getY() > topRight.getY() ? topLeft.getY() : topRight.getY();
////        maxY = maxY > bottomRight.getY() ? maxY : bottomRight.getY();
////        maxY = maxY > bottomLeft.getY() ? maxY : bottomLeft.getY();
////
////
////        Rect src = new Rect();
////        src.set((int) (minX * 20), (int) (minY * 20),
////                (int) (maxX * 20), (int)(maxY * 20));
////        Rect dest = new Rect(0,0,src.width(),src.height());
////        canvas.drawBitmap(trackBitmap,src, src, null);
//        canvas.drawBitmap(trackBitmap, 0, 0, null);
//
//        sum += System.currentTimeMillis() - startTime;
//        i++;
//        //System.out.println(String.format("track: %d", System.currentTimeMillis() - startTime));
//
//    }

}
