package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.get.PlaceService;
import retrofit2.Call;

public class PlaceClient extends NetworkClient {
    private PlaceService place;

    public PlaceClient() {
        place = Utils.RETROFIT.create(PlaceService.class);
    }

    public Single<Response> getAllPlace(Token token) {
        Call service = place.getAllPlace(token.getToken());

        return actionService(service);

        /*return Single.create(observer -> place.getAllPlace(token.getToken()).enqueue(new Callback<Response<PlaceList>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Response<PlaceList>> call, retrofit2.Response<Response<PlaceList>> response) {
                if (response.isSuccessful()){
                    observer.onSuccess(response.body().getData().getPlaces());
                } else {
                    try {
                        JSONObject errorBody = new JSONObject(Objects
                                .requireNonNull(
                                        response.errorBody()).string());
                        observer.onError(new Throwable(errorBody.getString("message")));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Response<PlaceList>> call, Throwable t) {
                observer.onError( new Throwable("네트워크 상태를 확인하세요"));
            }
        }));*/
    }
}
