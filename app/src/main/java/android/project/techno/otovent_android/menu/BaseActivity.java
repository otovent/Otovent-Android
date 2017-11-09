package android.project.techno.otovent_android.menu;

import android.app.NotificationManager;
import android.os.Bundle;
import android.project.techno.otovent_android.API.Impl.ServiceImpl;
import android.project.techno.otovent_android.API.Service;
import android.project.techno.otovent_android.R;
import android.project.techno.otovent_android.menu.fragment.NotificationFragment;
import android.project.techno.otovent_android.menu.fragment.TimeLineFragment;
import android.project.techno.otovent_android.menu.fragment.UserFragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class BaseActivity extends AppCompatActivity {
    NotificationManager notificationManager;
    Service service;
    Timer timer;

    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        service = new ServiceImpl();

        //Thread run every 1s to get notification
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                service.pushNotification(BaseActivity.this,notificationManager,"user",
                        "Notification","There are some news for you");
            }
        },0,10000);


        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // Memanggil method switchToTimeline untuk membuat pertama kali langsung terbuka timeline
        switchToTimeline();
        String menuFragment = getIntent().getStringExtra("fragment");
        if (menuFragment != null){
            if (menuFragment.equalsIgnoreCase("User")){
                switchToUser();
            }
        }
    }

    public void switchToTimeline() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content, new TimeLineFragment()).commit();
    }

    public void switchToUser() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content, new UserFragment()).commit();
    }

    public void switchToNotification(){
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content, new NotificationFragment()).commit();
    }

    @Override
    public void onBackPressed() {

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchToTimeline();
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    switchToNotification();
                    return true;
                case R.id.navigation_user:
                    switchToUser();
                    return true;
            }
            return false;
        }

    };
}
