package android.project.techno.otovent_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Miura on 10/29/2017.
 */

public class timeline_basic extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.empty_timeline);
    }

    public void Profile(View v) {
        Intent i = new Intent();
        i.setClass(this,user_profile.class);
        startActivity(i);
    }
}
