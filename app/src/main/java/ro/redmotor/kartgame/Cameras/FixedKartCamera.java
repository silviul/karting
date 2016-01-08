package ro.redmotor.kartgame.Cameras;

import android.graphics.Canvas;

import ro.redmotor.kartgame.Drawables.Scene;
import ro.redmotor.kartgame.Game.Utilities.Point;

/**
 * Created by Gabi on 1/5/2016.
 */
public class FixedKartCamera implements ICamera {

    protected Scene scene;

    public FixedKartCamera(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void applyCamera(Canvas canvas) {

        applyCameraForVehiclePosition(canvas, scene.getVehiclePosition(), scene.getVehicleAngle());
    }

    protected void applyCameraForVehiclePosition(Canvas canvas, Point vehiclePosition, double angle) {

        float trackScale = (float)scene.getGame().getTrack().getScale();

        float xPos = (float)(vehiclePosition.getX() - scene.getWidthInM()/2)  * trackScale;
        float yPos = (float)(scene.getGame().getTrack().getTrackHeight() - vehiclePosition.getY()
                - scene.getHeightInM()/10*8) * trackScale;
        canvas.translate(-xPos, -yPos);

        float rotateXPos = (float)(vehiclePosition.getX())  * trackScale;
        float rotateYPos = (float)(scene.getGame().getTrack().getTrackHeight() - vehiclePosition.getY()) * trackScale;
        canvas.rotate(-(float) (angle / Math.PI * 180.0f), rotateXPos, rotateYPos);

    }
}
