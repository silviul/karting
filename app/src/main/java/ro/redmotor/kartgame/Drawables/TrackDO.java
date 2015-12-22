package ro.redmotor.kartgame.Drawables;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import ro.redmotor.kartgame.Game.Engine.Game;
import ro.redmotor.kartgame.Game.Utilities.Line2;
import ro.redmotor.kartgame.Game.Utilities.Point;
import ro.redmotor.kartgame.R;

/**
 * Created by Gabi on 12/14/2015.
 * As evidence shows, still not sure how to draw the goddam track properly
 * Work in progress
 * TODO: clean this up
 */
public class TrackDO extends DrawableObject {

    private Bitmap trackBitmap;
    private Game game;

    public TrackDO(Point screenSize, Game game, boolean debug) {
        super(screenSize, debug);
        this.game = game;
    }

    @Override
    public void loadObject(Context context) {
        trackBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.redmotor);
    }


   /* protected void drawObject2(Canvas canvas) {


        float trackScale = (float)game.getTrack().getScale();
        float scaleFactor = getPixelsPerMeterWidth() / trackScale;

        float xPos = (float)game.getVehicle().getPosition().getX() * trackScale;
        float yPos = (float)(game.getTrack().getTrackHeight() - game.getVehicle().getPosition().getY()) * trackScale;

        xPos = xPos - (float) (getWidthInM()  / 2) * trackScale + ((float) game.getVehicle().getWidth() / 2) * trackScale;
        yPos = yPos - (float) getHeightInM() * trackScale;

        canvas.translate(-xPos, -yPos);
        double angle = -game.getVehicle().getAngle() / Math.PI * 180.0f;
        canvas.rotate((float) angle);
        canvas.scale(scaleFactor, scaleFactor);

        canvas.drawBitmap(trackBitmap, 0, 0, null);

//        float srcX = (float)xPos - ((float)screenSize.getX()/scaleFactor/2);
//        srcX = srcX < 0 ? 0 : srcX;
//        float srcY = (float)yPos - ((float)screenSize.getY()/scaleFactor/2);
//        srcY = srcY < 0 ? 0 : srcY;
//        float srcX2 = srcX + (float)screenSize.getX()/scaleFactor;
//        srcX2 = srcX2 >= trackBitmap.getWidth() ? trackBitmap.getWidth() : srcX2;
//        float srcY2 = srcY + (float)screenSize.getY()/scaleFactor;
//        srcY2 = srcY2 >= trackBitmap.getHeight() ? trackBitmap.getHeight() : srcY2;
//
//        Rect src = new Rect((int)srcX,(int)srcY, (int)srcX2, (int)srcY2);
//        Rect dest = new Rect(0, 0, (int)(screenSize.getX()/scaleFactor), (int)(screenSize.getY()/scaleFactor));
//
//
//        canvas.drawBitmap(trackBitmap, src, dest, null);

    }*/

    /*private boolean odd = false;*/


    @Override
    protected void drawObject(Canvas canvas) {

        float trackScale = (float)game.getTrack().getScale();
        float scaleFactor = getPixelsPerMeterWidth() / trackScale;

        double xPos = game.getVehicle().getPosition().getX() * trackScale;
        double yPos = (game.getTrack().getTrackHeight() - game.getVehicle().getPosition().getY()) * trackScale;

        Line2 line = new Line2(new Point(0,0), new Point(xPos, yPos));
        line = line.rotate(game.getVehicle().getAngle());

        canvas.translate((-(float) line.getEndPoint().getX()) * scaleFactor
                        + (float) (screenSize.getX() / 2) // add kart screen offset
                        - metersWidth((float) game.getVehicle().getWidth() / 2), // add half a kart length
                -(float) line.getEndPoint().getY() * scaleFactor
                        + (float) screenSize.getY());

        double angle = -game.getVehicle().getAngle() / Math.PI * 180.0f;

        canvas.scale(scaleFactor, scaleFactor);
        canvas.rotate((float) angle);
        canvas.drawBitmap(trackBitmap, 0, 0, null);

/*

        float lengthY = getHeightInM() * trackScale * 0.5f;
        float lengthX = getWidthInM() * trackScale * 1.5f;
        //float srcX = (float)game.getVehicle().getPosition().getX() * trackScale - ((getWidthInM()*trackScale)/2);
        float srcX = (float)game.getVehicle().getPosition().getX() * trackScale - (lengthY) + (float) (game.getVehicle().getWidth() / 2)* trackScale;
        //srcX -= length;
        //srcX = srcX < 0 ? 0 : srcX;
        //float srcY = (float)game.getTrack().getTrackHeight() - (float)game.getVehicle().getPosition().getY() * trackScale - ((getHeightInM()*trackScale)/2);
        float srcY = (float)game.getTrack().getTrackHeight() * trackScale - (float)game.getVehicle().getPosition().getY() * trackScale - (lengthY);
        //srcY -= length;
        //srcY = srcY < 0 ? 0 : srcY;
        float srcX2 = srcX + 2 * lengthY;
        //srcX2 = srcX2 >= trackBitmap.getWidth() ? trackBitmap.getWidth() :  srcX2;
        float srcY2 = srcY + 2 * lengthY;
        //srcY2 = srcY2 >= trackBitmap.getHeight() ? trackBitmap.getHeight() : srcY2;

        //Rect src = new Rect((int)srcX,(int)srcY, (int)srcX2, (int)srcY2);
        //Rect dest = new Rect((int)0, (int)0, (int)(screenSize.getX() / scaleFactor), (int)(screenSize.getY() / scaleFactor));

        Rect src = new Rect((int)srcX,(int)srcY, (int)srcX2, (int)srcY2);
        Rect dest = new Rect((int)0, (int)0, (int)(screenSize.getX() / scaleFactor), (int)(screenSize.getY() / scaleFactor));

        canvas.scale(scaleFactor, scaleFactor);
        canvas.rotate((float) angle);
        //if (!odd) {
            canvas.drawBitmap(trackBitmap, 0, 0, null);
        //    odd= true;
        //} else if (odd) {
            //canvas.drawBitmap(trackBitmap, src, src, null);
        //    odd = false;
        //}


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
//        canvas.rotate((float) angle);
//
//
//
//        canvas.scale(scaleFactor, scaleFactor);
//
//
//        float srcX = (float)game.getVehicle().getPosition().getX() * trackScale - ((getWidthInM()*trackScale)/2);
//        srcX = srcX < 0 ? 0 : srcX;
//        float srcY = (float)game.getVehicle().getPosition().getY() * trackScale - ((getHeightInM()*trackScale)/2);
//        srcY = srcY < 0 ? 0 : srcY;
//        float srcX2 = srcX + 10 * trackScale;
//        srcX2 = srcX2 >= trackBitmap.getWidth() ? trackBitmap.getWidth() : srcX2;
//        float srcY2 = srcY + 17 * trackScale;
//        srcY2 = srcY2 >= trackBitmap.getHeight() ? trackBitmap.getHeight() : srcY2;
//
//        Rect src = new Rect((int)0,(int)0, (int)srcX2, (int)srcY2);
//        Rect dest = new Rect(0, 0, 10 * (int)(getPixelsPerMeterWidth() / scaleFactor), 17 * (int)(getPixelsPerMeterHeight() / scaleFactor));
//
//        //canvas.drawBitmap(trackBitmap, 0, 0, null);
//        canvas.drawBitmap(trackBitmap, src, dest, null);

*/
    }

}
