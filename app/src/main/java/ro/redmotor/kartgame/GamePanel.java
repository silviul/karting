package ro.redmotor.kartgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import ro.redmotor.kartgame.Drawables.DrawableObject;
import ro.redmotor.kartgame.Game.Engine.Game;
import ro.redmotor.kartgame.Game.Engine.Interfaces.IGameListener;
import ro.redmotor.kartgame.Game.Track.Track;
import ro.redmotor.kartgame.Sound.SoundPlayer;

/**
 * Created by Gabi on 12/12/2015.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback, IGameListener {

    private GameThread gameThread;
    private Game game;
    private DrawableObject track;
    private DrawableObject kart;
    private DrawableObject steeringWheel;
    private DrawableObject pedals;
    private DrawableObject telemetry;
    private IGameControl steeringControl;
    private IGameControl throttleBrakingControl;


    //sound
    private SoundPlayer soundPlayer;

    //see if we're pressing the clutch
    //we don't have an IGameController for this yet
    //not used for now
    private boolean clutchIn = false;

    public GamePanel(Context context,
                     DrawableObject track,
                     DrawableObject kart,
                     DrawableObject steeringWheel,
                     DrawableObject pedals,
                     DrawableObject telemetry,
                     IGameControl steeringControl,
                     IGameControl throttleBrakingControl,
                     SoundPlayer soundPlayer,
                     Game game) {
        super(context);

        this.track = track;
        this.kart = kart;
        this.steeringWheel = steeringWheel;
        this.pedals = pedals;
        this.telemetry = telemetry;
        this.steeringControl = steeringControl;
        this.throttleBrakingControl = throttleBrakingControl;
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

        //load track
        track.loadObject(getContext());

        //load kart
        kart.loadObject(getContext());

        //load steering control
        steeringWheel.loadObject(getContext());
        //load throttle braking control
        pedals.loadObject(getContext());

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        LoadAssets();

        try {
            game.startNewGame();
            //start game thread
            gameThread =  new GameThread(getHolder(), this);
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
        while(retry) {
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

                break;
            }
        }


        return true;
    }

    public void update() {
        try {

            //get selections from the user
            double throttleBrake = throttleBrakingControl.getSelection();
            double braking =  throttleBrake < 0 ? throttleBrake : 0;
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
        //canvas.scale(0.25f, 0.25f);
        super.draw(canvas);

        //draw assets
        track.draw(canvas);
        kart.draw(canvas);

        //draw controls
        steeringWheel.draw(canvas);
        pedals.draw(canvas);


        //draw telemetry
        telemetry.draw(canvas);

    }


    @Override
    public void lapCompleted(int lapNo, double lapTime) {
        System.out.println("Lap completed " + lapNo);
    }

    @Override
    public void vehicleCollided(Track.CollisionResult result) {
        if (result == Track.CollisionResult.LightCollision) {
            Vibrator vibrator = (Vibrator)getContext().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(30);
        }
    }

    @Override
    public void lapStarted(int lapNo) {
        System.out.println("Lap started "+lapNo);
    }
}
