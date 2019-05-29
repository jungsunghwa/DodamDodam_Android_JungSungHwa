package kr.hs.dgsw.smartschool.dodamdodam.network.request;

import java.util.List;

import kr.hs.dgsw.smartschool.dodamdodam.Model.lostfound.Picture;

public class LostFoundRequest {
    private Integer idx;
    private Integer type;
    private String title;
    private String upload_time;
    private String modify_time;
    private String place;
    private List<Picture> picture;
    private String content;
    private String contact;

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }

    public String getModify_time() {
        return modify_time;
    }

    public void setModify_time(String modify_time) {
        this.modify_time = modify_time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public List<Picture> getPicture() {
        return picture;
    }

    public void setPicture(List<Picture> picture) {
        this.picture = picture;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
