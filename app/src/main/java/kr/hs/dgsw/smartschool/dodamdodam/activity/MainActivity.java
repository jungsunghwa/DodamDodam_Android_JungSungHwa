package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import kr.hs.dgsw.b1nd.bottomsheet.B1ndBottomSheetDialogFragment;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.MainActivityBinding;

public class MainActivity extends BaseActivity<MainActivityBinding> {

    @Override
    protected int layoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(binding.appbarLayout.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                B1ndBottomSheetDialogFragment bottomSheetDialogFragment = new B1ndBottomSheetDialogFragment()
                        .setProfileImageResource(android.R.drawable.sym_def_app_icon, getResources())
                        .setSubIconImageResource(android.R.drawable.ic_lock_power_off, getResources())
                        .setName("이름")
                        .setEmail("이메일")
                        .setTemper("TEMPER");
                bottomSheetDialogFragment.menuInflate(R.menu.menu_main);
                bottomSheetDialogFragment.setOnBottomSheetOptionsItemSelectedListener(this::onOptionsItemSelected);
                bottomSheetDialogFragment.show(getSupportFragmentManager(), "bottom");
                break;
            case R.id.menu_song_apply:
                startActivity(new Intent(this, SongApplyActivity.class));
                break;
            case R.id.menu_location_apply:
                startActivity(new Intent(this, LocationApplyActivity.class));
                break;
        }

        return false;
    }
}
