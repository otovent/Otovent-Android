package android.project.techno.otovent_android.Adapter;

import android.project.techno.otovent_android.R;
import android.project.techno.otovent_android.model.Comment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import customfonts.MyTextView;

/**
 * Created by root on 21/11/17.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
    private List<Comment> data;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        MyTextView fullName, commentDesc;
        ImageView photoProfil;

        public MyViewHolder(View itemView) {
            super(itemView);
            fullName = (MyTextView) itemView.findViewById(R.id.fullname_commentUser);
            commentDesc = (MyTextView)  itemView.findViewById(R.id.commentUser);
            photoProfil = (ImageView) itemView.findViewById(R.id.profPic_commentedUser);
        }
    }

    public CommentAdapter() {
    }

    public CommentAdapter(List<Comment> data) {
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_comments, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Comment comment = data.get(position);
        holder.fullName.setText(comment.getUsername());
        holder.commentDesc.setText(comment.getCommentDescription());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
