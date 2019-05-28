package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Identity;
import kr.hs.dgsw.smartschool.dodamdodam.Model.location.Location;
import kr.hs.dgsw.smartschool.dodamdodam.Model.location.LocationInfo;
import kr.hs.dgsw.smartschool.dodamdodam.Model.location.Locations;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.LocationRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.post.LocationService;
import retrofit2.Call;

public class LocationClient extends NetworkClient {
    private LocationService location;

    public LocationClient() {
        location = Utils.RETROFIT.create(LocationService.class);
    }

    public Single<Response> postLocation(LocationRequest<LocationInfo> request, String token, String method) {
        Call<Response> service = location.postLocation(token, request);
        if (method.equals("PUT")) service = location.putLocation(token, request);

        return actionService(service);
    }

    public Single<Response> getLocation(String token) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = sdf.format(new Date());


        if (Utils.identity == Identity.STUDENT) {
            Call<Response<LocationRequest<LocationInfo>>> service = location.getMyLocation(token, date);
            return actionService(service);
        } else if (Utils.identity == Identity.TEACHER) {
            Call<Response<List<Locations>>> service = location.getLocation(token, date);
            return actionService(service);
        }

        return actionService(null);
    }

    public Single<Response> checkLocation(String token, int idx){
        Call<Response> service = location.checkLocation(token, idx);
        return actionService(service);
    }
}
