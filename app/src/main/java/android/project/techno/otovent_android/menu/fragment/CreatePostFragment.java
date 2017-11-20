package android.project.techno.otovent_android.menu.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.project.techno.otovent_android.API.Impl.ServiceImpl;
import android.project.techno.otovent_android.API.Service;
import android.project.techno.otovent_android.R;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gmail.samehadar.iosdialog.IOSDialog;

import java.util.HashMap;

import customfonts.MyTextView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by N-REW on 10/11/2017.
 */
public class CreatePostFragment extends Fragment {
    private Service service;
    private EditText status;
    private MyTextView btnPost,btnBack;
    private Long idUser;

    public CreatePostFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_create_post, container, false);

        SharedPreferences credential = view.getContext().getSharedPreferences("user",MODE_PRIVATE);
        idUser = credential.getLong("ID",-1);

        service = new ServiceImpl();
        status = (EditText) view.findViewById(R.id.editText);
        btnPost = (MyTextView) view.findViewById(R.id.post);
        btnBack = (MyTextView) view.findViewById(R.id.back);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> postBody = new HashMap<String, String>();
                postBody.put("description",status.getText().toString());

                final IOSDialog iosDialog = new IOSDialog.Builder(v.getContext())
                        .setTitle("Getting Data")
                        .setTitleColorRes(R.color.gray)
                        .build();
                service.createPost(v.getContext(),idUser,postBody,iosDialog);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeLineFragment timeLineFragment = new TimeLineFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content,timeLineFragment,null)
                        .commit();
            }
        });
        return view;
    }
}
