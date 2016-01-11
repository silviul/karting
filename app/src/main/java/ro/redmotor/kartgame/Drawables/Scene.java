package ro.redmotor.kartgame.drawables;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;

import ro.redmotor.kartgame.cameras.ICamera;
import ro.redmotor.kartgame.game.engine.Game;
import ro.redmotor.kartgame.game.utilities.Point;

/**
 * Created by Gabi on 1/4/2016.
 */
public class Scene {

    private Game game;
    private DrawableObject track;
    private DrawableObject kart;
    private DrawableObject steeringWheel;
    private DrawableObject pedals;
    private DrawableObject telemetry;
    private DrawableObject ghost;
    private DrawableObject cam;

    private ICamera currentCamera;
    private ArrayList<ICamera> cameras;

    protected Point screenSize;
    private Float aspectRatio;
    private Float pixelsPerMeterWidth;
    private Float pixelsPerMeterHeight;
    protected boolean debug;
    private final int METERS_PER_SCREEN_WIDTH = 10;

    private Point vehiclePosition;
    private double vehicleAngle;

    public Scene(Point screenSize, Game game) {
        this.screenSize = screenSize;
        this.game = game;
    }


    public Game getGame() {
        return game;
    }

    public DrawableObject getTrack() {
        return track;
    }

    public void setTrack(DrawableObject track) {
        this.track = track;
    }

    public void setKart(DrawableObject kart) {
        this.kart = kart;
    }

    public void setSteeringWheel(DrawableObject steeringWheel) {
        this.steeringWheel = steeringWheel;
    }

    public void setPedals(DrawableObject pedals) {
        this.pedals = pedals;
    }

    public void setTelemetry(DrawableObject telemetry) {
        this.telemetry = telemetry;
    }

    public void setGhost(DrawableObject ghost) {
        this.ghost = ghost;
    }

    public void setCam(DrawableObject cam) {
        this.cam = cam;
    }

    public Point getVehiclePosition() {
        return vehiclePosition;
    }

    public double getVehicleAngle() {
        return vehicleAngle;
    }

    public void setCameras(ArrayList<ICamera> cameras) {
        this.cameras = cameras;
        //call this so we have a cam set
        nextCam();

    }

    public void loadDrawables(Context context) {
        //load track
        track.loadObject(context);

        //load kart
        kart.loadObject(context);
        ghost.loadObject(context);

        //load steering control
        steeringWheel.loadObject(context);
        //load throttle braking control
        pedals.loadObject(context);

        //load camera
        cam.loadObject(context);

    }

    public void drawScene(Canvas canvas) {

        //first pickup current vehicle position and angle
        //so we don't multiple read this
        vehiclePosition = new Point(game.getVehicle().getPosition().getX(),game.getVehicle().getPosition().getY());
        vehicleAngle = game.getVehicle().getAngle();

        int initialCanvas = canvas.save();

        //scale track to fit devices, so we have the exact same meter width on all
        float trackScale = (float)game.getTrack().getScale();
        float scaleFactor = getPixelsPerMeterWidth() / trackScale;
        canvas.scale(scaleFactor,scaleFactor);

        //apply the currently selected camera
        if (currentCamera!=null) {
            currentCamera.applyCamera(canvas);
        }

        //draw scene
        track.draw(canvas);
        ghost.draw(canvas);
        kart.draw(canvas);

        //restore canvas for overlays
        canvas.restoreToCount(initialCanvas);
        //draw controls
        steeringWheel.draw(canvas);
        pedals.draw(canvas);


        //draw telemetry
        telemetry.draw(canvas);

        //draw camera control
        cam.drawObject(canvas);
    }


    public void nextCam() {
        if (cameras==null || cameras.size() == 0) return;
        if (currentCamera == null) {
            currentCamera = cameras.get(0);
        } else {
            int index = cameras.indexOf(currentCamera);
            //if by any chance index is -1 then just return cameras[0]
            index++;
            if (index<cameras.size()) {
                currentCamera = cameras.get(index);
            }
            else currentCamera = cameras.get(0);
        }

    }


    // HELPER METHODS beyond this point

    public float getAspectRatio() {
        if (aspectRatio == null)  aspectRatio = (float)(screenSize.getY() / screenSize.getX());
        return aspectRatio;
    }


    public Point getScreenSize() {
        return this.screenSize;
    }

    public float getWidthInM() {
        return METERS_PER_SCREEN_WIDTH;
    }

    public float getHeightInM() {
        return METERS_PER_SCREEN_WIDTH * getAspectRatio();
    }

    public float getPixelsPerMeterWidth() {
        if (pixelsPerMeterWidth == null) pixelsPerMeterWidth = (float)(screenSize.getX() / getWidthInM());
        return pixelsPerMeterWidth;
    }

    public float getPixelsPerMeterHeight() {
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
