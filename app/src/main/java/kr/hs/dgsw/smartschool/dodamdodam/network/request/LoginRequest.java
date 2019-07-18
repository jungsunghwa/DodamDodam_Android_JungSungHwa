package kr.hs.dgsw.smartschool.dodamdodam.network.request;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginRequest {
    private String id;
    private String pw;
    private String device;

    public LoginRequest(String id, String pw) {
        try {
            this.id = id;
            this.pw = encryptSHA512(pw);
            this.device = "mobile";
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
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
