package kr.hs.dgsw.smartschool.dodamdodam.Model.lostfound;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LostFound implements Parcelable {
    private Integer idx;
    private String member_id;
    private Integer type;
    private String title;
    private String upload_time;
    private String modify_time;
    private String place;
    private ArrayList<Picture> picture;
    private String content;
    private String contact;

    protected LostFound(Parcel in) {
        if (in.readByte() == 0) {
            idx = null;
        } else {
            idx = in.readInt();
        }
        member_id = in.readString();
        if (in.readByte() == 0) {
            type = null;
        } else {
            type = in.readInt();
        }
        title = in.readString();
        upload_time = in.readString();
        modify_time = in.readString();
        place = in.readString();
        picture = in.createTypedArrayList(Picture.CREATOR);
        content = in.readString();
        contact = in.readString();
    }

    public static final Creator<LostFound> CREATOR = new Creator<LostFound>() {
        @Override
        public LostFound createFromParcel(Parcel in) {
            return new LostFound(in);
        }

        @Override
        public LostFound[] newArray(int size) {
            return new LostFound[size];
        }
    };

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getMemberId() {
        return member_id;
    }

    public void setMemberId(String member_id) {
        this.member_id = member_id;
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

    public void setPicture(ArrayList<Picture> picture) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idx);
        dest.writeString(member_id);
        dest.writeInt(type);
        dest.writeString(title);
        dest.writeString(upload_time);
        dest.writeString(modify_time);
        dest.writeString(place);
        dest.writeList(picture);
        dest.writeString(content);
        dest.writeString(contact);
    }
}
