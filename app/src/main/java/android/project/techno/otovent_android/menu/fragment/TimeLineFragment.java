package android.project.techno.otovent_android.menu.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.project.techno.otovent_android.API.Impl.ServiceImpl;
import android.project.techno.otovent_android.API.Service;
import android.project.techno.otovent_android.Adapter.NotificationAdapter;
import android.project.techno.otovent_android.Adapter.TimelineAdapter;
import android.project.techno.otovent_android.Adapter.util.ClickListener;
import android.project.techno.otovent_android.Adapter.util.DividerItemDecoration;
import android.project.techno.otovent_android.Adapter.util.RecyclerTouchListener;
import android.project.techno.otovent_android.menu.BaseActivity;
import android.project.techno.otovent_android.model.EventModel;
import android.project.techno.otovent_android.model.Notification;
import android.project.techno.otovent_android.model.PostEvent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.project.techno.otovent_android.R;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.droidbyme.dialoglib.DroidDialog;
import com.gmail.samehadar.iosdialog.IOSDialog;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeLineFragment extends Fragment {
//    private ImageView maps;
    private List<PostEvent> postEventList;
    private RecyclerView recyclerView;
    private TimelineAdapter timelineAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Service service;
    private View root;
    private Long idUser;

    private Button createPost,createEvent;

    public TimeLineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        root = inflater.inflate(R.layout.fragment_timeline, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycleViewTimeline);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshTimeline);

        createPost = (Button) view.findViewById(R.id.btnPost);
        createEvent = (Button) view.findViewById(R.id.btnEvent);

        postEventList = new ArrayList<>();

        service = new ServiceImpl();

        SharedPreferences credential = view.getContext().getSharedPreferences("user",MODE_PRIVATE);
        idUser = credential.getLong("ID",-1);

        initDataList(view);

        timelineAdapter = new TimelineAdapter(postEventList);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(),LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(timelineAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postEventList = new ArrayList<>();
                initDataList(view);
                swipeRefreshLayout.setRefreshing(false);
                for (PostEvent postEvent : postEventList){
                    Log.e("List Post : ",postEvent.getFullName());
                }
                timelineAdapter.notifyDataSetChanged();
            }
        });

        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePostFragment createPostFragment = new CreatePostFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content,createPostFragment,null)
                        .commit();
            }
        });

        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateEventFragment createEventFragment = new CreateEventFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content,createEventFragment,null)
                        .commit();
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(view.getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                PostEvent postEvent = postEventList.get(position);

                if (postEvent.getTypePostEvent().equalsIgnoreCase("POST")) {
                    DetailPostFragment.postEvent = postEvent;
                    DetailPostFragment detailPostFragment = new DetailPostFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, detailPostFragment, null)
                            .commit();
                } else if (postEvent.getTypePostEvent().equalsIgnoreCase("EVENT")){
                    final IOSDialog iosDialog = new IOSDialog.Builder(view.getContext())
                            .setTitle("Getting Data")
                            .setTitleColorRes(R.color.gray)
                            .build();
                    Log.e("ID EVENT", postEvent.getId().toString());
                    service.getEventModel(view.getContext(),postEvent.getId(),iosDialog);
                }
            }
            @Override
            public void onLongClick(View view, final int position) {
                new DroidDialog.Builder(view.getContext())
                        .icon(R.drawable.add_user)
                        .title("Delete Post")
                        .content("Do you want to delete this ?")
                        .cancelable(true, true)
                        .positiveButton("Yes", new DroidDialog.onPositiveListener() {
                            @Override
                            public void onPositive(Dialog dialog) {
                                postEventList.remove(position);
                                timelineAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        })
                        .negativeButton("No", new DroidDialog.onNegativeListener() {
                            @Override
                            public void onNegative(Dialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        }));

        return view;
    }

    private void initDataList(View view){
        final IOSDialog iosDialog = new IOSDialog.Builder(view.getContext())
                .setTitle("Getting Data")
                .setTitleColorRes(R.color.gray)
                .build();
        service.getTimelineUser(view.getContext(),idUser,postEventList,iosDialog);
    }

}
