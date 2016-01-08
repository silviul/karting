package ro.redmotor.kartgame.Cameras;

import android.graphics.Canvas;

import ro.redmotor.kartgame.Drawables.Scene;

/**
 * Created by Gabi on 1/5/2016.
 */
public class FixedTrackCamera implements ICamera {

    private Scene scene;

    public FixedTrackCamera(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void applyCamera(Canvas canvas) {
        float trackScale = (float)scene.getGame().getTrack().getScale();

        float xPos = (float)(scene.getGame().getVehicle().getPosition().getX() - scene.getWidthInM()/2)  * trackScale;
        float yPos = (float)(scene.getGame().getTrack().getTrackHeight() - scene.getGame().getVehicle().getPosition().getY()
                - scene.getHeightInM()/2) * trackScale;
        canvas.translate(-xPos, -yPos);

    }
}
