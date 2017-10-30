package android.project.techno.otovent_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Miura on 10/29/2017.
 */

public class user_profile extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.user_profile);
    }
}
