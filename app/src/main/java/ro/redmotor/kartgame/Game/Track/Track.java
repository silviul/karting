package ro.redmotor.kartgame.Game.Track;

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


    public CollisionResult detectCollision(Vehicle vehicle) {
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
            vehicle.setAngle(vehicle.getAngle() + Math.PI / 12);
            vehicle.setSpeed(vehicle.getSpeed() * 0.8);
            return CollisionResult.LightCollision;
        }

        if (checkPointCollides(frontRight)) {
            vehicle.setAngle(vehicle.getAngle() - Math.PI / 12);
            vehicle.setSpeed(vehicle.getSpeed() * 0.8);
            return CollisionResult.LightCollision;
        }


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