package ro.redmotor.kartgame.Drawables;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import ro.redmotor.kartgame.Game.Utilities.Point;

/**
 * Created by Gabi on 12/14/2015.
 */
public class ThrottleBrakingControlDO extends DrawableObject {


    private float yLine = -1;
    private float boundX1;
    private float boundX2;
    private float boundY1;
    private float boundY2;

    public ThrottleBrakingControlDO(Scene scene, boolean debug) {
        super(scene, debug);

        //be careful of the order X2 > X1 and Y2 > Y1
        boundX1 = scene.metersWidth(0);
        boundX2 = scene.metersWidth(3);
        boundY1 = scene.metersHeight(scene.getHeightInM() / 2 - 1.5f);
        boundY2 = scene.metersHeight(scene.getHeightInM() / 2 + 1.5f);
        yLine = boundY1 + (boundY2 - boundY1) / 2;

    }

    @Override
    public void loadObject(Context context) {

    }

    @Override
    protected void drawObject(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        canvas.drawRect(boundX1, boundY1, boundX2, boundY2 - (boundY2 - boundY1) / 2, paint);
        paint.setColor(Color.RED);
        canvas.drawRect(boundX1, boundY2 - (boundY2 - boundY1) / 2, boundX2, boundY2, paint);

        Paint paint2 = new Paint();
        paint2.setColor(Color.YELLOW);
        if (yLine > 0) {
            canvas.drawLine(boundX1, yLine, boundX2, yLine, paint2);
        }
        paint2.setTextSize(20);
        //canvas.drawText(String.format("Th/Brake: %.3f", getThrottleBrake()), metersWidth(4), metersHeight(1), paint2);

    }

    public boolean checkPoint(float x, float y) {

        if (x < boundX2 && x > boundX1 && y < boundY2 && y > boundY1) {
            yLine = y;
            return true;
        }

        return false;
    }

    public double getThrottleBrake() {
        double fullLockToLock = boundY2-boundY1;
        double selection = yLine - boundY1;
        double result = selection * 2 / fullLockToLock - 1;
        return -result;
    }

}
