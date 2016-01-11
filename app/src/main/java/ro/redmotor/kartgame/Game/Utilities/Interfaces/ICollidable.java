package ro.redmotor.kartgame.game.utilities.interfaces;

import ro.redmotor.kartgame.game.utilities.Point;

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
