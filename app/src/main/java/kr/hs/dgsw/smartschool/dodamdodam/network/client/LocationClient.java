package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Identity;
import kr.hs.dgsw.smartschool.dodamdodam.Model.LocationInfo;
import kr.hs.dgsw.smartschool.dodamdodam.Model.location.Locations;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.LocationRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.post.LocationService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.internal.EverythingIsNonNull;

public class LocationClient extends NetworkClient {
    private LocationService location;
    private NetworkClient networkClient = new NetworkClient();

    public LocationClient() {
        location = Utils.RETROFIT.create(LocationService.class);
    }

    public Single<Response> postLocation(LocationRequest request, String token, String method) {
        Call<Response> service = location.postLocation(token, request);
        if (method.equals("PUT")) service = location.putLocation(token, request);

        return actionService(service);
    }

    public Single<Response> getLocation(String token) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());


        if (Utils.identity == Identity.STUDENT) {
            Call<Response<LocationRequest<LocationInfo>>> service = location.getMyLocation(token, date);
            return actionService(service);
        } else if (Utils.identity == Identity.TEACHER) {
            Call<Response<ArrayList<Locations>>> service = location.getLocation(token, date);
            return actionService(service);
        }

        return actionService(null);
    }
}
