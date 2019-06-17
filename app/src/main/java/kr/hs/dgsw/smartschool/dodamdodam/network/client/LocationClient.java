package kr.hs.dgsw.smartschool.dodamdodam.network.client;

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
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.post.LocationService;
import retrofit2.Call;

public class LocationClient extends NetworkClient {
    private LocationService location;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    String date;

    public LocationClient() {
        location = Utils.RETROFIT.create(LocationService.class);
        date = sdf.format(new Date());
    }

    public Single<Response> putLocation(LocationInfo request, Token token) {
        request.setTimetableIdx(null);
        return location.putLocation(token.getToken(), request.getIdx(), request);
    }

    public Single<Response> postLocation(LocationRequest<LocationInfo> request, Token token) {
       return location.postLocation(token.getToken(), request).map(response -> response);
    }

    public Single<List<Locations>> getLocation(Token token) {
        return location.getLocation(token.getToken(), date).map(Response::getData);
    }

    public Single<LocationRequest> getMyLocation(Token token) {
        return location.getMyLocation(token.getToken(), date).map(Response::getData);
    }

    public Single<Response> checkLocation(String token, int idx) {
        Call<Response> service = location.checkLocation(token, idx);
        return actionService(service);
    }
}
