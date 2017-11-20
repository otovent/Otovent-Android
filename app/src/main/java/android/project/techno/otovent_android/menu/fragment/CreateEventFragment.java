package android.project.techno.otovent_android.menu.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.project.techno.otovent_android.API.Impl.ServiceImpl;
import android.project.techno.otovent_android.API.Service;
import android.project.techno.otovent_android.R;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gmail.samehadar.iosdialog.IOSDialog;

import java.util.HashMap;

import customfonts.MyEditText;
import customfonts.MyTextView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by root on 20/11/17.
 */

public class CreateEventFragment extends Fragment {
    private EditText status;
    private EditText title,longitude,latitude;
    private MyTextView btnPost,btnBack;
    private Long idUser;
    private Service service;

    public CreateEventFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_create_event, container, false);

        status = (EditText) view.findViewById(R.id.editText);
        title = (EditText) view.findViewById(R.id.title);
        latitude = (EditText) view.findViewById(R.id.Latitude);
        longitude = (EditText) view.findViewById(R.id.Longitude);
        btnPost = (MyTextView) view.findViewById(R.id.post);
        btnBack = (MyTextView) view.findViewById(R.id.back);
        SharedPreferences credential = view.getContext().getSharedPreferences("user",MODE_PRIVATE);
        idUser = credential.getLong("ID",-1);

        service = new ServiceImpl();

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> bodyEvent = new HashMap<String, String>();
                bodyEvent.put("idUser",idUser.toString());
                bodyEvent.put("name",title.getText().toString());
                bodyEvent.put("description",status.getText().toString());
                bodyEvent.put("longitude",longitude.getText().toString());
                bodyEvent.put("latitude",latitude.getText().toString());
                final IOSDialog iosDialog = new IOSDialog.Builder(view.getContext())
                        .setTitle("Getting Data")
                        .setTitleColorRes(R.color.gray)
                        .build();
                service.createEvent(v.getContext(),bodyEvent,iosDialog);
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
