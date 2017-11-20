package android.project.techno.otovent_android.API;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.project.techno.otovent_android.model.Notification;
import android.project.techno.otovent_android.model.SearchRequest;
import android.project.techno.otovent_android.model.UserRequest;
import android.support.v4.widget.SwipeRefreshLayout;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by root on 20/10/17.
 */

public interface Service {
    void authToBackend(String endpoint, String username, String password, final Context callingClass, final ProgressDialog progressDialog);
    void addOrEditUser(String endpoint, Map<String, String> postBody, final Context callingClass,
                       final ProgressDialog progressDialog);
    void pushNotification(Context context, NotificationManager notificationManager,String valueFragmentToGo,
                                 String title, String contentTitile, Integer numberOfMessage);
    void getNotificationForUserFromBackend(Long idUser, Context context, NotificationManager notificationManager,String valueFragmentToGo,
                                                  String title, String message);
    void getNewNotificationForNotificationFragment(Context context, Long idUser, List<Notification> data, SwipeRefreshLayout swipeRefreshLayout);

    void getUserCredential(Long id, final Context callingClass);

    void searchUser( final Context callingClass, String searchName, List<SearchRequest> resultData);
}
