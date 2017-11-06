package android.project.techno.otovent_android.menu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.project.techno.otovent_android.R;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeLineFragment extends Fragment {
    private ImageView maps;

    public TimeLineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        maps = (ImageView) view.findViewById(R.id.logo_maps);
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailEventFragment detailEventFragment = new DetailEventFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content,detailEventFragment,null)
                        .commit();
            }
        });
        return view;
    }

}
