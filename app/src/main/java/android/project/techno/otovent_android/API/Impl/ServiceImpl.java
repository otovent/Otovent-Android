package android.project.techno.otovent_android.API.Impl;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.project.techno.otovent_android.API.Service;
import android.project.techno.otovent_android.menu.fragment.PeopleProfileFragment;
import android.project.techno.otovent_android.menu.fragment.SearchFragment;
import android.project.techno.otovent_android.menu.fragment.TimeLineFragment;
import android.project.techno.otovent_android.model.PostEvent;
import android.project.techno.otovent_android.model.SearchRequest;
import android.project.techno.otovent_android.model.UserRequest;
import android.project.techno.otovent_android.R;
import android.project.techno.otovent_android.menu.BaseActivity;
import android.project.techno.otovent_android.signin;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.gmail.samehadar.iosdialog.IOSDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.SimpleFormatter;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by root on 20/10/17.
 */

public class ServiceImpl implements Service{
    public static Long firstIdNotificationToCheckPushNotification = 0L;

    @Override
    public void authToBackend(String endpoint, String username, String password, final Context callingClass, final IOSDialog progressDialog) {
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
                        ed.putString("firstName",userAuthed.getFirstName());
                        ed.putString("lastName",userAuthed.getLastName());
                        ed.putString("email",userAuthed.getEmail());
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
        progressDialog.show();

        queue.add(requestLogin);
    }

