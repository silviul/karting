package ro.redmotor.kartgame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Gabi on 12/12/2015.
 * Thread to update game and UI
 */
public class GameThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    public GameThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {

        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;

    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        long startTime;
        long loopTimeMills;
        //how much time for the given fps on each frame
        long targetTimeMills = 1000/30;
        long waitTimeMills;

        while(running) {
            startTime = System.nanoTime();

            //do stuff
            try {
                canvas = this.surfaceHolder.lockCanvas();
                if (canvas!=null) {
                    synchronized (surfaceHolder) {
                        this.gamePanel.update();
                        this.gamePanel.draw(canvas);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            //see how much time the frame took
            loopTimeMills = (System.nanoTime() - startTime) / 1000000;
            //and how much we have to wait to achieve the required fps
            waitTimeMills = (targetTimeMills - loopTimeMills);

            //if (waitTimeMills<0) System.out.println("waitTimeMills:"+waitTimeMills);
            //sleep
            try {
                if (waitTimeMills > 0) {
                    sleep(waitTimeMills);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

}
