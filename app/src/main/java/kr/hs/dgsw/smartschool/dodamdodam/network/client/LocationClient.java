package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Model.location.LocationInfo;
import kr.hs.dgsw.smartschool.dodamdodam.Model.location.Locations;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.LocationRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.LocationService;

public class LocationClient extends NetworkClient {
    private LocationService location;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    String date;

    public LocationClient(Context context) {
        super(context);
        location = Utils.RETROFIT.create(LocationService.class);
        date = sdf.format(new Date());
    }

    public Single<String> putLocation(LocationInfo request, Token token) {
        request.setTimetableIdx(null);
        return location.putLocation(token.getToken(), request.getIdx(), request).map(getResponseMessageFunction());
    }

    public Single<String> deleteLocation(Token token, int idx) {
        return location.deleteLocation(token.getToken(), idx).map(getResponseMessageFunction());
    }

    public Single<String> postLocation(LocationRequest<LocationInfo> request, Token token) {
        return location.postLocation(token.getToken(), request).map(getResponseMessageFunction());
    }

    public Single<List<Locations>> getLocation(Token token) {
        return location.getLocation(token.getToken(), date).map(getResponseObjectsFunction());
    }

    public Single<LocationRequest> getMyLocation(Token token) {
        return location.getMyLocation(token.getToken(), date).map(getResponseObjectsFunction());
    }

    public Single<String> checkLocation(Token token, int idx) {
        return location.checkLocation(token.getToken(), idx).map(getResponseMessageFunction());
    }

    public Single<String> unCheckLocation(Token token, int idx) {
        return location.unCheckLocation(token.getToken(), idx).map(getResponseMessageFunction());
    }

}
