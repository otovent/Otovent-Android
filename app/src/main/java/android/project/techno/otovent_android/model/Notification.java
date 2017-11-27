package android.project.techno.otovent_android.model;

/**
 * Created by root on 10/11/17.
 */

public class Notification {
    private Long id;
    private String fullName;
    private Long idTarget;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTarget() {
        return idTarget;
    }

    public void setIdTarget(Long idTarget) {
        this.idTarget = idTarget;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
