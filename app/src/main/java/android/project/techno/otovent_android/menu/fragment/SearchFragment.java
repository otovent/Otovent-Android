package android.project.techno.otovent_android.menu.fragment;

import android.os.Bundle;
import android.project.techno.otovent_android.API.Impl.ServiceImpl;
import android.project.techno.otovent_android.API.Service;
import android.project.techno.otovent_android.Adapter.SearchUserAdapter;
import android.project.techno.otovent_android.Adapter.util.DividerItemDecoration;
import android.project.techno.otovent_android.R;
import android.project.techno.otovent_android.model.SearchRequest;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.samehadar.iosdialog.IOSDialog;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by N-REW on 10/11/2017.
 */
public class SearchFragment extends Fragment {
    private List<SearchRequest> searchRequestList;
    private RecyclerView recyclerView;
    private SearchUserAdapter searchUserAdapter;
    private Service service;
    public View root;
    private SearchView searchView;

    public SearchFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search_people, container, false);
        root = inflater.inflate(R.layout.fragment_search_people, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleViewSearch);
        searchView = (SearchView) view.findViewById(R.id.searchView);
        searchRequestList = new ArrayList<>();

        service = new ServiceImpl();

        searchUserAdapter = new SearchUserAdapter(searchRequestList);

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(),LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(searchUserAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                searchRequestList = new ArrayList<>();
                final IOSDialog iosDialog = new IOSDialog.Builder(view.getContext())
                        .setTitle("Getting Data")
                        .setTitleColorRes(R.color.gray)
                        .build();
                service.searchUser(view.getContext(),query,searchRequestList,iosDialog);
                searchUserAdapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return view;
    }

}
