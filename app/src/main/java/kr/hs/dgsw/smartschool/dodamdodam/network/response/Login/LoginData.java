package kr.hs.dgsw.smartschool.dodamdodam.network.response.Login;

import com.google.gson.annotations.SerializedName;

public class LoginData {
    private String token;
    @SerializedName("refresh_token")
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String value) {
        this.token = value;
    }
}
