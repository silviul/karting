package ro.redmotor.kartgame.Game.Utilities;

/**
 * Created by Gabi on 12/26/2015.
 */
public class SpatialObject {

    protected Point position;
    protected double angle;

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
