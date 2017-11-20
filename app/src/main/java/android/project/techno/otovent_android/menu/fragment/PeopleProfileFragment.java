package android.project.techno.otovent_android.menu.fragment;

import android.os.Bundle;
import android.project.techno.otovent_android.R;
import android.project.techno.otovent_android.model.SearchRequest;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import customfonts.MyTextView;

/**
 * Created by N-REW on 10/11/2017.
 */
public class PeopleProfileFragment extends Fragment {
    public static SearchRequest user;

    private MyTextView fullname,job;

    public PeopleProfileFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_people_profile, container, false);
        fullname = (MyTextView) view.findViewById(R.id.myTextView);
        fullname.setText(user.getSearchName());
        return view;
    }
}
