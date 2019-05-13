package kr.hs.dgsw.smartschool.dodamdodam.network.request;

import android.content.Intent;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.hs.dgsw.smartschool.dodamdodam.Model.Location;
import kr.hs.dgsw.smartschool.dodamdodam.Model.LocationInfo;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Place;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Time;

public class LocationRequest {
    private List<LocationInfo> locations = new ArrayList<>();

    public LocationRequest(Map<Time, Place> timePlaceMap) {
        locations.clear();
        Stream.of(timePlaceMap).forEach(stringObjectEntry -> {
            Time time = stringObjectEntry.getKey();
            Place place = stringObjectEntry.getValue();

            if (place != null) {
                locations.add(new LocationInfo(time.getIdx(), place.getIdx()));
            } else {
                locations.add(new LocationInfo(time.getIdx(), null));
            }
        });
    }

    public List<LocationInfo> getLocations() {
        return locations;
    }

    public void setLocations(Map<Time, Place> timePlaceMap) {
        Stream.of(timePlaceMap).forEach(stringObjectEntry -> {
            Time time = stringObjectEntry.getKey();
            Place place = stringObjectEntry.getValue();

            LocationInfo location = findLocationByTimeIdx(time.getIdx());

            if (location.getPlaceIdx() != null) {

                if (place == null) location.setPlaceIdx(null);
                else location.setPlaceIdx(place.getIdx());

                int index = locations.indexOf(location);
                locations.remove(index);
                locations.add(index, location);
            }else if (place != null){
                location.setPlaceIdx(place.getIdx());
                locations.add(location);
            }else{
                locations.add(location);
            }
        });
    }

    public LocationInfo findLocationByTimeIdx(Integer timeIdx){
        for (LocationInfo location : locations) {
            if (location.getTimetableIdx() == timeIdx){
                return location;
            }
        }
        return new LocationInfo(timeIdx,null);
    }
}
