package ro.redmotor.kartgame;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import ro.redmotor.kartgame.drawables.Scene;
import ro.redmotor.kartgame.game.engine.Game;
import ro.redmotor.kartgame.game.engine.interfaces.IGameListener;
import ro.redmotor.kartgame.game.track.Track;
import ro.redmotor.kartgame.sound.SoundPlayer;

/**
 * Created by Gabi on 12/12/2015.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback, IGameListener {

    private GameThread gameThread;
    private Scene scene;
    private Game game;
    private IGameControl steeringControl;
    private IGameControl throttleBrakingControl;
    private IGameControl camControl;

    //sound
    private SoundPlayer soundPlayer;

    //see if we're pressing the clutch
    //we don't have an IGameController for this yet
    //not used for now
    private boolean clutchIn = false;

    public GamePanel(Context context,
                     Scene scene,
                     IGameControl steeringControl,
                     IGameControl throttleBrakingControl,
                     IGameControl camControl,
                     SoundPlayer soundPlayer,
                     Game game) {
        super(context);

        this.scene = scene;
        this.steeringControl = steeringControl;
        this.throttleBrakingControl = throttleBrakingControl;
        this.camControl = camControl;
        this.soundPlayer = soundPlayer;
        this.game = game;


        getHolder().addCallback(this);
        setFocusable(true);


    }

    public void LoadAssets() {

        //load sound
        soundPlayer.loadSounds(getContext());

        //load game assets
        game.loadTrack("");
        game.loadVehicle();

        scene.loadDrawables(getContext());
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        LoadAssets();

        try {
            game.startNewGame();
            //start game thread
            gameThread = new GameThread(getHolder(), this);
            gameThread.setRunning(true);
            gameThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        soundPlayer.stopEngine();
        boolean retry = true;
        while (retry) {
            try {
                gameThread.setRunning(false);
                gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        final int action = ev.getAction();

        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                System.out.println("Action_Down");
                float x = ev.getX();
                float y = ev.getY();
                throttleBrakingControl.checkPointerDown(ev.getPointerId(0), x, y);
                steeringControl.checkPointerDown(ev.getPointerId(0), x, y);
                camControl.checkPointerDown(ev.getPointerId(0), x, y);
                if (camControl.getSelection()==1) scene.nextCam();
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                //the move action doesn't trigger for a specific pointer id
                //so we need to check all pointers (there's one move event for all movements)
                int pointerCount = ev.getPointerCount();
                for (int i = 0; i < pointerCount; i++) {
                    float x = ev.getX(i);
                    float y = ev.getY(i);
                    steeringControl.checkPointerMove(ev.getPointerId(i), x, y);
                }
                break;
            }

            case MotionEvent.ACTION_UP: {
                final int pointerId = ev.getPointerId(0);
                float x = ev.getX();
                float y = ev.getY();
                throttleBrakingControl.checkPointerUp(pointerId, x, y);
                steeringControl.checkPointerUp(pointerId, x, y);
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {
                final int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK)
                        >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = ev.getPointerId(pointerIndex);

                float x = ev.getX(pointerIndex);
                float y = ev.getY(pointerIndex);
                throttleBrakingControl.checkPointerUp(pointerId, x, y);
                steeringControl.checkPointerUp(pointerId, x, y);

                break;
            }

            case MotionEvent.ACTION_POINTER_DOWN: {
                System.out.println("Action_Pointer_Down");
                final int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK)
                        >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = ev.getPointerId(pointerIndex);

                float x = ev.getX(pointerIndex);
                float y = ev.getY(pointerIndex);
                throttleBrakingControl.checkPointerDown(pointerId, x, y);
                steeringControl.checkPointerDown(pointerId, x, y);
                camControl.checkPointerDown(pointerId, x, y);
                if (camControl.getSelection()==1) scene.nextCam();

                break;
            }
        }


        return true;
    }

    public void update() {
        try {

            //get selections from the user
            double throttleBrake = throttleBrakingControl.getSelection();
            double braking = throttleBrake < 0 ? throttleBrake : 0;
            double throttle = throttleBrake > 0 ? throttleBrake : 0;

            //apply selections to game
            game.getVehicle().getPowertrain().setClutchIn(clutchIn);
            game.getVehicle().setSteering(steeringControl.getSelection());
            game.getVehicle().setThrottle(throttle);
            game.getVehicle().setBraking(braking);
            //do a game loop update
            game.update();

            //play the lame sound effects
            soundPlayer.playEngine(game.getVehicle().getPowertrain().getEngine().getRpm());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        //uncomment this if you want to zoom out the scene for debugging
        //canvas.scale(0.1f, 0.1f);
        //canvas.translate(500/0.80f,500/0.80f);
        super.draw(canvas);

        scene.drawScene(canvas);

    }


    @Override
    public void lapCompleted(int lapNo, double lapTime) {
        System.out.println("Lap completed " + lapNo);
    }

    @Override
    public void vehicleCollided(Track.CollisionResult result) {
        if (result == Track.CollisionResult.LightCollision) {
            Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(30);
        }
    }

    @Override
    public void lapStarted(int lapNo) {
        System.out.println("Lap started " + lapNo);
    }
}
