package ro.redmotor.kartgame.Drawables;

import android.content.Context;
import android.graphics.Canvas;

import ro.redmotor.kartgame.Game.Utilities.Point;

/**
 * Created by Gabi on 12/14/2015.
 * Represents an object that can be drawn
 * For the moment it has some helper methods and a
 * template that saves the canvas state and restores it
 * so we don't have to worry about that in derived classes
 *
 */
public abstract class DrawableObject {

    protected Point screenSize;
    private Float aspectRatio;
    private Float pixelsPerMeterWidth;
    private Float pixelsPerMeterHeight;
    protected boolean debug;
    private final int METERS_PER_SCREEN_WIDTH = 10;

    public DrawableObject(Point screenSize, boolean debug) {
        this.debug = debug;
        this.screenSize = screenSize;
    }

    public void draw(Canvas canvas)
    {
        int canvasState = canvas.save();
        this.drawObject(canvas);
        canvas.restoreToCount(canvasState);
    }

    public abstract void loadObject(Context context);
    protected abstract void drawObject(Canvas canvas);

    protected float getAspectRatio() {
        if (aspectRatio == null)  aspectRatio = (float)(screenSize.getY() / screenSize.getX());
        return aspectRatio;
    }

    protected float getWidthInM() {
        return METERS_PER_SCREEN_WIDTH;
    }

    protected float getHeightInM() {
        return METERS_PER_SCREEN_WIDTH * getAspectRatio();
    }

    protected float getPixelsPerMeterWidth() {
        if (pixelsPerMeterWidth == null) pixelsPerMeterWidth = (float)(screenSize.getX() / getWidthInM());
        return pixelsPerMeterWidth;
    }

    protected float getPixelsPerMeterHeight() {
        if (pixelsPerMeterHeight == null) pixelsPerMeterHeight = (float)(screenSize.getY() / getHeightInM());
        return pixelsPerMeterHeight;
    }

    /**
     * Helper method to translate meters in usable pixels
     * @param meters
     * @return
     */
    protected float metersHeight(float meters) {
        return meters * getPixelsPerMeterHeight();
    }

    /**
     * Helper method to translate meters in usable pixels
     * @param meters
     * @return
     */
    protected float metersWidth(float meters) {
        return meters * getPixelsPerMeterWidth();
    }

}
