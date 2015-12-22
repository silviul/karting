package ro.redmotor.kartgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import ro.redmotor.kartgame.Game.Engine.Interfaces.ITrackLoader;
import ro.redmotor.kartgame.Game.Engine.Interfaces.IVehicleLoader;
import ro.redmotor.kartgame.Game.Track.Track;
import ro.redmotor.kartgame.Game.Utilities.Line2;
import ro.redmotor.kartgame.Game.Utilities.Point;
import ro.redmotor.kartgame.Game.Vehicle.Vehicle;

/**
 * Created by Gabi on 12/15/2015.
 * Loads track and vehicle from resource files
 */
public class AssetLoader implements ITrackLoader, IVehicleLoader {

    private Context context;

    public AssetLoader(Context context) {
        this.context = context;
    }

    @Override
    public Track loadTrack(String trackName) {
        //load the track matrix for collision detection
        Bitmap trackLayout = BitmapFactory.decodeResource(context.getResources(), R.drawable.redmotor_bw);
        int[][] trackL = new int[trackLayout.getWidth()][];
        for (int x = 0; x < trackLayout.getWidth(); x++) {
            trackL[x] = new int[trackLayout.getHeight()];
            for (int y = 0; y < trackLayout.getHeight(); y++) {
                trackL[x][y] = trackLayout.getPixel(x,y) == Color.BLACK ? 1 : 0;
            }
        }
        //linie in plus

        //Initialize an new track
        Track track = new Track(trackL);
        track.setFinishLine(new Line2(new Point(33.5, 10), new Point(33.5, 16.5)));
        track.setStartAngle(-Math.PI/2);
        track.setScale(20);
        track.setStartPosition(new Point(35,11));

        return track;
    }

    @Override
    public Vehicle loadVehicle() {
        Vehicle vehicle = new Vehicle(2,1.3,1.8,170);
        return vehicle;
    }



}
