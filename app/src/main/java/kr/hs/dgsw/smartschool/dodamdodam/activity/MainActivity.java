package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import androidx.appcompat.app.ActionBar;

import com.google.android.material.picker.MaterialDatePickerDialog;

import java.util.Calendar;
import java.util.Date;

import kr.hs.dgsw.b1nd.bottomsheet.B1ndBottomSheetDialogFragment;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.MainActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.widget.DodamDateExtendedFloatingActionButton;

public class MainActivity extends BaseActivity<MainActivityBinding> implements OnDateClickListener, DatePickerDialog.OnDateSetListener {

    @Override
    protected int layoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.appbarLayout.wave.setVisibility(View.GONE);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        binding.fabDateBack.setOnClickListener(((OnDateClickListener) this)::onDateBackClick);
        binding.fabDateToday.setOnClickListener(((OnDateClickListener) this)::onDateTodayClick);
        binding.fabDateForward.setOnClickListener(((OnDateClickListener) this)::onDateForwardClick);
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
                        .setEmail("이메일");
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

    @Override
    public void onDateBackClick(View v) {
        Date currentDate = binding.fabDateToday.getCurrentDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
        binding.fabDateToday.setCurrentDate(calendar.getTime());
    }

    @Override
    public void onDateTodayClick(View v) {
        Date currentDate = binding.fabDateToday.getCurrentDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateForwardClick(View v) {
        Date currentDate = binding.fabDateToday.getCurrentDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        binding.fabDateToday.setCurrentDate(calendar.getTime());
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        binding.fabDateToday.setCurrentDate(calendar.getTime());
        //TODO MEAL CHANGE
    }
}

interface OnDateClickListener {
    void onDateBackClick(View v);
    void onDateTodayClick(View v);
    void onDateForwardClick(View v);
}