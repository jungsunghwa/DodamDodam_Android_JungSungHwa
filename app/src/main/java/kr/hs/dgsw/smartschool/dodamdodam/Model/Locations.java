package kr.hs.dgsw.smartschool.dodamdodam.Model;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Map;

public class Locations extends ArrayList<Location> {
    public Locations(Map<Time, Place> timePlaceMap) {
        this.clear();
        Stream.of(timePlaceMap).forEach(stringObjectEntry -> {
            Time time = stringObjectEntry.getKey();
            Place place = stringObjectEntry.getValue();

            if (place != null){
                this.add(new Location(time.getIdx(),place.getIdx()));
            }
        });
    }
}
