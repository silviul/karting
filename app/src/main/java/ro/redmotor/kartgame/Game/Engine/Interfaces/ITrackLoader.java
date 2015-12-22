package ro.redmotor.kartgame.Game.Engine.Interfaces;

import ro.redmotor.kartgame.Game.Track.Track;

/**
 * Created by Gabi on 12/11/2015.
 */
public interface ITrackLoader
{
    Track loadTrack(String trackName);
}
