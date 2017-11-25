package android.project.techno.otovent_android.Adapter;

import android.project.techno.otovent_android.R;
import android.project.techno.otovent_android.model.Notification;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

import customfonts.MyTextView;

/**
 * Created by root on 10/11/17.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    private List<Notification> data;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        MyTextView tanggalNotikasi,pesan;
        ImageView photoProfil;
        public MyViewHolder(View itemView) {
            super(itemView);
            tanggalNotikasi = (MyTextView) itemView.findViewById(R.id.txt_tanggal_notif);
            pesan = (MyTextView) itemView.findViewById(R.id.txt_user_dan_pesan);
            photoProfil = (ImageView) itemView.findViewById(R.id.logo_user_timeline);
        }
    }

    public NotificationAdapter(){}

    public NotificationAdapter(List<Notification> data){
        this.data=data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_notification,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Notification notification = data.get(position);
        holder.tanggalNotikasi.setText(notification.getNotificationDate());
        holder.pesan.setText(notification.getMessage());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
