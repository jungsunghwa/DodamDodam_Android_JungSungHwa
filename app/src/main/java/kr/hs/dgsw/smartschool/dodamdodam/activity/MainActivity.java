package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;

import com.annimon.stream.Optional;
import com.scwang.wave.Util;

import java.util.Calendar;
import java.util.Date;

import kr.hs.dgsw.smartschool.dodamdodam.Model.Identity;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.activity.offbase.OffbaseActivity;
import kr.hs.dgsw.smartschool.dodamdodam.activity.song.SongListActivity;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.MainActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.MainViewModel;

public class MainActivity extends BaseActivity<MainActivityBinding> implements OnDateClickListener, DatePickerDialog.OnDateSetListener {

    private static final String TAG = "MainActivity";

    private MainViewModel viewModel;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected int layoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new MainViewModel(this);

        viewModel.getMealData().observe(this, meal -> {
            binding.mealItems.mealLunch.setLoading(false);
            if (meal.isExists()) {
                binding.mealItems.mealBreakfast.setMeal(Optional
                        .ofNullable(meal.getBreakfast())
                        .orElse(getString(R.string.text_meal_empty)));
                binding.mealItems.mealLunch.setMeal(Optional
                        .ofNullable(meal.getLunch())
                        .orElse(getString(R.string.text_meal_empty)));
                binding.mealItems.mealDinner.setMeal(Optional
                        .ofNullable(meal.getDinner())
                        .orElse(getString(R.string.text_meal_empty)));
            } else {
                binding.mealItems.mealBreakfast.setMeal(null);
                binding.mealItems.mealLunch.setMeal(getString(R.string.text_meal_empty));
                binding.mealItems.mealDinner.setMeal(null);
            }
        });
        viewModel.getLoading().observe(this, loading -> binding.mealItems.mealLunch.setLoading(loading));
        viewModel.getError().observe(this, error -> {
            Log.w(TAG, "ERROR: ", error);
            binding.mealItems.mealLunch.setLoading(false);
            binding.mealItems.mealLunch.setError(error.getMessage());
        });

        viewModel.today();

        binding.appbarLayout.wave.setVisibility(View.GONE);

        showWithAnimate();

        ActionBar actionBar = getSupportActionBar();

        binding.navView.setNavigationItemSelectedListener(this::onOptionsItemSelected);
        binding.navView.getMenu().getItem(3).setVisible(Utils.identity == Identity.TEACHER);

        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        binding.fabDateBack.setOnClickListener(((OnDateClickListener) this)::onDateBackClick);
        binding.fabDateToday.setOnClickListener(((OnDateClickListener) this)::onDateTodayClick);
        binding.fabDateForward.setOnClickListener(((OnDateClickListener) this)::onDateForwardClick);

    }

    @Override
    protected void onCreatePhone(@Nullable Bundle savedInstanceState) {
        super.onCreatePhone(savedInstanceState);
        drawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.appbarLayout.toolbar, R.string.desc_drawer_open, R.string.desc_drawer_close);
        binding.drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
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
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (!isTablet()) drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (!isTablet() && binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;
        switch (id) {
            case R.id.menu_song_apply:
                if (Utils.identity == Identity.STUDENT)
                    intent = new Intent(this, SongListActivity.class);
                else {
                    notSupportToast();
                    break;
                }
                startActivity(intent);
                break;
            case R.id.menu_location_apply:
                if (Utils.identity == Identity.STUDENT)
                    intent = new Intent(this, LocationApplyActivity.class);
                else intent = new Intent(this, LocationCheckActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_offbase:
                if (Utils.identity == Identity.STUDENT)
                    intent = new Intent(this, OffbaseActivity.class);
                else {
                    notSupportToast();
                    break;
                }
                startActivity(intent);
                break;
            case R.id.menu_bus_apply:
                if (Utils.identity == Identity.STUDENT)
                    intent = new Intent(this, BusApplyActivity.class);
                else {
                    notSupportToast();
                    break;
                }
                startActivity(intent);
                break;
            case R.id.menu_counsel_apply:
                if (Utils.identity == Identity.STUDENT)
                    intent = new Intent(this, CounselActivity.class);
                else {
                    notSupportToast();
                    break;
                }
                startActivity(intent);
                break;
            case R.id.menu_lost_found:
                if (Utils.identity == Identity.STUDENT)
                    intent = new Intent(this, LostFoundActivity.class);
                else {
                    notSupportToast();
                    break;
                }
                startActivity(intent);
                break;
            default :
                notSupportToast();
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
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        viewModel.date(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth, 0, 0, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        ((OnDateClickListener) this).onDateChanged(calendar.getTime());
    }
}

interface OnDateClickListener {
    void onDateBackClick(View v);

    void onDateTodayClick(View v);

    void onDateForwardClick(View v);

    void onDateChanged(Date date);
}