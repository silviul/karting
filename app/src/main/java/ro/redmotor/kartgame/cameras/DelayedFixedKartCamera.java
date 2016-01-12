package ro.redmotor.kartgame.cameras;

import android.graphics.Canvas;

import ro.redmotor.kartgame.drawables.Scene;
import ro.redmotor.kartgame.game.utilities.Point;

/**
 * Created by Gabi on 1/5/2016.
 */
public class DelayedFixedKartCamera extends FixedKartCamera implements ICamera {


    private Point lastOne;
    private double lastOneAngle;
    private Point lastOne2;
    private double lastOneAngle2;
    private Point lastOne3;
    private double lastOneAngle3;

    public DelayedFixedKartCamera(Scene scene) {
        super(scene);
    }

    @Override
    public void applyCamera(Canvas canvas) {
        if (lastOne == null) {
            lastOne = scene.getVehiclePosition();
            lastOneAngle = scene.getVehicleAngle();
            lastOne2 = scene.getVehiclePosition();
            lastOneAngle2 = scene.getVehicleAngle();
            lastOne3 = scene.getVehiclePosition();
            lastOneAngle3 = scene.getVehicleAngle();
        }

        applyCameraForVehiclePosition(canvas,lastOne3,lastOneAngle3);
        lastOne3 = new Point(lastOne2.getX(),lastOne2.getY());
        lastOneAngle3 = lastOneAngle2;
        lastOne2 = new Point(lastOne.getX(),lastOne.getY());
        lastOneAngle2 = lastOneAngle;
        lastOne = scene.getVehiclePosition();
        lastOneAngle = scene.getVehicleAngle();

    }
}
