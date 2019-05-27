package kr.hs.dgsw.smartschool.dodamdodam.network.request;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

import kr.hs.dgsw.b1nd.service.model.ClassInfo;
import kr.hs.dgsw.smartschool.dodamdodam.Model.location.LocationInfo;
import kr.hs.dgsw.smartschool.dodamdodam.Model.location.Location;
import kr.hs.dgsw.smartschool.dodamdodam.Model.place.Place;
import kr.hs.dgsw.smartschool.dodamdodam.Model.timetable.Time;

public class LocationRequest<T extends Location> {
    private List<T> locations = new ArrayList<>();

    public LocationRequest(List<LocationInfo> timePlaceMap, ClassInfo classInfo) {
        locations.clear();
        Stream.of(timePlaceMap).forEach(locationInfo -> {
            Time time = locationInfo.getTime();
            Place place = locationInfo.getPlace();

            if (place != null) {
                locations.add((T)new Location(time.getIdx(), place.getIdx()));
            } else {
                locations.add((T)new Location(time.getIdx(), classInfo.getPlaceIdx()));
            }
        });
    }

    public List<T> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationInfo> timePlaceMap, ClassInfo classInfo) {
        Stream.of(timePlaceMap).forEach(locationInfo -> {
            Time time = locationInfo.getTime();
            Place place = locationInfo.getPlace();

            LocationInfo location = findLocationByTimeIdx(time.getIdx(), classInfo);

            if (location.getChecked() == null) location.setChecked(false);

            if (location.getPlaceIdx() != null) {

                if (place == null) location.setPlaceIdx(classInfo.getPlaceIdx());
                else location.setPlaceIdx(place.getIdx());

                int index = locations.indexOf(location);
                locations.remove(index);
                locations.add(index, (T) location);
            } else if (place != null) {
                location.setPlaceIdx(place.getIdx());
                int index = locations.indexOf(location);
                locations.remove(index);
                locations.add(index, (T) location);
            }else {
                if (place == null) location.setPlaceIdx(classInfo.getPlaceIdx());
                else location.setPlaceIdx(place.getIdx());
            }
        });
    }

    public LocationInfo findLocationByTimeIdx(Integer timeIdx,ClassInfo classInfo) {
        for (Location location : locations) {
            LocationInfo locationInfo = (LocationInfo) location;
            if (locationInfo.getTimetableIdx() == timeIdx) {
                return locationInfo;
            }
        }
        return new LocationInfo(timeIdx, classInfo.getPlaceIdx());
    }
}
