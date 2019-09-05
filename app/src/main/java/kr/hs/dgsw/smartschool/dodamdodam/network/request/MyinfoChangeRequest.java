package kr.hs.dgsw.smartschool.dodamdodam.network.request;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import kr.hs.dgsw.smartschool.dodamdodam.Model.lostfound.Picture;

public class MyinfoChangeRequest {

    private String mobile;
    private String email;
    private String status_message;
    private String profile_image;
    private String pw;

    public MyinfoChangeRequest() {

    }

    public MyinfoChangeRequest(String mobile, String email, String status_message, String profile_image) {
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

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public void setPassword(String pw) {
        try {
            this.pw = encryptSHA512(pw);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private String encryptSHA512(String target) throws NoSuchAlgorithmException {
        final MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
        final StringBuilder encryptedPassword = new StringBuilder();

        messageDigest.update(target.getBytes());

        final byte[] buffer = messageDigest.digest();

        for (byte temp : buffer) {

            StringBuilder sb = new StringBuilder(Integer.toHexString(temp));

            while (sb.length() < 2) {

                sb.insert(0, "0");
            }

            sb = new StringBuilder(sb.substring(sb.length() - 2));
            encryptedPassword.append(sb);
        }

        return encryptedPassword.toString();
    }
}
