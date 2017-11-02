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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Miura on 10/29/2017.
 */

public class signup extends AppCompatActivity {
    TextView signinhere;
    TextView signUp;
    Typeface fonts1;
    EditText email;
    EditText username;
    EditText password;
    EditText firstName;
    EditText lastName;
    Service service;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        signinhere = (TextView) findViewById(R.id.signinhere);
        signUp = (TextView) findViewById(R.id.signup1);
        email = (EditText) findViewById(R.id.email);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        firstName = (EditText) findViewById(R.id.first_name);
        lastName = (EditText) findViewById(R.id.last_name);

        progressDialog = new ProgressDialog(signup.this,R.style.AppTheme);
        service = new ServiceImpl();

        signinhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(signup.this, signin.class);
                startActivity(it);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,String> postBody = new HashMap<String, String>();
                postBody.put("email",email.getText().toString());
                postBody.put("username",username.getText().toString());
                postBody.put("password",password.getText().toString());
                postBody.put("first_name",firstName.getText().toString());
                postBody.put("last_name",lastName.getText().toString());

                service.addOrEditUser("users/add",
                        postBody,signup.this,progressDialog);
            }
        });

        fonts1 = Typeface.createFromAsset(signup.this.getAssets(),
                "fonts/Lato-Regular.ttf");
        TextView t1 = (TextView) findViewById(R.id.signinhere);
        t1.setTypeface(fonts1);
    }
}