package android.project.techno.otovent_android.menu;

import android.Manifest;
import android.app.Fragment;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.project.techno.otovent_android.API.Impl.ServiceImpl;
import android.project.techno.otovent_android.API.Service;
import android.project.techno.otovent_android.R;
import android.project.techno.otovent_android.menu.fragment.DetailEventFragment;
import android.project.techno.otovent_android.menu.fragment.NotificationFragment;
import android.project.techno.otovent_android.menu.fragment.SearchFragment;
import android.project.techno.otovent_android.menu.fragment.TimeLineFragment;
import android.project.techno.otovent_android.menu.fragment.UserFragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.Timer;
import java.util.TimerTask;

public class BaseActivity extends AppCompatActivity {
    NotificationManager notificationManager;
    Service service;
    Timer timer;
    LocationManager mLocationManager;

    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        service = new ServiceImpl();

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        hitungJarak(location,new LatLng(-31,151),500);
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });

        //Thread run every 1s to get notification
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                service.getNotificationForUserFromBackend(1L, BaseActivity.this, notificationManager, "notification",
                        "Notification", "There are some news for you");
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
            } else if (menuFragment.equalsIgnoreCase("notification")){
                switchToNotification();
            }
        }
    }

    public void switchToDetailEvent() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content, new DetailEventFragment()).commit();
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
        NotificationFragment notificationFragment = new NotificationFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.content,notificationFragment)
                .commit();
    }

    public void switchToSearch(){
        SearchFragment fragment = new SearchFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.content,fragment)
                .commit();
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
                case R.id.navigation_search:
                    switchToSearch();
                    return true;
                case R.id.navigation_promoted_timeline:
                    switchToDetailEvent();
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

    private void hitungJarak(Location locationUser, LatLng event, float radius){
        Location locationEvent = new Location(LocationManager.GPS_PROVIDER);
            locationEvent.setLatitude(event.latitude);
            locationEvent.setLongitude(event.longitude);
        if (locationUser.distanceTo(locationEvent) > radius) {
            Toast.makeText(this, "Kowadkwaodkowakdodkawowk lu diluar", Toast.LENGTH_SHORT).show();
            Log.e("wkadowdka","diluar");
        } else {
            Toast.makeText(this, "Kowadkwaodkowakdodkawowk lu didialem", Toast.LENGTH_SHORT).show();
            Log.e("wkadowdka","didalam");
        }
    }
}
