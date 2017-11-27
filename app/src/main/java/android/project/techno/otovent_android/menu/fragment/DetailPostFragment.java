package android.project.techno.otovent_android.menu.fragment;

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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gmail.samehadar.iosdialog.IOSDialog;

import java.util.ArrayList;
import java.util.List;

import customfonts.MyTextView;

/**
 * Created by root on 21/11/17.
 */

public class DetailPostFragment extends Fragment {
    public static PostEvent postEvent;
    private Service service;
    private List<Comment> data;
    private IOSDialog iosDialog;
    private RecyclerView recyclerView;
    private CommentAdapter commentAdapter;
    private ImageView logoMaps;
    private MyTextView user;
    private MyTextView post;

    public DetailPostFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_detail_post,container,false);

        service = new ServiceImpl();
        data = new ArrayList<>();

//        logoMaps = (ImageView) view.findViewById(R.id.logo_maps);
        user = (MyTextView) view.findViewById(R.id.nama_pengguna);
        post = (MyTextView) view.findViewById(R.id.postDesc);

        user.setText(postEvent.getFullName());
        post.setText(postEvent.getStatus());

        initData();

        commentAdapter = new CommentAdapter(data);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleViewComment);

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(view.getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(),LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(commentAdapter);

//        logoMaps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DetailEventFragment detailEventFragment = new DetailEventFragment();
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.content,detailEventFragment,null)
//                        .commit();
//            }
//        });

        return view;
    }

    private void initData(){
        data = new ArrayList<>();
        Comment comment = new Comment();
            comment.setCommentDescription("wkdoakwodkaodwkdoa");
            comment.setUsername("Aldi Pradana");

        data.add(comment);
        data.add(comment);
        data.add(comment);
        data.add(comment);

        for( Comment comments : data){
            Log.e("Data Comment : ",comment.getCommentDescription());
        }
    }
}
