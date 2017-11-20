package android.project.techno.otovent_android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.project.techno.otovent_android.API.Impl.ServiceImpl;
import android.project.techno.otovent_android.API.Service;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gmail.samehadar.iosdialog.IOSDialog;

/**
 * Created by Miura on 10/29/2017.
 */

public class signin extends AppCompatActivity {
    private ProgressDialog progressDialog = null;
    TextView create;
    TextView login;
    Typeface fonts1;
    Service service;
    EditText email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        progressDialog = new ProgressDialog(signin.this,R.style.AppTheme);
        final IOSDialog iosDialog = new IOSDialog.Builder(signin.this)
                .setTitle("Getting Data")
                .setTitleColorRes(R.color.gray)
                .build();
        service = new ServiceImpl();
        create = (TextView) findViewById(R.id.create);
        login = (TextView) findViewById(R.id.signin1);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(signin.this, signup.class);
                startActivity(it);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.authToBackend("users/login",email.getText().toString(),
                        password.getText().toString(),signin.this,iosDialog);
            }
        });
        fonts1 = Typeface.createFromAsset(signin.this.getAssets(),
                "fonts/Lato-Regular.ttf");
        TextView t4 = (TextView) findViewById(R.id.create);
        t4.setTypeface(fonts1);
    }

}