package android.project.techno.otovent_android.menu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.project.techno.otovent_android.API.Impl.ServiceImpl;
import android.project.techno.otovent_android.API.Service;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gmail.samehadar.iosdialog.IOSDialog;

import java.util.ArrayList;
import java.util.List;

import customfonts.MyEditText;

/**
 * Created by N-REW on 10/11/2017.
 */
public class SearchFragment extends Fragment {
    private List<SearchRequest> searchRequestList;
    private RecyclerView recyclerView;
    private SearchUserAdapter searchUserAdapter;
    private Button btnSearch;
    private MyEditText searchField;
    private Service service;
    public View root;

    public SearchFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search_people, container, false);
        root = inflater.inflate(R.layout.fragment_search_people, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleViewSearch);
        searchRequestList = new ArrayList<>();
        btnSearch = (Button) view.findViewById(R.id.btnSearch);
        searchField = (MyEditText) view.findViewById(R.id.first_name);
        service = new ServiceImpl();

        searchUserAdapter = new SearchUserAdapter(searchRequestList);

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(),LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(searchUserAdapter);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                searchRequestList = new ArrayList<>();
                final IOSDialog iosDialog = new IOSDialog.Builder(view.getContext())
                        .setTitle("Getting Data")
                        .setTitleColorRes(R.color.gray)
                        .build();
                service.searchUser(view.getContext(),searchField.getText().toString(),searchRequestList,iosDialog);
            }
        });

        return view;
    }

//    private void searchUser(String searchName){
//        final IOSDialog iosDialog = new IOSDialog.Builder(root.getContext())
//                .setTitle("Getting Data")
//                .setTitleColorRes(R.color.gray)
//                .build();
//        service.searchUser(root.getContext(),searchName,searchRequestList,iosDialog);
//    }

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
