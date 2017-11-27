package android.project.techno.otovent_android.menu.fragment;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.project.techno.otovent_android.API.Impl.ServiceImpl;
import android.project.techno.otovent_android.API.Service;
import android.project.techno.otovent_android.Adapter.CommentAdapter;
import android.project.techno.otovent_android.Adapter.util.ClickListener;
import android.project.techno.otovent_android.Adapter.util.DividerItemDecoration;
import android.project.techno.otovent_android.Adapter.util.RecyclerTouchListener;
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
import android.widget.Toast;

import com.droidbyme.dialoglib.DroidDialog;
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

        SharedPreferences credential = view.getContext().getSharedPreferences("user", MODE_PRIVATE);
        final Long idUser = credential.getLong("ID", -1);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commendPost.getText().toString().isEmpty()){
                    Toast.makeText(v.getContext(), "Please Fill Your Comment Section First", Toast.LENGTH_SHORT).show();
                }
                else {
                    Long idPost = postEvent.getId();
                    String comment = commendPost.getText().toString();
                    final IOSDialog iosDialog = new IOSDialog.Builder(view.getContext())
                            .setTitle("Getting Data")
                            .setTitleColorRes(R.color.gray)
                            .build();
                    service.postComment(v.getContext(), idPost, idUser, comment, iosDialog);
                    commentAdapter.notifyDataSetChanged();
                }
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(view.getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(final View view, final int position) {
                new DroidDialog.Builder(view.getContext())
                        .icon(R.drawable.add_user)
                        .title("Delete Comment")
                        .content("Do you want to delete this ?")
                        .cancelable(true, true)
                        .positiveButton("Yes", new DroidDialog.onPositiveListener() {
                            @Override
                            public void onPositive(Dialog dialog) {
                                final IOSDialog iosDialog = new IOSDialog.Builder(view.getContext())
                                        .setTitle("Getting Data")
                                        .setTitleColorRes(R.color.gray)
                                        .build();
                                service.deleteComment(view.getContext(),listComment.get(position).getId(),iosDialog);
                                listComment = new ArrayList<>();
                                initData(view,postEvent.getId());
                                commentAdapter.notifyDataSetChanged();
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
