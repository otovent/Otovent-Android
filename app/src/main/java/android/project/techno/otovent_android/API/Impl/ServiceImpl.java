package android.project.techno.otovent_android.API.Impl;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.project.techno.otovent_android.API.Service;
import android.project.techno.otovent_android.R;
import android.project.techno.otovent_android.menu.BaseActivity;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by root on 20/10/17.
 */

public class ServiceImpl implements Service{

    @Override
    public void authToBackend(String endpoint, String username, String password, final Context callingClass, final ProgressDialog progressDialog) {
        RequestQueue queue = Volley.newRequestQueue(callingClass);
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        JsonObjectRequest requestLogin = new JsonObjectRequest(callingClass.getString(R.string.ENV_HOST_BACKEND) + endpoint,
                new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    String result = response.getString("message");
                    Toast.makeText(callingClass, result, Toast.LENGTH_SHORT).show();
                    if (result.equalsIgnoreCase("Success Login")){
                        Intent it = new Intent(callingClass,BaseActivity.class);
                        callingClass.startActivity(it);
                    } else {
                        Toast.makeText(callingClass, "User or Password Not Correct", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.e("error",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(callingClass, error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("Result", error.toString());
                progressDialog.dismiss();
            }
        });
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait");
        progressDialog.show();

        queue.add(requestLogin);
    }

    @Override
    public void addOrEditUser(String endpoint, Map<String,String> postBody, final Context callingClass, final ProgressDialog progressDialog) {
        RequestQueue queue = Volley.newRequestQueue(callingClass);

        JsonObjectRequest requestLogin = new JsonObjectRequest(callingClass.getString(R.string.ENV_HOST_BACKEND) + endpoint,
                new JSONObject(postBody), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(callingClass, response.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(callingClass, "Tes Failed", Toast.LENGTH_SHORT).show();
                Log.i("Result", error.toString());
                progressDialog.dismiss();
            }
        });
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait");
        progressDialog.show();

        queue.add(requestLogin);
    }

    @Override
    public void pushNotification(Context context, NotificationManager notificationManager,String valueFragmentToGo,
                                   String title, String message) {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent base = new Intent(context,BaseActivity.class);
        base.putExtra("fragment",valueFragmentToGo);
        PendingIntent activityToGo = PendingIntent.getActivity(context,0,base,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notificationChannel = new NotificationCompat.Builder(context)
            .setContentText(message)
            .setContentTitle(title)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setVibrate(new long[]{100, 200, 300})
                .setContentIntent(activityToGo).setAutoCancel(true)
                .build();
        notificationManager.notify(0,notificationChannel);
    }
}
