package android.project.techno.otovent_android.menu.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.project.techno.otovent_android.API.Impl.ServiceImpl;
import android.project.techno.otovent_android.API.Service;
import android.project.techno.otovent_android.R;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.gmail.samehadar.iosdialog.IOSDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import customfonts.MyEditText;
import customfonts.MyTextView;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by root on 20/11/17.
 */

public class CreateEventFragment extends Fragment {
    private EditText status;
    private EditText title, longitude, latitude;
    private MyTextView btnPost, btnBack, btnSetLocation;
    private Long idUser;
    private EditText linkUrlImage;
    private Button btnUpload;
    private ImageView uploadPicture;
    private Service service;
    private String fileImageUploadString;
    private byte[] fileImageUpload;
    private boolean flag;
    private static int counterMarker=0;

    public CreateEventFragment() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 101:
                if (resultCode == RESULT_OK && null != data) {
                    Uri selectedImage = data.getData();
                    linkUrlImage.setText(selectedImage.toString());
                    uploadPicture.setImageURI(selectedImage);

                    Bitmap bitmap = ((BitmapDrawable) uploadPicture.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    fileImageUploadString = byteArrayOutputStream.toString();
                    fileImageUpload = byteArrayOutputStream.toByteArray();
                }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_create_event, container, false);

        status = (EditText) view.findViewById(R.id.editText);
        title = (EditText) view.findViewById(R.id.title);
        latitude = (EditText) view.findViewById(R.id.Latitude);
        longitude = (EditText) view.findViewById(R.id.Longitude);
        btnPost = (MyTextView) view.findViewById(R.id.post);
        btnBack = (MyTextView) view.findViewById(R.id.back);
        btnSetLocation = (MyTextView) view.findViewById(R.id.location);
        btnUpload = (Button) view.findViewById(R.id.Upload);
        uploadPicture = new ImageView(view.getContext());
        linkUrlImage = (EditText) view.findViewById(R.id.linkUpload);

        SharedPreferences credential = view.getContext().getSharedPreferences("user", MODE_PRIVATE);
        idUser = credential.getLong("ID", -1);

        service = new ServiceImpl();


        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.content.Intent it = new Intent(android.content.Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(it, 101);
                flag = true;
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> bodyEvent = new HashMap<String, String>();
                bodyEvent.put("idUser", idUser.toString());
                bodyEvent.put("name", title.getText().toString());
                bodyEvent.put("description", status.getText().toString());
                bodyEvent.put("longitude", longitude.getText().toString());
                bodyEvent.put("latitude", latitude.getText().toString());
                final IOSDialog iosDialog = new IOSDialog.Builder(view.getContext())
                        .setTitle("Getting Data")
                        .setTitleColorRes(R.color.gray)
                        .build();
                service.createEvent(v.getContext(),bodyEvent,fileImageUploadString,iosDialog);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeLineFragment timeLineFragment = new TimeLineFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, timeLineFragment, null)
                        .commit();
            }
        });

        btnSetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialogmap);
                dialog.show();

                MapView mMapView = (MapView) dialog.findViewById(R.id.mapView);
                MapsInitializer.initialize(getActivity());

                mMapView = (MapView) dialog.findViewById(R.id.mapView);
                mMapView.onCreate(dialog.onSaveInstanceState());
                mMapView.onResume();// needed to get the map to display immediately
                mMapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(final GoogleMap googleMap) {
                        CreateEventFragment.counterMarker=0;
                        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        googleMap.setMyLocationEnabled(true);

                        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                            @Override
                            public void onMapClick(LatLng point) {
                                if (CreateEventFragment.counterMarker==0) {
                                    MarkerOptions marker = new MarkerOptions().position(
                                            new LatLng(point.latitude, point.longitude)).title("Your Event Location");
                                    googleMap.addMarker(marker);
                                    System.out.println(point.latitude + "---" + point.longitude);
                                    latitude.setText(String.valueOf(point.latitude));
                                    longitude.setText(String.valueOf(point.longitude));
                                    CreateEventFragment.counterMarker++;
                                }
                            }
                        });

                        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                marker.remove();
                                CreateEventFragment.counterMarker--;
                                return true;
                            }
                        });
                    }
                });
            }
        });
        return view;
    }
}
