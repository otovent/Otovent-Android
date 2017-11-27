package android.project.techno.otovent_android.menu.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.project.techno.otovent_android.API.Impl.ServiceImpl;
import android.project.techno.otovent_android.API.Service;
import android.project.techno.otovent_android.Adapter.CommentAdapter;
import android.project.techno.otovent_android.Adapter.util.DividerItemDecoration;
import android.project.techno.otovent_android.R;
import android.project.techno.otovent_android.model.Comment;
import android.project.techno.otovent_android.model.PostEvent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.gmail.samehadar.iosdialog.IOSDialog;

import java.util.ArrayList;
import java.util.List;

import customfonts.MyTextView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by root on 21/11/17.
 */

public class DetailPostFragment extends Fragment {
    public static PostEvent postEvent = new PostEvent();
    public static ArrayList<Comment> listComment = new ArrayList<>();
    private Service service;
    private RecyclerView recyclerView;
    private CommentAdapter commentAdapter;
    private MyTextView user;
    private MyTextView post;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView send;
    private EditText commendPost;

    public DetailPostFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_detail_post,container,false);

        service = new ServiceImpl();
        user = (MyTextView) view.findViewById(R.id.nama_pengguna);
        post = (MyTextView) view.findViewById(R.id.postDesc);
        send = (ImageView) view.findViewById(R.id.imageView5);
        commendPost = (EditText) view.findViewById(R.id.myEditText);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshDetailPost);

        user.setText(postEvent.getFullName());
        post.setText(postEvent.getStatus());

        initData(view,postEvent.getId());

        commentAdapter = new CommentAdapter(listComment);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleViewComment);

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(view.getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(),LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(commentAdapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long idPost = postEvent.getId();
                SharedPreferences credential = view.getContext().getSharedPreferences("user",MODE_PRIVATE);
                Long idUser = credential.getLong("ID",-1);
                String comment = commendPost.getText().toString();
                final IOSDialog iosDialog = new IOSDialog.Builder(view.getContext())
                        .setTitle("Getting Data")
                        .setTitleColorRes(R.color.gray)
                        .build();
                service.postComment(v.getContext(),idPost,idUser,comment,iosDialog);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listComment = new ArrayList<>();
                initData(view,postEvent.getId());
                commentAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    private void initData(View view, Long idPost){
        final IOSDialog iosDialog = new IOSDialog.Builder(view.getContext())
                .setTitle("Getting Data")
                .setTitleColorRes(R.color.gray)
                .build();
        service.getComment(view.getContext(), idPost, iosDialog);
    }
}
