package kr.hs.dgsw.smartschool.dodamdodam.network.request;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.hs.dgsw.smartschool.dodamdodam.Model.member.Student;
import kr.hs.dgsw.smartschool.dodamdodam.Model.location.LocationInfo;
import kr.hs.dgsw.smartschool.dodamdodam.Model.location.Location;
import kr.hs.dgsw.smartschool.dodamdodam.Model.place.Place;
import kr.hs.dgsw.smartschool.dodamdodam.Model.timetable.Time;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;

public class LocationRequest<T extends Location> {
    private List<T> locations = new ArrayList<>();

    public LocationRequest(List<LocationInfo> timePlaceMap) {
        locations.clear();
        Stream.of(timePlaceMap).forEach(locationInfo -> {
            Time time = locationInfo.getTime();
            Place place = locationInfo.getPlace();

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
                locations.add(index, (T) location);
            } else if (place != null) {
                location.setPlaceIdx(place.getIdx());
                int index = locations.indexOf(location);
                locations.remove(index);
                locations.add(index,(T) location);
            }
        });
    }

    public LocationInfo findLocationByTimeIdx(Integer timeIdx) {
        for (T obj : locations) {
            LocationInfo location = (LocationInfo) obj;
            if (location.getTimetableIdx() == timeIdx) {
                return location;
            }
        }
        return new LocationInfo(timeIdx, null);
    }
}
