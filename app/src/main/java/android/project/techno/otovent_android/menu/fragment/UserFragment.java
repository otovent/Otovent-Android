package android.project.techno.otovent_android.menu.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.project.techno.otovent_android.Adapter.util.ImageGridUserAdapter;
import android.project.techno.otovent_android.menu.BaseActivity;
import android.project.techno.otovent_android.model.SearchRequest;
import android.project.techno.otovent_android.model.UserRequest;
import android.project.techno.otovent_android.signin;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.project.techno.otovent_android.R;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.Toast;

import com.droidbyme.dialoglib.DroidDialog;
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

    private FloatingActionButton btnEdit;

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

        btnEdit = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);

        gridView = (GridView) view.findViewById(R.id.gridview);
        gridView.setAdapter(new ImageGridUserAdapter(view.getContext()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(view.getContext(), "GridImage", Toast.LENGTH_SHORT).show();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DroidDialog.Builder(v.getContext())
                        .icon(R.drawable.add_user)
                        .title("Setting or Logout")
                        .content("Please choose what you want")
                        .cancelable(true, true)
                        .positiveButton("Settings", new DroidDialog.onPositiveListener() {
                            @Override
                            public void onPositive(Dialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .negativeButton("Logout", new DroidDialog.onNegativeListener() {
                            @Override
                            public void onNegative(Dialog dialog) {
                                SharedPreferences credential = getContext().getSharedPreferences("user",MODE_PRIVATE);
                                SharedPreferences.Editor edit = credential.edit();
                                edit.clear();
                                edit.commit();

                                Intent backToLogin = new Intent(getContext(),signin.class);
                                backToLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                backToLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                getContext().startActivity(backToLogin);
                                ((BaseActivity)getActivity()).finish();

                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        return view;
    }

}
