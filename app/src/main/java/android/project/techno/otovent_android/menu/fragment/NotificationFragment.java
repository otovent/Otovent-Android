package android.project.techno.otovent_android.menu.fragment;

import android.app.NotificationManager;
import android.project.techno.otovent_android.API.Impl.ServiceImpl;
import android.project.techno.otovent_android.API.Service;
import android.project.techno.otovent_android.Adapter.NotificationAdapter;
import android.project.techno.otovent_android.Adapter.util.ClickListener;
import android.project.techno.otovent_android.Adapter.util.DividerItemDecoration;
import android.project.techno.otovent_android.Adapter.util.RecyclerTouchListener;
import android.project.techno.otovent_android.model.Notification;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.project.techno.otovent_android.R;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by N-REW on 03/11/2017.
 */
public class NotificationFragment extends Fragment{
    private Service service;
    private NotificationManager notificationManager;
    private List<Notification> notificationList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;

    public NotificationFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        final View view =  inflater.inflate(R.layout.fragment_notification, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);

        notificationAdapter = new NotificationAdapter(notificationList);

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(),LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(notificationAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(view.getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                notificationList.get(position);
                Toast.makeText(view.getContext(), "wulullulu + "+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareNotificationTest();

        return view;
    }

    private void prepareNotificationTest(){
        Notification notification = new Notification("","satu","dua");
        notificationList.add(notification);

        notification = new Notification("","dua","tiga");
        notificationList.add(notification);

        notification = new Notification("","dua","tiga");
        notificationList.add(notification);

        notification = new Notification("","dua","tiga");
        notificationList.add(notification);

        notification = new Notification("","dua","tiga");
        notificationList.add(notification);

        notification = new Notification("","dua","tiga");
        notificationList.add(notification);

        notification = new Notification("","dua","tiga");
        notificationList.add(notification);

        notification = new Notification("","dua","tiga");
        notificationList.add(notification);
    }
}
