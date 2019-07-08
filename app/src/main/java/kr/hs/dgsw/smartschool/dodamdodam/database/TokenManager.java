package kr.hs.dgsw.smartschool.dodamdodam.database;

import android.content.Context;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;

public final class TokenManager {
    private static final String PAYLOAD_MEMBER_ID = "memberId";
    private static final String PAYLOAD_EXPIRATION_TIME = "exp";
    private static TokenManager INSTANCE;
    private DatabaseHelper helper;

    private TokenManager(Context context) {
        helper = DatabaseHelper.getInstance(context);
    }

    public static TokenManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (TokenManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TokenManager(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }

    public static boolean isValidate(String id, String tokenString) {
        try {
            JSONObject payload = decodedPayloadObject(tokenString);
            String tokenMemberId = payload.getString(PAYLOAD_MEMBER_ID);
            if (!id.equals(tokenMemberId)) {
                return false;
            }
        } catch (JSONException ignore) {
        }
        return true;
    }

    public static String getId(Token token) {
        try {
            JSONObject payload = decodedPayloadObject(token.getToken());
            return payload.getString(PAYLOAD_MEMBER_ID);
        } catch (JSONException ignore) {
            return null;
        }
    }

    private static JSONObject decodedPayloadObject(String tokenString) {
        try {
            String[] split = tokenString.split("\\.");
            return new JSONObject(new String(Base64.decode(split[1], Base64.DEFAULT)));
        } catch (JSONException ignore) {
            return null;
        }
    }

    public Token getToken() {
        return helper.getToken();
    }

    public void setToken(Token token) {
        helper.insert(DatabaseManager.TABLE_TOKEN, token);
    }

    public void setToken(String tokenString, String refreshTokenString) {
        Token token = new Token();
        token.setToken(tokenString);
        token.setRefreshToken(refreshTokenString);
        setToken(token);
    }

    public boolean isValidate() {
        try {
            Token token = getToken();
            if (token.isEmpty()) return false;
            JSONObject payload = decodedPayloadObject(token.getToken());
            Date expDate = new Date(payload.getLong(PAYLOAD_EXPIRATION_TIME) * 1000);
            return expDate.compareTo(new Date()) >= 0;
        } catch (JSONException ignore) {
        }
        return false;
    }

    public boolean isValidateRefresh() {
        try {
            Token token = getToken();
            if (token.isEmpty()) return false;
            JSONObject payload = decodedPayloadObject(token.getRefreshToken());
            Date expDate = new Date(payload.getLong(PAYLOAD_EXPIRATION_TIME) * 1000);
            return expDate.compareTo(new Date()) >= 0;
        } catch (JSONException ignore) {
        }
        return false;
    }

    public String getMyId() {
        try {
            Token token = getToken();
            JSONObject payload = decodedPayloadObject(token.getToken());
            return payload.getString(PAYLOAD_MEMBER_ID);
        } catch (JSONException ignore) {
            return null;
        }
    }
}
