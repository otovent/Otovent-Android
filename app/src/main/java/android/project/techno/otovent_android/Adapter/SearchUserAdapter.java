package android.project.techno.otovent_android.Adapter;

import android.project.techno.otovent_android.R;
import android.project.techno.otovent_android.model.PostEvent;
import android.project.techno.otovent_android.model.SearchRequest;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import customfonts.MyTextView;

/**
 * Created by root on 19/11/17.
 */

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.MyViewHolder> {
    private List<SearchRequest> data;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        MyTextView fullName,status;
        ImageView photoProfil;
        public MyViewHolder(View itemView) {
            super(itemView);
            photoProfil = (ImageView) itemView.findViewById(R.id.logo_user_search);
            fullName = (MyTextView) itemView.findViewById(R.id.myTextView4);
            status = (MyTextView) itemView.findViewById(R.id.myTextView5);
        }
    }

    public SearchUserAdapter(){}

    public SearchUserAdapter(List<SearchRequest> data){
        this.data=data;
    }

    @Override
    public SearchUserAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_search,parent,false);
        return new SearchUserAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchUserAdapter.MyViewHolder holder, final int position) {
        SearchRequest searchedUser = data.get(position);
        holder.fullName.setText(searchedUser.getSearchName());
        holder.status.setText(searchedUser.getSearchStatus());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
