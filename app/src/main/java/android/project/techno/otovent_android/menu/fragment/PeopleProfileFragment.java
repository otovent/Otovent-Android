package android.project.techno.otovent_android.menu.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.project.techno.otovent_android.API.Impl.ServiceImpl;
import android.project.techno.otovent_android.API.Service;
import android.project.techno.otovent_android.R;
import android.project.techno.otovent_android.menu.BaseActivity;
import android.project.techno.otovent_android.model.SearchRequest;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gmail.samehadar.iosdialog.IOSDialog;

import customfonts.MyTextView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by N-REW on 10/11/2017.
 */
public class PeopleProfileFragment extends Fragment {

    public static SearchRequest user;
    private MyTextView fullname,job;
    private Button buttonAddFriend;
    private Service service;

    public PeopleProfileFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_people_profile, container, false);
        fullname = (MyTextView) view.findViewById(R.id.myTextView);
        fullname.setText(user.getSearchName());
        buttonAddFriend = (Button) view.findViewById(R.id.addFriend);
        service = new ServiceImpl();

        SharedPreferences credential = view.getContext().getSharedPreferences("user",MODE_PRIVATE);
        final Long idUser = credential.getLong("ID",-1);


        final IOSDialog iosDialog = new IOSDialog.Builder(view.getContext())
                .setTitle("Getting Data")
                .setTitleColorRes(R.color.gray)
                .build();

//        service.cekFriendship(view.getContext(),idUser,user.getId(),iosDialog);

        Log.e("Result Cek Friend : ", BaseActivity.friendship.toString());
        Log.e("Result Cek Status : ", BaseActivity.statusFriendsip);

        if (BaseActivity.friendship.equals(Boolean.FALSE)){
            buttonAddFriend.setText("Add Friend");
            buttonAddFriend.setBackgroundColor(view.getResources().getColor(R.color.greyTitle));
            buttonAddFriend.setClickable(true);
            buttonAddFriend.setEnabled(true);
        } else {
            if (BaseActivity.statusFriendsip.equalsIgnoreCase("TO_CONFIRM")){
                buttonAddFriend.setText("Requested");
                buttonAddFriend.setBackgroundColor(view.getResources().getColor(R.color.gray));
                buttonAddFriend.setClickable(false);
                buttonAddFriend.setEnabled(false);
            } else if (BaseActivity.statusFriendsip.equalsIgnoreCase("ACCEPTED")) {
                buttonAddFriend.setText("Already Your Friend");
                buttonAddFriend.setBackgroundColor(view.getResources().getColor(R.color.color1));
                buttonAddFriend.setClickable(false);
                buttonAddFriend.setEnabled(false);
            } else if (BaseActivity.statusFriendsip.equalsIgnoreCase("REJECTED")){
                buttonAddFriend.setText("Add Friend");
                buttonAddFriend.setBackgroundColor(view.getResources().getColor(R.color.greyTitle));
                buttonAddFriend.setClickable(true);
                buttonAddFriend.setEnabled(true);
            }
        }

        buttonAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.addFriend(v.getContext(),idUser,user.getId(),iosDialog);
            }
        });
        return view;
    }
}
