package ro.redmotor.kartgame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //no title
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        //make it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        GameBuilder builder = new GameBuilder(getBaseContext());
        setContentView(builder.buildGamePanel(false));
    }

}
