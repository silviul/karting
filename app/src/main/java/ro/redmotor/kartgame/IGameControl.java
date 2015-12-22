package ro.redmotor.kartgame;

/**
 * Created by Gabi on 12/16/2015.
 */
public interface IGameControl {
    void checkPointerDown(int pointerId, float x, float y);
    void checkPointerUp(int pointerId, float x, float y);
    void checkPointerMove(int pointerId, float x, float y);
    double getSelection();
}
