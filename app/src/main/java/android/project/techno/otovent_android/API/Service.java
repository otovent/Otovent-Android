package android.project.techno.otovent_android.API;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.project.techno.otovent_android.model.Notification;
import android.project.techno.otovent_android.model.PostEvent;
import android.project.techno.otovent_android.model.SearchRequest;
import android.project.techno.otovent_android.model.UserRequest;
import android.support.v4.widget.SwipeRefreshLayout;

import com.gmail.samehadar.iosdialog.IOSDialog;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by root on 20/10/17.
 */

public interface Service {

    void authToBackend(String endpoint, String username, String password, final Context callingClass, final IOSDialog progressDialog);

    void addOrEditUser(String endpoint, Map<String, String> postBody, final Context callingClass,
                       final IOSDialog progressDialog);

    void getTimelineUser(Context callingClass, Long idUser, final List<PostEvent> postEventList, final IOSDialog iosDialog);

    void pushNotification(Context context, NotificationManager notificationManager,String valueFragmentToGo,
                                 String title, String contentTitile, Integer numberOfMessage);
    void getNotificationForUserFromBackend(Long idUser, Context context, NotificationManager notificationManager,String valueFragmentToGo,
                                                  String title, String message);
    void getNewNotificationForNotificationFragment(Context context, Long idUser, List<Notification> data, SwipeRefreshLayout swipeRefreshLayout);

    void getUserCredential(Long id, final Context callingClass);

    void searchUser(final Context callingClass, String searchName, List<SearchRequest> resultData, IOSDialog iosDialog);

    void createPost(final Context callingClass,final Long idUser, final Map<String,String> bodyCreatePost, final IOSDialog iosDialog);

    void createEvent(final Context callingClass, Map<String,String> bodyCreatePost, final IOSDialog iosDialog);

    void cekFriendship(final Context callingClass, Long idUser, Long idUserTarget, final IOSDialog iosDialog);

    void addFriend(final Context callClass, Long idUser, Long IdUserTarget, final IOSDialog iosDialog);
}
