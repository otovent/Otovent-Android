package android.project.techno.otovent_android.menu.fragment;

import android.os.Bundle;
import android.project.techno.otovent_android.R;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by root on 20/11/17.
 */

public class CreateEventFragment extends Fragment {
    public CreateEventFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_create_event, container, false);
        return view;
    }
}