    @Override
    public void addOrEditUser(String endpoint, Map<String,String> postBody, final Context callingClass, final IOSDialog progressDialog) {
        RequestQueue queue = Volley.newRequestQueue(callingClass);

        JsonObjectRequest requestLogin = new JsonObjectRequest(callingClass.getString(R.string.ENV_HOST_BACKEND) + endpoint,
                new JSONObject(postBody), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                Intent back = new Intent(callingClass.getApplicationContext(),signin.class);
                callingClass.startActivity(back);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Result", error.toString());
                progressDialog.dismiss();
            }
        });
        progressDialog.show();

        queue.add(requestLogin);
    }

    @Override
    public void getTimelineUser(Context callingClass, final Long idUser, final List<PostEvent> postEventList, final IOSDialog iosDialog) {
        RequestQueue queue = Volley.newRequestQueue(callingClass);

        StringRequest getNotif = new StringRequest(Request.Method.GET, callingClass.getString(R.string.ENV_HOST_BACKEND) + "users/get/timeline",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject= null;
                        JSONObject result = null;
                        JSONArray resultArray = null;
                        try {
                            jsonObject = new JSONObject(response);
                            result = jsonObject.getJSONObject("result");
                            resultArray = result.getJSONArray("content");
                            for (int i = resultArray.length()-1 ; i >= 0; i--){
                                JSONObject content = resultArray.getJSONObject(i);
                                PostEvent postEvent = new PostEvent();
                                    postEvent.setFullName(content.getJSONObject("user").getString("firstName") + " " +content.getJSONObject("user").getString("lastName"));
                                    postEvent.setTimeAndLocation("Temporary Not Located");
                                    postEvent.setStatus(content.getString("description"));
                                    postEvent.setPhotoProfile(content.getJSONObject("user").getString("photoProfile"));
                                    postEvent.setStatusEventPhoto(content.getString("imageUrl"));
                                    postEvent.setTypePostEvent(content.getString("tipePostEvent"));
                                postEventList.add(postEvent);
                            }
                        } catch (JSONException e) {
                            Log.e("Error Get Notification",e.toString());
                        }
                        iosDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Get Notification",error.toString());
                iosDialog.dismiss();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Content-type","application/json");
                headers.put("idUser",idUser.toString());
                return headers;
            }
        };
        iosDialog.show();
        queue.add(getNotif);
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

    @Override
    public void getNewNotificationForNotificationFragment(Context context, final Long idUser, final List<android.project.techno.otovent_android.model.Notification> data,
                                                          final SwipeRefreshLayout swipeRefreshLayout) {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest getNotif = new StringRequest(Request.Method.GET, context.getString(R.string.ENV_HOST_BACKEND) + "notification/get/new",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject= null;
                        JSONObject result = null;
                        JSONArray content=null;
                        try {
                            jsonObject = new JSONObject(response);
                            result = jsonObject.getJSONObject("result");
                            content = result.getJSONArray("content");
                            for (int i=0;i<content.length();i++){
                                String message = "";
                                JSONObject notif = content.getJSONObject(i);
                                android.project.techno.otovent_android.model.Notification obj
                                        = new android.project.techno.otovent_android.model.Notification();

                                String dateParse = notif.getString("date");
                                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                long millisec = Long.parseLong(dateParse);
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(millisec);
                                obj.setNotificationDate(dateFormat.format(calendar.getTime()));

                                if(notif.getString("notificationDependency").equalsIgnoreCase("COMMENT"))
                                    message = "You Have New Comment from your friend";
                                else if (notif.getString("notificationDependency").equalsIgnoreCase("FRIEND_REQUEST"))
                                    message = "You Have New Friend Request";
                                obj.setMessage(message);
                                obj.setType(notif.getString("notificationDependency"));
                                data.add(obj);
                            }
                            swipeRefreshLayout.setRefreshing(false);
                        } catch (JSONException e) {
                            Log.e("Error Get Notification",e.toString());
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
                return headers;
            }
        };

        queue.add(getNotif);
    }

    @Override
    public void getUserCredential(final Long id, Context callingClass) {
        RequestQueue queue = Volley.newRequestQueue(callingClass);

        StringRequest getNotif = new StringRequest(Request.Method.GET, callingClass.getString(R.string.ENV_HOST_BACKEND) + "users/get/user",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject= null;
                        JSONObject result = null;
                        JSONObject content = null;
                        try {
                            jsonObject = new JSONObject(response);
                            content = jsonObject.getJSONArray("result").getJSONObject(0);
                            UserRequest userRequest = new UserRequest();
                                userRequest.setId(content.getLong("id"));
                                userRequest.setEmail(content.getString("email"));
                                userRequest.setFirstName(content.getString("firstName"));
                                userRequest.setLastName(content.getString("lastName"));
                                userRequest.setUsername(content.getString("username"));
                                userRequest.setPassword(content.getString("password"));
                            BaseActivity.userLogged = userRequest;
                        } catch (JSONException e) {
                            Log.e("Error Get Notification",e.toString());
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
                headers.put("id",id.toString());
//                headers.put("dateRequested",dateRequested);
                return headers;
            }
        };

        queue.add(getNotif);
    }

    @Override
    public void searchUser(final Context callingClass, final String searchName, final List<SearchRequest> resultData,final IOSDialog iosDialog) {
        RequestQueue queue = Volley.newRequestQueue(callingClass);

        StringRequest getNotif = new StringRequest(Request.Method.GET, callingClass.getString(R.string.ENV_HOST_BACKEND) + "users/search",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject= null;
                        JSONArray jsonArray = null;
                        JSONObject result = null;
                        JSONObject content = null;
                        try {
                            jsonObject = new JSONObject(response);
                            jsonArray = jsonObject.getJSONArray("result");
                            for (int i = 0 ; i<jsonArray.length(); i++) {
                                content = jsonArray.getJSONObject(i);
                                SearchRequest userRequest = new SearchRequest();
                                    userRequest.setSearchName(content.getString("firstName")+" "+content.getString("lastName"));
                                    userRequest.setSearchStatus(content.getString("email"));
                                    userRequest.setPhotoUrl(content.getString("photoProfile"));
                                    userRequest.setId(content.getLong("id"));
                                resultData.add(userRequest);
                            }
                            iosDialog.dismiss();
                        } catch (JSONException e) {
                            Log.e("Error Get Notification",e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Get Notification",error.toString());
                iosDialog.dismiss();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Content-type","application/json");
                headers.put("searchName", searchName);
//                headers.put("dateRequested",dateRequested);
                return headers;
            }
        };
        iosDialog.show();
        queue.add(getNotif);
    }

    @Override
    public void createPost(final Context callingClass,final Long idUser, final Map<String,String> bodyCreatePost, final IOSDialog iosDialog) {
        RequestQueue queue = Volley.newRequestQueue(callingClass);
        Map<String, String> params = bodyCreatePost;

        JsonObjectRequest requestLogin = new JsonObjectRequest(callingClass.getString(R.string.ENV_HOST_BACKEND) + "posts/add",
                new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(callingClass, response.getString("message"), Toast.LENGTH_SHORT).show();
                    iosDialog.dismiss();
                    TimeLineFragment timeLineFragment = new TimeLineFragment();
                    FragmentActivity activity = (FragmentActivity) callingClass;
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content,timeLineFragment,null)
                            .commit();
                } catch (JSONException e) {
                    Log.e("error",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(callingClass, error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("Result", error.toString());
                iosDialog.dismiss();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Content-type","application/json");
                headers.put("id",idUser.toString());
                return headers;
            }
        };
        iosDialog.show();
        queue.add(requestLogin);
    }

    @Override
    public void createEvent(final Context callingClass, final Map<String, String> bodyCreatePost, final IOSDialog iosDialog) {
        RequestQueue queue = Volley.newRequestQueue(callingClass);
        Map<String, String> params = bodyCreatePost;

        JsonObjectRequest requestLogin = new JsonObjectRequest(callingClass.getString(R.string.ENV_HOST_BACKEND) + "event/add",
                new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(callingClass, response.getString("message"), Toast.LENGTH_SHORT).show();
                    iosDialog.dismiss();
                    TimeLineFragment timeLineFragment = new TimeLineFragment();
                    FragmentActivity activity = (FragmentActivity) callingClass;
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content,timeLineFragment,null)
                            .commit();
                } catch (JSONException e) {
                    Log.e("error",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(callingClass, error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("Result", error.toString());
                iosDialog.dismiss();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Content-type","application/json");
                return headers;
            }
        };
        iosDialog.show();
        queue.add(requestLogin);
    }

    @Override
    public void cekFriendship(final Context callingClass, final Long idUser, final Long idUserTarget, final IOSDialog iosDialog) {
        RequestQueue queue = Volley.newRequestQueue(callingClass);

        StringRequest getNotif = new StringRequest(Request.Method.GET, callingClass.getString(R.string.ENV_HOST_BACKEND) + "friends/cek/friendship/by/friend",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject= null;
                        JSONArray jsonArray = null;
                        try {
                            jsonObject = new JSONObject(response);
                            jsonArray = jsonObject.getJSONArray("result");
                            if (jsonArray == null) {
                                BaseActivity.friendship = Boolean.FALSE;
                                BaseActivity.statusFriendsip = "";
                            }
                            else {
                                String friendShipStatus = jsonArray.getJSONObject(0).getString("friendshipStatus");
                                BaseActivity.friendship = Boolean.TRUE;
                                BaseActivity.statusFriendsip = friendShipStatus;
                            }
                            iosDialog.dismiss();

                            PeopleProfileFragment peopleProfileFragment = new PeopleProfileFragment();
                            FragmentActivity activity = (FragmentActivity) callingClass;
                            activity.getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content,peopleProfileFragment,null)
                                    .commit();

                        } catch (JSONException e) {
                            BaseActivity.friendship = Boolean.FALSE;
                            BaseActivity.statusFriendsip = "";
                            Log.e("Error Get Notification",e.toString());
                            iosDialog.dismiss();
                            PeopleProfileFragment peopleProfileFragment = new PeopleProfileFragment();
                            FragmentActivity activity = (FragmentActivity) callingClass;
                            activity.getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content,peopleProfileFragment,null)
                                    .commit();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Get Notification",error.toString());
                BaseActivity.friendship = Boolean.FALSE;
                BaseActivity.statusFriendsip = "";
                iosDialog.dismiss();
                PeopleProfileFragment peopleProfileFragment = new PeopleProfileFragment();
                FragmentActivity activity = (FragmentActivity) callingClass;
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content,peopleProfileFragment,null)
                        .commit();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Content-type","application/json");
                headers.put("idUser", idUser.toString());
                headers.put("idFriend", idUserTarget.toString());
                return headers;
            }
        };
        iosDialog.show();
        queue.add(getNotif);
    }

    @Override
    public void addFriend(final Context callingClass, final Long idUser, final Long idUserTarget, final IOSDialog iosDialog) {
        RequestQueue queue = Volley.newRequestQueue(callingClass);
        Map<String, String> params = new HashMap<>();
        params.put("user",idUser.toString());
        params.put("friend",idUserTarget.toString());

        JsonObjectRequest requestLogin = new JsonObjectRequest(callingClass.getString(R.string.ENV_HOST_BACKEND) + "friends/add",
                new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(callingClass, response.getString("message"), Toast.LENGTH_SHORT).show();
                    iosDialog.dismiss();
                    Toast.makeText(callingClass, "Sended Friend Request", Toast.LENGTH_SHORT).show();
                    TimeLineFragment timeLineFragment = new TimeLineFragment();
                    FragmentActivity activity = (FragmentActivity) callingClass;
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content,timeLineFragment,null)
                            .commit();
                } catch (JSONException e) {
                    Log.e("error",e.toString());
                    iosDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(callingClass, error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("Result", error.toString());
                iosDialog.dismiss();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Content-type","application/json");
                return headers;
            }
        };
        iosDialog.show();
        queue.add(requestLogin);
    }
}


