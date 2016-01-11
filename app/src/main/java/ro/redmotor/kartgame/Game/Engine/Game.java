package ro.redmotor.kartgame.game.engine;

import ro.redmotor.kartgame.game.engine.interfaces.IGameListener;
import ro.redmotor.kartgame.game.engine.interfaces.ITrackLoader;
import ro.redmotor.kartgame.game.engine.interfaces.IVehicleLoader;
import ro.redmotor.kartgame.game.track.interfaces.ILapListener;
import ro.redmotor.kartgame.game.track.LapChecker;
import ro.redmotor.kartgame.game.track.LapsManager;
import ro.redmotor.kartgame.game.track.Track;
import ro.redmotor.kartgame.game.utilities.SpatialObject;
import ro.redmotor.kartgame.game.vehicle.Vehicle;
import ro.redmotor.kartgame.preferences.interfaces.GamePreferences;

/**
 * Created by Gabi on 12/11/2015.
 */
public class Game implements ILapListener {
    private Vehicle vehicle;
    private Track track;
    private IGameListener listener;
    private ITrackLoader trackLoader;
    private IVehicleLoader vehicleLoader;
    private LapChecker lapChecker;
    private LapsManager lapsManager;
    private GhostTracker ghostTracker;
    private boolean pause;


    public Vehicle getVehicle() {
        return vehicle;
    }

    public SpatialObject getGhost() {
        if (lapsManager.getCurrentLap() < 1) return null;
        return ghostTracker.getGhost(System.currentTimeMillis() - lapsManager.getCurrentLapStartTime());
    }

    public Track getTrack() {
        return track;
    }

    public void setListener(IGameListener listener) {
        this.listener = listener;
    }

    public LapsManager getLapsManager() {
        return lapsManager;
    }

    public void resumeGame() {
        this.pause = false;
    }

    public void pauseGame() {
        this.pause = true;
    }

    public Game(IGameListener listener, ITrackLoader trackLoader,
                IVehicleLoader vehicleLoader, GamePreferences gamePreferences) {
        this.listener = listener != null ? listener : new NullGameListener();
        this.trackLoader = trackLoader;
        this.vehicleLoader = vehicleLoader;
        this.lapChecker = new LapChecker();
        this.lapsManager = new LapsManager(this, gamePreferences);
        this.ghostTracker = new GhostTracker();
    }

    public void loadTrack(String trackName) {
        track = trackLoader.loadTrack(trackName);
    }

    public void loadVehicle() {
        vehicle = vehicleLoader.loadVehicle();
    }

    public void startNewGame() throws Exception {
        if (track == null) throw new Exception("No track loaded. Use Game.LoadTrack(trackName)");
        if (vehicle == null) throw new Exception("No vehicle loaded. Use Game.LoadVehicle()");

        vehicle.setPosition(track.getStartPosition());
        vehicle.setAngle(track.getStartAngle());
        lapsManager.reset();
        ghostTracker.reset();

    }

    public void update() {
        if (pause) return;
        vehicle.update();

        Track.CollisionResult result = track.detectCollision(vehicle);
        listener.vehicleCollided(result);

        handleLaps();
        if (lapsManager.getCurrentLapStartTime()!=null) {
            ghostTracker.updateGhostRun(System.currentTimeMillis() - lapsManager.getCurrentLapStartTime(), vehicle);
        }

    }

    private void handleLaps() {
        Long detectionTime = lapChecker.checkDetection(vehicle.getPosition(), track.getFinishLine());
        if (detectionTime != null) {
            lapsManager.newDetection(detectionTime);
        }

    }


    public void lapStarted(int lapNo, long lapStarted) {
        ghostTracker.startGhostRun();
        listener.lapStarted(lapNo);
    }

    public void lapFinished(int lapNo, double lapTime) {
        ghostTracker.endGhostRun();
        listener.lapCompleted(lapNo, lapTime);
    }

}
