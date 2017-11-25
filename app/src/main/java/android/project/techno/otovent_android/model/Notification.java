package android.project.techno.otovent_android.model;

/**
 * Created by root on 10/11/17.
 */

public class Notification {
    private String imgUrl;
    private String notificationDate;
    private String message;
    private String type;

    public Notification(){}

    public Notification(String imgUrl, String notificationDate, String message) {
        this.imgUrl = imgUrl;
        this.notificationDate = notificationDate;
        this.message = message;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
