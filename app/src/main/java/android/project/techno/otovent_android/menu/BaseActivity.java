package android.project.techno.otovent_android.menu;

import android.content.Intent;
import android.os.Bundle;
import android.project.techno.otovent_android.R;
import android.project.techno.otovent_android.menu.fragment.DetailEventFragment;
import android.project.techno.otovent_android.menu.fragment.TimeLineFragment;
import android.project.techno.otovent_android.menu.fragment.UserFragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BaseActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchToTimeline();
                    return true;
                case R.id.navigation_promoted_timeline:
                    switchToTimeline();
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.navigation_user:
                    switchToUser();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Memanggil method switchToTimeline untuk membuat pertama kali langsung terbuka timeline
        switchToTimeline();
    }

    public void switchToTimeline() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content, new TimeLineFragment()).commit();
    }

    public void switchToUser() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content, new UserFragment()).commit();
    }
    @Override
    public void onBackPressed() {

    }
}
