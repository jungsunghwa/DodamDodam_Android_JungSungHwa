package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import kr.hs.dgsw.smartschool.dodamdodam.Model.place.Place;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseGetDataType;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.LocationCheckActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.PlaceViewModel;


public class LocationCheckActivity extends BaseActivity<LocationCheckActivityBinding> {
    ListType listType;
    ArrayList<Place> placeList = new ArrayList<>();
    DatabaseHelper databaseHelper;
    PlaceViewModel placeViewModel;

    @Override
    protected int layoutId() {
        return R.layout.location_check_activity;
    }

    @Override
    protected void onCreateTablet(@Nullable Bundle savedInstanceState) {
        super.onCreateTablet(savedInstanceState);

        placeViewModel = new PlaceViewModel(this);

        placeViewModel.getAllPlace();

        placeViewModel.getData().observe(this, data -> {
            placeList = (ArrayList<Place>) data;

            binding.placeSpinner.setItems(placeList);

            binding.placeSpinner.setOnItemSelectedListener((MaterialSpinner.OnItemSelectedListener<String>)
                    (view, position, id, item) ->
                    Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show());
        });

        binding.toggle.setOnCheckedChangeListener((view, checkedId) -> {
            switch (checkedId) {
                case R.id.list_type_class:
                    binding.listTypeClass.setTextColor(Color.WHITE);
                    binding.listTypePlace.setTextColor(Color.BLACK);
                    binding.classSpinnerLayout.setVisibility(View.VISIBLE);
                    binding.placeSpinnerLayout.setVisibility(View.GONE);
                    listType = ListType.CLASS;
                    break;

                case R.id.list_type_place:
                    binding.listTypeClass.setTextColor(Color.BLACK);
                    binding.listTypePlace.setTextColor(Color.WHITE);
                    binding.classSpinnerLayout.setVisibility(View.GONE);
                    binding.placeSpinnerLayout.setVisibility(View.VISIBLE);
                    listType = ListType.PLACE;
                    break;
            }
        });

    }

}

enum ListType {
    PLACE,
    CLASS
}
