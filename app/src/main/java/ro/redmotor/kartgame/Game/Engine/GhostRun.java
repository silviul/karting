package ro.redmotor.kartgame.Game.Engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

import ro.redmotor.kartgame.Game.Utilities.SpatialObject;

/**
 * Created by Gabi on 12/26/2015.
 */
public class GhostRun {

    private Hashtable<Long, SpatialObject> ghostRun = new Hashtable();
    private int previousIndex = 0;

    public void updateGhost(long timestamp, SpatialObject spatialObject) {
        ghostRun.put(timestamp, spatialObject);
    }

    public SpatialObject getGhostForTimestamp(long timestamp) {
        ArrayList<Long> list = new ArrayList(ghostRun.keySet());
        Collections.sort(list);

        //check if we're below the index, then reset previous index
        if (list.get(previousIndex) > timestamp) previousIndex = 0;

        for (int i = previousIndex + 1; i < list.size(); i++)
        {
            long prevKey = list.get(i - 1);
            long key = list.get(i);
            if (timestamp < key && timestamp >= prevKey)
            {
                previousIndex = i - 1;
                return ghostRun.get(prevKey);
            }
        }

        return null;
    }


    public boolean betterThan(GhostRun compareTo) {
        //if we don't have a best one, any is better
        if (compareTo == null) return true;
        ArrayList thisGhostRun = new ArrayList(this.ghostRun.keySet());
        Collections.sort(thisGhostRun);
        ArrayList comparisonGhostRun = new ArrayList(compareTo.ghostRun.keySet());
        Collections.sort(comparisonGhostRun);
        Long thisRunTime = thisGhostRun.size() > 0 ? (Long)thisGhostRun.get(thisGhostRun.size() -1) : 0l;
        Long comparisonRunTime = comparisonGhostRun.size() > 0 ? (Long)comparisonGhostRun.get(comparisonGhostRun.size() -1) : 0l;
        return thisRunTime < comparisonRunTime;
    }
}
