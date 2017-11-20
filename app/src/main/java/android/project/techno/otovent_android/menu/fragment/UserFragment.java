package android.project.techno.otovent_android.menu.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.project.techno.otovent_android.Adapter.util.ImageGridUserAdapter;
import android.project.techno.otovent_android.model.UserRequest;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.project.techno.otovent_android.R;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.Toast;

import com.gmail.samehadar.iosdialog.IOSDialog;

import customfonts.MyTextView;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {
    private GridView gridView;
    private UserRequest userLogged;
    private Long idUser;
    private String fullName;
    private MyTextView userName;
    private IOSDialog iosDialog;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        final IOSDialog iosDialog = new IOSDialog.Builder(view.getContext())
                .setTitle("Getting Data")
                .setTitleColorRes(R.color.gray)
                .build();
        SharedPreferences credential = view.getContext().getSharedPreferences("user",MODE_PRIVATE);
        idUser = credential.getLong("ID",-1);
        fullName = credential.getString("firstName","NA") + " " +credential.getString("lastName","NA");

        userName = (MyTextView) view.findViewById(R.id.user_name);
        userName.setText(fullName);
        
        gridView = (GridView) view.findViewById(R.id.gridview);
        gridView.setAdapter(new ImageGridUserAdapter(view.getContext()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(view.getContext(), "GridImage", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
