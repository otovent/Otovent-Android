package android.project.techno.otovent_android.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by root on 27/11/17.
 */

public class EventModel {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String nama,status;
    private LatLng latLng;

    public EventModel(){}

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    @Override
    public String toString() {
        return "EventModel{" +
                "id=" + id +
                ", nama='" + nama + '\'' +
                ", status='" + status + '\'' +
                ", latLng=" + latLng +
                '}';
    }
}
