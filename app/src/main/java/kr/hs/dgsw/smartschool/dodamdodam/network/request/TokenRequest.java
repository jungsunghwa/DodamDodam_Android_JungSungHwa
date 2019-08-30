package kr.hs.dgsw.smartschool.dodamdodam.network.request;

public class TokenRequest {

    private String refresh_token;

    public TokenRequest(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
