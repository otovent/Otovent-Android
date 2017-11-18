package android.project.techno.otovent_android.model;

/**
 * Created by root on 22/11/17.
 */

public class PostEvent {
    private String photoProfile;
    private String fullName;
    private String timeAndLocation;
    private String status;
    private String statusEventPhoto;
    private Integer totalLike;
    private Integer totalComment;

    public PostEvent(){}

    public String getPhotoProfile() {
        return photoProfile;
    }

    public void setPhotoProfile(String photoProfile) {
        this.photoProfile = photoProfile;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTimeAndLocation() {
        return timeAndLocation;
    }

    public void setTimeAndLocation(String timeAndLocation) {
        this.timeAndLocation = timeAndLocation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusEventPhoto() {
        return statusEventPhoto;
    }

    public void setStatusEventPhoto(String statusEventPhoto) {
        this.statusEventPhoto = statusEventPhoto;
    }

    public Integer getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(Integer totalLike) {
        this.totalLike = totalLike;
    }

    public Integer getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(Integer totalComment) {
        this.totalComment = totalComment;
    }
}
