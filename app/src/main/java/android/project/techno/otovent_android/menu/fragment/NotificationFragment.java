package android.project.techno.otovent_android.menu.fragment;

import android.app.NotificationManager;
import android.project.techno.otovent_android.API.Impl.ServiceImpl;
import android.project.techno.otovent_android.API.Service;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.project.techno.otovent_android.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by N-REW on 03/11/2017.
 */
public class NotificationFragment extends Fragment{
    private Service service;
    private NotificationManager notificationManager;

    public NotificationFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        final View view =  inflater.inflate(R.layout.fragment_notification, container, false);
        return view;
    }
}
