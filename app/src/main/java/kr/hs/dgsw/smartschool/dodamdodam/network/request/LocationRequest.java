package kr.hs.dgsw.smartschool.dodamdodam.network.request;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

import kr.hs.dgsw.smartschool.dodamdodam.Model.location.Location;
import kr.hs.dgsw.smartschool.dodamdodam.Model.location.LocationInfo;
import kr.hs.dgsw.smartschool.dodamdodam.Model.place.Place;
import kr.hs.dgsw.smartschool.dodamdodam.Model.timetable.Time;

public class LocationRequest {
    private List<Location> locations = new ArrayList<>();

    public LocationRequest(List<LocationInfo> timePlaceMap) {
        locations.clear();
        Stream.of(timePlaceMap).forEach(locationInfo -> {
            Time time = locationInfo.getTime();
            Place place = locationInfo.getPlace();

            if (place != null) {
                locations.add(new Location(time.getIdx(), place.getIdx()));
            } else {
                locations.add(new Location(time.getIdx(), null));
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T extends Location> List<T> getLocations() {
        return (List<T>) locations;
    }

    public void setLocations(List<LocationInfo> timePlaceMap) {
        Stream.of(timePlaceMap).forEach(locationInfo -> {
            Time time = locationInfo.getTime();
            Place place = locationInfo.getPlace();

            LocationInfo location = findLocationByTimeIdx(time.getIdx());

            if (location.getChecked() == null) location.setChecked(false);

            if (location.getPlaceIdx() != null) {

                if (place == null) location.setPlaceIdx(null);
                else location.setPlaceIdx(place.getIdx());

                int index = locations.indexOf(location);
                locations.remove(index);
                locations.add(index, location);
            } else if (place != null) {
                location.setPlaceIdx(place.getIdx());
                int index = locations.indexOf(location);
                locations.remove(index);
                locations.add(index, location);
            }
        });
    }

    public LocationInfo findLocationByTimeIdx(Integer timeIdx) {
        for (Location location : locations) {
            LocationInfo locationInfo = (LocationInfo) location;
            if (locationInfo.getTimetableIdx() == timeIdx) {
                return locationInfo;
            }
        }
        return new LocationInfo(timeIdx, null);
    }
}
