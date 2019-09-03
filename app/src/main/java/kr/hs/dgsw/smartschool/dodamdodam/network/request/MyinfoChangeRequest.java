package kr.hs.dgsw.smartschool.dodamdodam.network.request;

import kr.hs.dgsw.smartschool.dodamdodam.Model.lostfound.Picture;

public class MyinfoChangeRequest {

    private String mobile;
    private String email;
    private String status_message;
    private Picture profile_image;

    public MyinfoChangeRequest() {

    }

    public MyinfoChangeRequest(String mobile, String email, String status_message, Picture profile_image) {
        this.mobile = mobile;
        this.email = email;
        this.status_message = status_message;
        this.profile_image = profile_image;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    public void setProfile_image(Picture profile_image) {
        this.profile_image = profile_image;
    }
}
