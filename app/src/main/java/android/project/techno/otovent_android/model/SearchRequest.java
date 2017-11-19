package android.project.techno.otovent_android.model;

/**
 * Created by root on 19/11/17.
 */

public class SearchRequest {
    private String searchName;
    private String searchStatus;
    private String photoUrl;

    public SearchRequest(){}

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getSearchStatus() {
        return searchStatus;
    }

    public void setSearchStatus(String searchStatus) {
        this.searchStatus = searchStatus;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
