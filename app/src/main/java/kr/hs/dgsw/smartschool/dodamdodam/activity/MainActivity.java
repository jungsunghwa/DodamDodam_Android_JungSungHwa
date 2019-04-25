package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.DatePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import java.util.Calendar;
import java.util.Date;

import kr.hs.dgsw.b1nd.bottomsheet.B1ndBottomSheetDialogFragment;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.MainActivityBinding;

public class MainActivity extends BaseActivity<MainActivityBinding> implements OnDateClickListener, DatePickerDialog.OnDateSetListener {

    @Override
    protected int layoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onCreatePhone(@Nullable Bundle savedInstanceState) {
        super.onCreatePhone(savedInstanceState);

        binding.appbarLayout.wave.setVisibility(View.GONE);

        showWithAnimate();

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
    protected void onCreateTablet(@Nullable Bundle savedInstanceState) {
        super.onCreateTablet(savedInstanceState);
    }

    private void showWithAnimate() {
        binding.rootLayout.animate().setDuration(500).alpha(1);
        binding.waveHeader.start();
        ValueAnimator animator = ObjectAnimator.ofFloat(binding.waveHeader, View.ALPHA, 0, 2).setDuration(1000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
        ((OnDateClickListener) this).onDateChanged(calendar.getTime());
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
        ((OnDateClickListener) this).onDateChanged(calendar.getTime());
    }

    @Override
    public void onDateChanged(Date date) {
        binding.fabDateToday.setCurrentDate(date);
        //TODO MEAL CHANGE
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        ((OnDateClickListener) this).onDateChanged(calendar.getTime());
    }
}

interface OnDateClickListener {
    void onDateBackClick(View v);

    void onDateTodayClick(View v);

    void onDateForwardClick(View v);

    void onDateChanged(Date date);
}