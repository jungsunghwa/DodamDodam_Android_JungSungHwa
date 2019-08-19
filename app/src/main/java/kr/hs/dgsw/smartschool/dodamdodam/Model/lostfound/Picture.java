package kr.hs.dgsw.smartschool.dodamdodam.Model.lostfound;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Picture implements Parcelable {
    private String original_name;
    private String upload_name;
    private String type;
    private String url;

    public Picture(Parcel in) {
        original_name = in.readString();
        upload_name = in.readString();
        type = in.readString();
        url = in.readString();
    }

    public Picture(String original_name, String upload_name, String type) {
        this.original_name = original_name;
        this.upload_name = upload_name;
        this.type = type;
    }

    public static final Creator<Picture> CREATOR = new Creator<Picture>() {
        @Override
        public Picture createFromParcel(Parcel in) {
            return new Picture(in);
        }

        @Override
        public Picture[] newArray(int size) {
            return new Picture[size];
        }
    };

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public String getUpload_name() {
        return upload_name;
    }

    public void setUpload_name(String upload_name) {
        this.upload_name = upload_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(original_name);
        dest.writeString(upload_name);
        dest.writeString(type);
        dest.writeString(url);
    }
}
