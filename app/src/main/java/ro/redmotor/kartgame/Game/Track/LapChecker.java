package ro.redmotor.kartgame.Game.Track;

import ro.redmotor.kartgame.Game.Utilities.Line2;
import ro.redmotor.kartgame.Game.Utilities.Point;

/**
 * Created by Gabi on 12/11/2015.
 */
public class LapChecker
{
    private Point previousPosition;
    private Long detectionTime;

    public Long checkDetection(Point vehiclePosition, Line2 finishLine)
    {
        boolean isDetected = false;

    //if it's the first check we don't have a vehicle line yet, return no detection
    if (previousPosition == null)
    {
        previousPosition = new Point(vehiclePosition.getX(), vehiclePosition.getY());
        return null;
    }

    //compute vehicle line between two checks
    Line2 vehicleLine = new Line2(previousPosition, vehiclePosition);
    boolean crossingFinishLine = doBoundingBoxesIntersect(vehicleLine, finishLine);
     if (crossingFinishLine)
    {
        //find out if it's the first detection or
        //if it not but we've already detected it less than 3 seconds ago
        if (detectionTime == null || (System.currentTimeMillis() - detectionTime) > 3000)
        {
            detectionTime = System.currentTimeMillis();
            isDetected = true;
        }
    }
    previousPosition = new Point(vehiclePosition.getX(), vehiclePosition.getY());

    return isDetected ? detectionTime : null;

}


    /// <summary>
    /// Simple bounds checking for now (should be faster and we'll do for our purposes)
    /// </summary>
    /// <param name="a"></param>
    /// <param name="b"></param>
    /// <returns></returns>
    public boolean doBoundingBoxesIntersect(Line2 l1, Line2 l2)
    {
        return l1.getEndPoint().getX() <= l2.getEndPoint().getX()
                && l1.getStartPoint().getX() >= l2.getStartPoint().getX()
                && l1.getStartPoint().getY() <= l2.getEndPoint().getY()
                && l1.getEndPoint().getY() >= l2.getStartPoint().getY();
    }
}