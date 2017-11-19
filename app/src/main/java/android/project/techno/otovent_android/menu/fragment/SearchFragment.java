package android.project.techno.otovent_android.menu.fragment;

import android.os.Bundle;
import android.project.techno.otovent_android.Adapter.SearchUserAdapter;
import android.project.techno.otovent_android.Adapter.TimelineAdapter;
import android.project.techno.otovent_android.Adapter.util.DividerItemDecoration;
import android.project.techno.otovent_android.R;
import android.project.techno.otovent_android.model.PostEvent;
import android.project.techno.otovent_android.model.SearchRequest;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by N-REW on 10/11/2017.
 */
public class SearchFragment extends Fragment {
    private List<SearchRequest> searchRequestList;
    private RecyclerView recyclerView;
    private SearchUserAdapter searchUserAdapter;

    public SearchFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_people, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleViewSearch);
        searchRequestList = new ArrayList<>();

        initData();

        searchUserAdapter = new SearchUserAdapter(searchRequestList);

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(),LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(searchUserAdapter);

        return view;
    }

    private void initData(){
        searchRequestList = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest();
            searchRequest.setSearchName("Dono ");
            searchRequest.setSearchStatus("Web Developer");
        searchRequestList.add(searchRequest);
        searchRequestList.add(searchRequest);
        searchRequestList.add(searchRequest);
        searchRequestList.add(searchRequest);
        searchRequestList.add(searchRequest);
    }
}
