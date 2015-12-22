package ro.redmotor.kartgame.Game.Engine;

import ro.redmotor.kartgame.Game.Engine.Interfaces.IGameListener;
import ro.redmotor.kartgame.Game.Engine.Interfaces.ITrackLoader;
import ro.redmotor.kartgame.Game.Engine.Interfaces.IVehicleLoader;
import ro.redmotor.kartgame.Game.Track.Interfaces.ILapListener;
import ro.redmotor.kartgame.Game.Track.LapChecker;
import ro.redmotor.kartgame.Game.Track.LapsManager;
import ro.redmotor.kartgame.Game.Track.Track;
import ro.redmotor.kartgame.Game.Vehicle.Vehicle;

/**
 * Created by Gabi on 12/11/2015.
 */
public class Game implements ILapListener
{
    private Vehicle vehicle;
    private Track track;
    private IGameListener listener;
    private ITrackLoader trackLoader;
    private IVehicleLoader vehicleLoader;
    private LapChecker lapChecker;
    private LapsManager lapsManager;
    private boolean pause;


    public Vehicle getVehicle() {
        return vehicle;
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

    public Game(IGameListener listener, ITrackLoader trackLoader, IVehicleLoader vehicleLoader)
    {
        this.listener = listener != null ? listener : new NullGameListener();
        this.trackLoader = trackLoader;
        this.vehicleLoader = vehicleLoader;
        this.lapChecker = new LapChecker();
        this.lapsManager = new LapsManager(this);
    }

    public void loadTrack(String trackName)
    {
        track = trackLoader.loadTrack(trackName);
    }

    public void loadVehicle()
    {
        vehicle = vehicleLoader.loadVehicle();
    }

    public void startNewGame () throws Exception {
        if (track == null) throw new Exception("No track loaded. Use Game.LoadTrack(trackName)");
        if (vehicle == null) throw new Exception("No vehicle loaded. Use Game.LoadVehicle()");

        vehicle.setPosition(track.getStartPosition());
        vehicle.setAngle(track.getStartAngle());
        lapsManager.reset();

    }

    public void update()
    {
        if (pause) return;
        vehicle.update();

        Track.CollisionResult result = track.detectCollision(vehicle);
        listener.vehicleCollided(result);

        handleLaps();

    }

    private void handleLaps()
    {
        Long detectionTime = lapChecker.checkDetection(vehicle.getPosition(), track.getFinishLine());
        if (detectionTime!=null)
        {
            lapsManager.newDetection(detectionTime);
        }

    }

    public void lapStarted(int lapNo, long lapStarted)
    {
        listener.lapStarted(lapNo);
    }

    public void lapFinished(int lapNo, double lapTime)
    {
        listener.lapCompleted(lapNo, lapTime);
    }

}
