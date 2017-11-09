package android.project.techno.otovent_android.API;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;

import java.util.Map;

/**
 * Created by root on 20/10/17.
 */

public interface Service {
    void authToBackend(String endpoint, String username, String password, final Context callingClass, final ProgressDialog progressDialog);
    void addOrEditUser(String endpoint, Map<String, String> postBody, final Context callingClass,
                       final ProgressDialog progressDialog);
    void pushNotification(Context context, NotificationManager notificationManager,String valueFragmentToGo,
                                 String title, String contentTitile);
}
