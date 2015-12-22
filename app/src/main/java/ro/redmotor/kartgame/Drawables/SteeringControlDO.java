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
 * Created by Gabi on 12/14/2015.
 */
public class SteeringControlDO extends DrawableObject implements IGameControl {

    private Bitmap wheel;
    private float xLine = -1;
    private float boundX1;
    private float boundX2;
    private float boundY1;
    private float boundY2;

    private Integer steeringPointer;

    public SteeringControlDO(Point screenSize, boolean debug) {
        super(screenSize, debug);

        //be careful of the order X2 > X1 and Y2 > Y1
        boundX1 = metersWidth(6.5f);
        boundX2 = metersWidth(9.5f);
        boundY1 = metersHeight(getHeightInM() - 3);
        boundY2 = metersHeight(getHeightInM());
        resetSteering();

    }

    @Override
    public void loadObject(Context context) {
        wheel = BitmapFactory.decodeResource(context.getResources(), R.drawable.wheel);
    }

    @Override
    protected void drawObject(Canvas canvas) {

        //draw steering bounds if debug
        if (debug) {
            Paint paint = new Paint();
            paint.setColor(Color.RED);

            Paint paint2 = new Paint();
            paint2.setColor(Color.YELLOW);

            canvas.drawRect(boundX1, boundY1, boundX2, boundY2, paint);
            if (xLine > 0) {
                canvas.drawLine(xLine, boundY1, xLine, boundY2, paint2);
            }
        }

        //draw wheel
        canvas.translate(boundX1 + (boundX2 - boundX1)/2, boundY1 + (boundY2-boundY1)/2);
        float scaleX = (canvas.getWidth()/ getWidthInM()) / (wheel.getWidth()/ 3f);
        canvas.scale(scaleX,scaleX);
        canvas.rotate((float)(getSteering() / Math.PI * 180.0f));
        canvas.drawBitmap(wheel,-wheel.getWidth()/2, -wheel.getHeight()/2,null );

    }

    private void resetSteering() {
        xLine = boundX1 + (boundX2 - boundX1) / 2;
    }

    private boolean checkPoint(float x, float y) {

        if (x < boundX2 && x > boundX1 && y < boundY2 && y > boundY1) {
            xLine = x;
            return true;
        }

        return false;

    }

    private double getSteering() {
        double fullLockToLock = boundX2-boundX1;
        double selection = xLine - boundX1;
        double result = selection * 2 / fullLockToLock - 1;
        return result;
    }

    @Override
    public void checkPointerDown(int pointerId, float x, float y) {
        if (checkPoint(x,y)) {
            this.steeringPointer = pointerId;
        }
    }

    @Override
    public void checkPointerUp(int pointerId, float x, float y) {
        if (steeringPointer != null && pointerId == steeringPointer) {
            steeringPointer = null;
            resetSteering();

        }
    }

    @Override
    public void checkPointerMove(int pointerId, float x, float y) {
        if (steeringPointer != null && steeringPointer == pointerId) {
            checkPoint(x,y);
        }
    }

    @Override
    public double getSelection() {
        return getSteering();
    }
}
