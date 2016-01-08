package ro.redmotor.kartgame.Game.Track;

import android.util.Log;

import ro.redmotor.kartgame.Game.Utilities.Line2;
import ro.redmotor.kartgame.Game.Utilities.Point;
import ro.redmotor.kartgame.Game.Vehicle.Vehicle;

/**
 * Created by Gabi on 12/11/2015.
 */
public class Track {
    private LapChecker lapChecker;

    private int[][] trackLayout;
    private Point startPosition;
    private double startAngle;
    private Line2 finishLine;
    private double scale = 10;

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public Point getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Point startPosition) {
        this.startPosition = startPosition;
    }

    public double getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(double startAngle) {
        this.startAngle = startAngle;
    }

    public Line2 getFinishLine() {
        return finishLine;
    }

    public void setFinishLine(Line2 finishLine) {
        this.finishLine = finishLine;
    }

    public Track(int[][] trackLayout) {
        this.trackLayout = trackLayout;
        this.lapChecker = new LapChecker();
    }

    //TODO: do this properly
    // 0=no previous collision, 1=left collision, 2= right collision
    private int lastCollision = 0;


    public CollisionResult detectCollision(Vehicle vehicle) {

        double halfWidth = vehicle.getWidth()/2;
        double halfHeight = vehicle.getLength()/2;

        Point frontLeft = new Line2(vehicle.getPosition(),
                new Point(vehicle.getPosition().getX() - halfWidth, vehicle.getPosition().getY() + halfHeight))
                .rotate(vehicle.getAngle()).getEndPoint();

        Point frontRight = new Line2(vehicle.getPosition(),
                new Point(vehicle.getPosition().getX() + halfWidth, vehicle.getPosition().getY() + halfHeight))
                .rotate(vehicle.getAngle()).getEndPoint();

        Point rearRight = new Line2(vehicle.getPosition(),
                new Point(vehicle.getPosition().getX() + halfWidth, vehicle.getPosition().getY() - halfHeight))
                .rotate(vehicle.getAngle()).getEndPoint();

        Point rearLeft = new Line2(vehicle.getPosition(),
                new Point(vehicle.getPosition().getX() - halfWidth, vehicle.getPosition().getY() - halfHeight))
                .rotate(vehicle.getAngle()).getEndPoint();;

        if (checkPointCollides(frontLeft)) {
            System.out.println("collides front left");
            //force same side collision if we keep colliding
            if (lastCollision == 2) {
                vehicle.setAngle(vehicle.getAngle() - Math.PI / 12);
            } else {
                lastCollision = 1;
                vehicle.setAngle(vehicle.getAngle() + Math.PI / 12);
            }
            vehicle.setSpeed(vehicle.getSpeed() * 0.7);
            return CollisionResult.LightCollision;
        }

        if (checkPointCollides(frontRight)) {
            System.out.println("collides front right");
            //force same side collision if we keep colliding
            if (lastCollision == 1) {
                vehicle.setAngle(vehicle.getAngle() + Math.PI / 12);
            } else {
                lastCollision = 2;
                vehicle.setAngle(vehicle.getAngle() - Math.PI / 12);
            }
            vehicle.setSpeed(vehicle.getSpeed() * 0.7);
            return CollisionResult.LightCollision;
        }

        lastCollision = 0;
        return CollisionResult.NoCollision;
    }


    public CollisionResult detectCollision2(Vehicle vehicle) {

        Point frontLeft = new Line2(vehicle.getPosition(),
                new Point(vehicle.getPosition().getX()+ 0.2, vehicle.getPosition().getY() + vehicle.getLength()))
                .rotate(vehicle.getAngle()).getEndPoint();

        Point frontRight = new Line2(vehicle.getPosition(),
                new Point(vehicle.getPosition().getX() + vehicle.getWidth() - 0.2, vehicle.getPosition().getY() + vehicle.getLength()))
                .rotate(vehicle.getAngle()).getEndPoint();

        Point rearRight = new Line2(vehicle.getPosition(),
                new Point(vehicle.getPosition().getX() + vehicle.getWidth(), vehicle.getPosition().getY()))
                .rotate(vehicle.getAngle()).getEndPoint();

        Point rearLeft = vehicle.getPosition();

        if (checkPointCollides(frontLeft)) {
            System.out.println("collides front left");
            //force same side collision if we keep colliding
            if (lastCollision == 2) {
                vehicle.setAngle(vehicle.getAngle() - Math.PI / 12);
            } else {
                lastCollision = 1;
                vehicle.setAngle(vehicle.getAngle() + Math.PI / 12);
            }
            vehicle.setSpeed(vehicle.getSpeed() * 0.7);
            return CollisionResult.LightCollision;
        }

        if (checkPointCollides(frontRight)) {
            System.out.println("collides front right");
            //force same side collision if we keep colliding
            if (lastCollision == 1) {
                vehicle.setAngle(vehicle.getAngle() + Math.PI / 12);
            } else {
                lastCollision = 2;
                vehicle.setAngle(vehicle.getAngle() - Math.PI / 12);
            }
            vehicle.setSpeed(vehicle.getSpeed() * 0.7);
            return CollisionResult.LightCollision;
        }

        lastCollision = 0;
        return CollisionResult.NoCollision;
    }

    public boolean checkPointCollides(Point p) {
        int height = trackLayout[0].length;
        int width = trackLayout.length;
        int x = (int) ((p.getX() * scale) - 1);
        int y = (int) (height - (p.getY() * scale) - 1);
        if (x >= width) return true;
        if (y >= height) return true;
        try {
            return trackLayout[x][y] == 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public double getTrackWidth() {
        return this.trackLayout.length / scale;
    }

    public double getTrackHeight() {
        return this.trackLayout[0].length / scale;
    }


    public enum CollisionResult {
        NoCollision,
        Totalled,
        LightCollision
    }
}