package kr.hs.dgsw.smartschool.dodamdodam.network.request;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.hs.dgsw.smartschool.dodamdodam.Model.LocationInfo;
import kr.hs.dgsw.smartschool.dodamdodam.Model.location.Location;
import kr.hs.dgsw.smartschool.dodamdodam.Model.place.Place;
import kr.hs.dgsw.smartschool.dodamdodam.Model.timetable.Time;

public class LocationRequest<T extends Location> {
    private List<T> locations = new ArrayList<>();

    public LocationRequest(Map<Time, Place> timePlaceMap) {
        locations.clear();
        Stream.of(timePlaceMap).forEach(stringObjectEntry -> {
            Time time = stringObjectEntry.getKey();
            Place place = stringObjectEntry.getValue();

            if (place != null) {
                locations.add((T) new Location(time.getIdx(), place.getIdx()));
            } else {
                locations.add((T) new Location(time.getIdx(), null));
            }
        });
    }

    public List<T> getLocations() {
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
                locations.add(index, (T) location);
            }else if (place != null){
                location.setPlaceIdx(place.getIdx());
                locations.add((T) location);
            }else{
                locations.add((T) location);
            }
        });
    }

    public LocationInfo findLocationByTimeIdx(Integer timeIdx){
        for (T obj : locations) {
            LocationInfo location = (LocationInfo) obj;
            if (location.getTimetableIdx() == timeIdx){
                return location;
            }
        }
        return new LocationInfo(timeIdx,null);
    }
}
