package ro.redmotor.kartgame.drawables;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by Gabi on 12/14/2015.
 * Represents an object that can be drawn
 * For the moment it has some helper methods and a
 * template that saves the canvas state and restores it
 * so we don't have to worry about that in derived classes
 *
 */
public abstract class DrawableObject {

    protected Scene scene;
    protected boolean debug;

    public DrawableObject(Scene scene, boolean debug) {
        this.debug = debug;
        this.scene = scene;
    }

    public void draw(Canvas canvas)
    {
        int canvasState = canvas.save();
        this.drawObject(canvas);
        canvas.restoreToCount(canvasState);
    }

    public abstract void loadObject(Context context);
    protected abstract void drawObject(Canvas canvas);


}
