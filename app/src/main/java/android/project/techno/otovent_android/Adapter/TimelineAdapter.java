package android.project.techno.otovent_android.Adapter;

import android.project.techno.otovent_android.R;
import android.project.techno.otovent_android.model.PostEvent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import customfonts.MyTextView;

/**
 * Created by root on 22/11/17.
 */

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.MyViewHolder> {
    private List<PostEvent> data;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        MyTextView fullName,tanggalLokasi,status,type;
        TextView totalLike,totalComment;
        ImageView photoProfil,photoEvent;
        public MyViewHolder(View itemView) {
            super(itemView);
            photoProfil = (ImageView) itemView.findViewById(R.id.user_timeline);
            photoEvent = (ImageView) itemView.findViewById(R.id.imageView3);

            fullName = (MyTextView) itemView.findViewById(R.id.nama_pengguna);
            type = (MyTextView) itemView.findViewById(R.id.typePostEvent);
            tanggalLokasi = (MyTextView) itemView.findViewById(R.id.lokasi_user);
            status = (MyTextView) itemView.findViewById(R.id.status);
            totalLike = (TextView) itemView.findViewById(R.id.totalLike);
            totalComment= (TextView) itemView.findViewById(R.id.totalComment);
        }
    }

    public TimelineAdapter(){}

    public TimelineAdapter(List<PostEvent> data){
        this.data=data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_timeline,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        PostEvent postEvent = data.get(position);
        holder.fullName.setText(postEvent.getFullName());
        holder.tanggalLokasi.setText(postEvent.getTimeAndLocation());
        holder.status.setText(postEvent.getStatus());
        holder.totalComment.setText("0");
        holder.totalLike.setText("0");
        holder.type.setText(postEvent.getTypePostEvent());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
