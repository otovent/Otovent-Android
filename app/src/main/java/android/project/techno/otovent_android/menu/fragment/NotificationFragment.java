package android.project.techno.otovent_android.menu.fragment;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.SharedPreferences;
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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.droidbyme.dialoglib.DroidDialog;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by N-REW on 03/11/2017.
 */
public class NotificationFragment extends Fragment{
    private Service service;
    private NotificationManager notificationManager;
    private List<Notification> notificationList;
    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View root;
    private Long idUser;

    public NotificationFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        final View view =  inflater.inflate(R.layout.fragment_notification, container, false);
        root = inflater.inflate(R.layout.fragment_notification, container, false);
        notificationList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);

        notificationAdapter = new NotificationAdapter(notificationList);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.activity_main_swipe_refresh_layout);

        service = new ServiceImpl();

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(view.getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(),LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(notificationAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(view.getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(final View view, int position) {
                Notification notification = notificationList.get(position);
                if (notification.getType().equalsIgnoreCase("FRIEND_REQUEST")){
//                    Toast.makeText(view.getContext(), "Friend Request", Toast.LENGTH_SHORT).show();
                    new DroidDialog.Builder(view.getContext())
                            .icon(R.drawable.add_user)
                            .title("Accept or Reject ?")
                            .content("Someone Added you to his/her friend.")
                            .cancelable(true, true)
                            .positiveButton("Accept", new DroidDialog.onPositiveListener() {
                                @Override
                                public void onPositive(Dialog dialog) {
                                    Toast.makeText(view.getContext(), "Accepted", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .negativeButton("Reject", new DroidDialog.onNegativeListener() {
                                @Override
                                public void onNegative(Dialog dialog) {
                                    Toast.makeText(view.getContext(), "Rejected", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .neutralButton("View Requester", new DroidDialog.onNeutralListener() {
                                @Override
                                public void onNeutral(Dialog dialog) {
                                    Toast.makeText(view.getContext(), "View", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                }
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                notificationList = new ArrayList<>();
                service.getNewNotificationForNotificationFragment(view.getContext(),idUser,notificationList,swipeRefreshLayout);
//                notificationAdapter.notifyDataSetChanged();
            }
        });

        SharedPreferences credential = view.getContext().getSharedPreferences("user",MODE_PRIVATE);
        idUser = credential.getLong("ID",-1);

        prepareNotificationTest(view);
        return view;
    }

    public void prepareNotificationTest(View view){
        service.getNewNotificationForNotificationFragment(view.getContext(),idUser,notificationList,swipeRefreshLayout);
//        notificationAdapter.notifyDataSetChanged();
    }
}
