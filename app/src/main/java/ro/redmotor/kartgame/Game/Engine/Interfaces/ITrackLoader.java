package ro.redmotor.kartgame.game.engine.interfaces;

import ro.redmotor.kartgame.game.track.Track;

/**
 * Created by Gabi on 12/11/2015.
 */
public interface ITrackLoader
{
    Track loadTrack(String trackName);
}
