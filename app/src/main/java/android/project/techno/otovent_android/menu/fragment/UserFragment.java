package android.project.techno.otovent_android.menu.fragment;


import android.os.Bundle;
import android.project.techno.otovent_android.Adapter.util.ImageGridUserAdapter;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.project.techno.otovent_android.R;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {
    private GridView gridView;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
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
