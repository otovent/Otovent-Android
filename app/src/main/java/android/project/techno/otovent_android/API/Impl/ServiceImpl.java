package android.project.techno.otovent_android.API.Impl;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.project.techno.otovent_android.API.Service;
import android.project.techno.otovent_android.model.UserRequest;
import android.project.techno.otovent_android.R;
import android.project.techno.otovent_android.menu.BaseActivity;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by root on 20/10/17.
 */

public class ServiceImpl implements Service{
    private static Long firstIdNotificationToCheckPushNotification = 0L;
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
                        UserRequest userAuthed = new UserRequest();
                        JSONObject user = response.getJSONArray("result").getJSONObject(0);
                        userAuthed.setId(user.getLong("id"));
                        userAuthed.setEmail(user.getString("email"));
                        userAuthed.setFirstName(user.getString("firstName"));
                        userAuthed.setLastName(user.getString("lastName"));
                        userAuthed.setPassword(user.getString("password"));

                        //Save credential to shared preference
                        SharedPreferences sp = callingClass.getSharedPreferences("user",Context.MODE_PRIVATE);
                        SharedPreferences.Editor ed = sp.edit();
                        ed.putLong("ID",userAuthed.getId());
                        ed.commit();

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
                                   String title, String message, Integer numberOfMessage) {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent base = new Intent(context,BaseActivity.class);
        base.putExtra("fragment",valueFragmentToGo);
        PendingIntent activityToGo = PendingIntent.getActivity(context,0,base,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notificationChannel = new NotificationCompat.Builder(context)
            .setContentText(message)
            .setContentTitle(numberOfMessage+ " " +title)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setVibrate(new long[]{100, 200, 300, 200, 100})
                .setContentIntent(activityToGo).setAutoCancel(true)
                .build();
        notificationManager.notify(0,notificationChannel);
    }

    @Override
    public void getNotificationForUserFromBackend(final Long idUser, final Context context, final NotificationManager notificationManager, final String valueFragmentToGo,
                                                  final String title, final String message){
        RequestQueue queue = Volley.newRequestQueue(context);

//        DateFormat df = new SimpleDateFormat("dd-mm-yyyy");
//        final String dateRequested = df.format(new Date());

        StringRequest getNotif = new StringRequest(Request.Method.GET, context.getString(R.string.ENV_HOST_BACKEND) + "notification/get/new",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Long firstIdResponse = 0L;
                        JSONObject jsonObject= null;
                        JSONObject result = null;
                        JSONObject content = null;
                        Integer totalNotification = 0;
                        try {
                            jsonObject = new JSONObject(response);
                            result = jsonObject.getJSONObject("result");
                            totalNotification = result.getInt("totalElements");
                            content = result.getJSONArray("content").getJSONObject(0);
                            firstIdResponse = content.getLong("id");
                        } catch (JSONException e) {
                            Log.e("Error Get Notification",e.toString());
                        }
                        if (totalNotification > 0) {
                            if (firstIdNotificationToCheckPushNotification != firstIdResponse) {
                                firstIdNotificationToCheckPushNotification = firstIdResponse;
                                pushNotification(context, notificationManager, valueFragmentToGo, title, message, totalNotification);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Get Notification",error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Content-type","application/json");
                headers.put("idUser",idUser.toString());
//                headers.put("dateRequested",dateRequested);
                return headers;
            }
        };

        queue.add(getNotif);
    }
}
