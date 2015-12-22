package ro.redmotor.kartgame.Drawables;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import ro.redmotor.kartgame.Game.Utilities.Point;
import ro.redmotor.kartgame.IGameControl;
import ro.redmotor.kartgame.R;

/**
 * Created by Gabi on 12/16/2015.
 */
public class ThrottleBrakingSeparateControlsDO extends DrawableObject implements IGameControl {

    private Bitmap throttle;
    private Bitmap brake;

    //brake detection bounds
    private float boundX1b;
    private float boundX2b;
    private float boundY1b;
    private float boundY2b;

    //throttle detection bounds
    private float boundX1t;
    private float boundX2t;
    private float boundY1t;
    private float boundY2t;

    //finger ids pressed
    private Integer brakePointer, throttlePointer;

    public ThrottleBrakingSeparateControlsDO(Point screenSize, boolean debug) {
        super(screenSize, debug);
        boundX1b = metersWidth(0f);
        boundX2b = metersWidth(1.5f);
        boundY1b = metersHeight(getHeightInM() - 3.1f);
        boundY2b = metersHeight(getHeightInM() - 0);

        boundX1t = metersWidth(1.6f);
        boundX2t = metersWidth(3.5f);
        boundY1t = metersHeight(getHeightInM() - 3.1f);
        boundY2t = metersHeight(getHeightInM() - 0);
    }

    @Override
    public void loadObject(Context context) {
        throttle = BitmapFactory.decodeResource(context.getResources(), R.drawable.throttle);
        brake = BitmapFactory.decodeResource(context.getResources(), R.drawable.brake);
    }

    @Override
    protected void drawObject(Canvas canvas) {

        //draw detection bounds in debug mode
        if (debug) {
            Paint p = new Paint();
            p.setColor(Color.RED);
            canvas.drawRect(boundX1b, boundY1b, boundX2b, boundY2b, p);
            canvas.drawRect(boundX1t, boundY1t, boundX2t, boundY2t, p);
        }

        //save state so we can reset for throttel pedal
        int state = canvas.save();
        //draw brake pedal
        canvas.translate(boundX1b + (boundX2b - boundX1b) / 2, boundY1b + (boundY2b - boundY1b) / 2);
        float scaleX = (canvas.getWidth()/ getWidthInM()) / (brake.getWidth()/ 1f);
        canvas.scale(scaleX, scaleX);
        canvas.drawBitmap(brake, -brake.getWidth() / 2, -brake.getHeight() / 2, null);

        //draw trottle pedal
        canvas.restoreToCount(state);
        canvas.translate(boundX1t + (boundX2t - boundX1t) / 2, boundY1t + (boundY2t - boundY1t) / 2);
        canvas.scale(scaleX, scaleX);
        canvas.drawBitmap(throttle,-throttle.getWidth()/2, -throttle.getHeight()/2,null );
    }

    private PedalPress checkPoint(float x, float y) {
        //check throttle
        if (x < boundX2t && x > boundX1t && y < boundY2t && y > boundY1t) {
            return PedalPress.Throttle;
        }

        //check brake
        if (x < boundX2b && x > boundX1b && y < boundY2b && y > boundY1b) {
            return PedalPress.Brake;
        }

        return PedalPress.None;
    }


    @Override
    public void checkPointerDown(int pointerId, float x, float y) {
        PedalPress pedal = checkPoint(x, y);
        switch (pedal) {
            case Brake:
                brakePointer = pointerId;
                break;
            case Throttle:
                throttlePointer = pointerId;
                break;

            case None:
                break;
        }

    }

    @Override
    public void checkPointerUp(int pointerId, float x, float y) {
        if (throttlePointer != null && pointerId == throttlePointer) {
            throttlePointer = null;
        }

        if (brakePointer != null && pointerId == brakePointer) {
            brakePointer = null;
        }

    }

    @Override
    public void checkPointerMove(int pointerId, float x, float y) {

    }

    @Override
    public double getSelection() {
        //brakes take precedence over throttle
        if (brakePointer != null) return -1;
        if (throttlePointer != null) return 1;
        //no pedals pressed
        return 0;

    }

    public enum PedalPress {
        Throttle, Brake, None
    }
}
