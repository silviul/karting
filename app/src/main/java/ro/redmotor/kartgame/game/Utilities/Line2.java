package ro.redmotor.kartgame.game.utilities;

/**
 * Created by Gabi on 12/11/2015.
 */
public class Line2 {

    private Point startPoint;
    private Point endPoint;

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public Line2(Point startPoint, Point endPoint)
    {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public double getLength()
    {
            return Math.sqrt((startPoint.getX() - endPoint.getX()) * (startPoint.getX() - endPoint.getX()) +
                    (startPoint.getY() - endPoint.getY()) * (startPoint.getY() - endPoint.getY()));
    }

    public Line2 rotate(double angle)
    {
        angle = -angle;
        double x = startPoint.getX()
                + (endPoint.getX()- startPoint.getX()) * Math.cos(angle)
                - (endPoint.getY() -startPoint.getY())*Math.sin(angle);

        double y = startPoint.getY()
                + (endPoint.getY() - startPoint.getY()) * Math.cos(angle)
                + (endPoint.getX() - startPoint.getX()) * Math.sin(angle);

        return new Line2(new Point(startPoint.getX(), startPoint.getY()), new Point(x, y));
    }
}
