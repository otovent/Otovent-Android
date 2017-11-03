package android.project.techno.otovent_android.menu.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.project.techno.otovent_android.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by N-REW on 02/11/2017.
 */
public class EditProfileFragment extends Fragment {

    public EditProfileFragment(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }
}
