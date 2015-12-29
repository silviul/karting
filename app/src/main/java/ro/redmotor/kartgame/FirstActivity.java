package ro.redmotor.kartgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class FirstActivity extends AppCompatActivity {

    private View playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        playButton = findViewById(R.id.play_button);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    public void goPlay(View view) {
        playButton.setEnabled(false);
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }
}
