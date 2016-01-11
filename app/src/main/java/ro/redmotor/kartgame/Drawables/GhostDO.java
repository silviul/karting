package ro.redmotor.kartgame.drawables;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import ro.redmotor.kartgame.game.engine.Game;
import ro.redmotor.kartgame.game.utilities.SpatialObject;
import ro.redmotor.kartgame.R;

/**
 * Created by Gabi on 12/26/2015.
 */
public class GhostDO extends DrawableObject {

    private Bitmap ghost;

    public GhostDO(Scene scene, boolean debug) {
        super(scene, debug);
        scene.setGhost(this);
    }

    @Override
    public void loadObject(Context context) {
        ghost = BitmapFactory.decodeResource(context.getResources(), R.drawable.ghost);
    }

    @Override
    protected void drawObject(Canvas canvas) {

        Game game = scene.getGame();
        SpatialObject ghostObj = game.getGhost();
        if (ghostObj != null) {
            float trackScale = (float)game.getTrack().getScale();
            float scaleX = trackScale / (ghost.getWidth() / (float)game.getVehicle().getWidth());
            canvas.scale(scaleX, scaleX);


            float x = (float) ghostObj.getPosition().getX() * trackScale / scaleX;
            float y = (float)(game.getTrack().getTrackHeight() - ghostObj.getPosition().getY()) * trackScale / scaleX;
            canvas.translate(x, y);
            canvas.rotate((float) (ghostObj.getAngle() / Math.PI * 180.0f));

            canvas.drawBitmap(ghost, -ghost.getWidth()/2, -ghost.getHeight()/2, null);
        }

    }

}
