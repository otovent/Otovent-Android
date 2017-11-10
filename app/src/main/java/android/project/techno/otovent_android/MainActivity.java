package android.project.techno.otovent_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.project.techno.otovent_android.menu.BaseActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView signin;
    TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //First check to shared preference
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        Long idUserAlreadyLogin = sp.getLong("ID",0);

        if (idUserAlreadyLogin != 0){
            Intent it = new Intent(MainActivity.this,BaseActivity.class);
            startActivity(it);
            return;
        }
        signin = (TextView)findViewById(R.id.signin);
        signup = (TextView)findViewById(R.id.signup);

        signin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent it = new Intent(MainActivity.this, signin.class);
                startActivity(it);
            }
        });

        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View vv) {
                Intent it = new Intent(MainActivity.this, signup.class);
                startActivity(it);
            }
        });
    }
}
