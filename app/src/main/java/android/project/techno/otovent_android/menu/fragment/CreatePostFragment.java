package android.project.techno.otovent_android.menu.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.project.techno.otovent_android.API.Impl.ServiceImpl;
import android.project.techno.otovent_android.API.Service;
import android.project.techno.otovent_android.R;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gmail.samehadar.iosdialog.IOSDialog;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import customfonts.MyTextView;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by N-REW on 10/11/2017.
 */
public class CreatePostFragment extends Fragment {
    private Service service;
    private EditText status,linkUrlImage;
    private Button btnUpload;
    private MyTextView btnPost,btnBack;
    private Long idUser;
    private boolean flag;
    private ImageView uploadPicture;
    private byte[] fileImageUpload;
    private String fileImageUploadString;

    public CreatePostFragment(){

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 101:
                if (resultCode == RESULT_OK && null != data) {
                    Uri selectedImage = data.getData();
                    linkUrlImage.setText(selectedImage.toString());
                    uploadPicture.setImageURI(selectedImage);

                    Bitmap bitmap = ((BitmapDrawable) uploadPicture.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                    fileImageUploadString = byteArrayOutputStream.toString();
                    fileImageUpload = byteArrayOutputStream.toByteArray();
                }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_create_post, container, false);

        SharedPreferences credential = view.getContext().getSharedPreferences("user",MODE_PRIVATE);
        idUser = credential.getLong("ID",-1);

        service = new ServiceImpl();
        status = (EditText) view.findViewById(R.id.editText);
        btnPost = (MyTextView) view.findViewById(R.id.post);
        btnBack = (MyTextView) view.findViewById(R.id.back);
        btnUpload = (Button) view.findViewById(R.id.Upload);
        uploadPicture = new ImageView(view.getContext());
        linkUrlImage = (EditText) view.findViewById(R.id.linkUpload);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status.getText().toString().isEmpty()){
                    Toast.makeText(v.getContext(), "Please Fill Your Description", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, String> postBody = new HashMap<String, String>();
                    postBody.put("description", status.getText().toString());

                    final IOSDialog iosDialog = new IOSDialog.Builder(v.getContext())
                            .setTitle("Getting Data")
                            .setTitleColorRes(R.color.gray)
                            .build();
//                service.createPost(v.getContext(),idUser,postBody,fileImageUpload,iosDialog);
                    service.createPost(v.getContext(), idUser, postBody, fileImageUploadString, iosDialog);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeLineFragment timeLineFragment = new TimeLineFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content,timeLineFragment,null)
                        .commit();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(it, 101);
                flag = true;
            }
        });

        return view;
    }
}
