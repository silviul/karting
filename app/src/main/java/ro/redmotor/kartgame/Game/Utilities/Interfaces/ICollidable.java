package ro.redmotor.kartgame.Game.Utilities.Interfaces;

import ro.redmotor.kartgame.Game.Utilities.Point;

/**
 * Created by Gabi on 12/11/2015.
 */
public interface ICollidable {
    Point getPosition();
    double getAngle();
    double getLength();
    double getWidth();
    double getSpeed();

}
