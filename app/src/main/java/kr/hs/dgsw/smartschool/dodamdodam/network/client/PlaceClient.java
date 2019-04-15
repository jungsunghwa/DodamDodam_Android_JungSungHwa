package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Place;
import kr.hs.dgsw.smartschool.dodamdodam.Model.PlaceList;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Time;
import kr.hs.dgsw.smartschool.dodamdodam.Model.TimeTables;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.GetPlace;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.GetTimeTable;
import retrofit2.Call;
import retrofit2.Callback;

public class PlaceClient {
    private GetPlace getPlace;

    public PlaceClient(){
        getPlace = Utils.RETROFIT.create(GetPlace.class);
    }

    public Single<List<Place>> getAllPlace(Token token) {
        return Single.create(observer -> {
            getPlace.getAllPlace(token.getToken()).enqueue(new Callback<Response<PlaceList>>() {
                @Override
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
                public void onFailure(Call<Response<PlaceList>> call, Throwable t) {
                    observer.onError( new Throwable("네트워크상태를 확인하세요"));
                }
            });
        });
    }
}
