package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Model.place.PlaceList;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.PlaceService;

public class PlaceClient extends NetworkClient{
    private PlaceService place;

    public PlaceClient() {
        place = Utils.RETROFIT.create(PlaceService.class);
    }

    public Single<PlaceList> getAllPlace(Token token) {
        return place.getAllPlace(token.getToken()).map(getResponseObjectsFunction());
    }
}
