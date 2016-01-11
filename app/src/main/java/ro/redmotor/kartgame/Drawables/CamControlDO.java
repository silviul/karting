package ro.redmotor.kartgame.drawables;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import ro.redmotor.kartgame.IGameControl;
import ro.redmotor.kartgame.R;

/**
 * Created by Gabi on 1/5/2016.
 */
public class CamControlDO extends DrawableObject implements IGameControl {

    private Bitmap cam;

    //cam detection bounds
    private float boundX1b;
    private float boundX2b;
    private float boundY1b;
    private float boundY2b;

    private double selection = 0;


    public CamControlDO(Scene scene, boolean debug) {
        super(scene, debug);

        scene.setCam(this);

        boundX1b = scene.metersWidth(0.2f);
        boundX2b = scene.metersWidth(1.2f);
        boundY1b = scene.metersHeight(scene.getHeightInM() - 5f);
        boundY2b = scene.metersHeight(scene.getHeightInM() - 4f);

    }

    @Override
    public void loadObject(Context context) {
        cam = BitmapFactory.decodeResource(context.getResources(), R.drawable.cam);

    }

    @Override
    protected void drawObject(Canvas canvas) {
        canvas.translate(boundX1b + (boundX2b - boundX1b) / 2, boundY1b + (boundY2b - boundY1b) / 2);
        float scaleX = (canvas.getWidth()/ scene.getWidthInM()) / (cam.getWidth()/ 1f);
        canvas.scale(scaleX, scaleX);

        canvas.drawBitmap(cam, -cam.getWidth() / 2, -cam.getHeight() / 2, null);

    }

    @Override
    public void checkPointerDown(int pointerId, float x, float y) {
        if (x < boundX2b && x > boundX1b && y < boundY2b && y > boundY1b) {
            this.selection = 1;
        }
    }

    @Override
    public void checkPointerUp(int pointerId, float x, float y) {

    }

    @Override
    public void checkPointerMove(int pointerId, float x, float y) {

    }

    @Override
    public double getSelection() {
        if (selection == 1) {
            selection = 0;
            return 1;
        }
        return 0;
    }
}
