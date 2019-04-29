package kr.hs.dgsw.smartschool.dodamdodam.activity;

import androidx.annotation.Nullable;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.LocationCheckActivityBinding;

import android.graphics.Color;
import android.os.Bundle;

public class LocationCheckActivity extends BaseActivity<LocationCheckActivityBinding> {

    ListType listType;

    @Override
    protected void onCreateTablet(@Nullable Bundle savedInstanceState) {
        super.onCreateTablet(savedInstanceState);



        binding.toggle.setOnCheckedChangeListener((view, checkedId) ->{
            switch (checkedId){
                case R.id.list_type_class:
                    binding.listTypeClass.setTextColor(Color.WHITE);
                    binding.listTypePlace.setTextColor(Color.BLACK);
                    listType = ListType.CLASS;
                    break;

                case R.id.list_type_place:
                    binding.listTypeClass.setTextColor(Color.BLACK);
                    binding.listTypePlace.setTextColor(Color.WHITE);
                    listType = ListType.PLACE;
                    break;
            }
        });




    }

    @Override
    protected int layoutId() {
        return R.layout.location_check_activity;
    }
}

enum ListType{
    PLACE,
    CLASS
}
