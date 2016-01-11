package ro.redmotor.kartgame.game.engine;

import ro.redmotor.kartgame.game.utilities.SpatialObject;
import ro.redmotor.kartgame.game.vehicle.Vehicle;

/**
 * Created by Gabi on 12/26/2015.
 */
public class GhostTracker {

    private GhostRun currentGhostRun;
    private GhostRun bestGhostRun;

    public void startGhostRun() {
        currentGhostRun = new GhostRun();
    }

    public void updateGhostRun(long timestamp, Vehicle vehicle) {
        if (currentGhostRun != null) {
            currentGhostRun.updateGhost(timestamp, vehicle.toSpatialObject());
        }
    }

    public void endGhostRun() {
        if (currentGhostRun.betterThan(bestGhostRun)) {
            bestGhostRun = currentGhostRun;
        }
    }

    public SpatialObject getGhost(long timestamp) {
        if (bestGhostRun == null) return null;
        return bestGhostRun.getGhostForTimestamp(timestamp);
    }

    public void reset() {
        this.bestGhostRun = null;
        this.currentGhostRun = null;
    }
}
