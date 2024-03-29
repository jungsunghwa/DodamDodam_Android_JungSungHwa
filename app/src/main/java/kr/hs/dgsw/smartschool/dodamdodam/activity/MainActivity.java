package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import com.annimon.stream.Optional;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;

import kr.hs.dgsw.smartschool.dodamdodam.Model.Identity;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.activity.offbase.OffbaseActivity;
import kr.hs.dgsw.smartschool.dodamdodam.activity.song.SongListActivity;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.MainActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.MainViewModel;

interface OnDateClickListener {
    void onDateBackClick(View v);

    void onDateTodayClick(View v);

    void onDateForwardClick(View v);

    void onDateChanged(Date date);
}

public class MainActivity extends BaseActivity<MainActivityBinding> implements OnDateClickListener, DatePickerDialog.OnDateSetListener {

    private MainViewModel viewModel;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected int layoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        viewModel.getData().observe(this, meal -> {
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

        viewModel.getErrorMessage().observe(this, error -> {
            binding.mealItems.mealBreakfast.setMeal(null);
            binding.mealItems.mealLunch.setLoading(false);
            binding.mealItems.mealLunch.setError(error);
            binding.mealItems.mealDinner.setMeal(null);
        });

        viewModel.today();

        binding.appbarLayout.wave.setVisibility(View.GONE);

        ActionBar actionBar = getSupportActionBar();

        binding.navView.setNavigationItemSelectedListener(this::onOptionsItemSelected);
        binding.navView.getMenu().getItem(3).setVisible(Utils.identity == Identity.STUDENT);
        binding.navView.getChildAt(0).setVerticalScrollBarEnabled(false);

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
        binding.drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                lightStatusMode();
                lightNavMode();
                binding.mealItems.mealLunch.setLoading(false);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                darkStatusMode();
                darkNavMode();
                binding.mealItems.mealLunch.setLoading(false);
            }
        });
        drawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.appbarLayout.toolbar, R.string.desc_drawer_open, R.string.desc_drawer_close);
        binding.drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @Override
    protected void onCreateTablet(@Nullable Bundle savedInstanceState) {
        super.onCreateTablet(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (!isTablet() && drawerToggle != null) drawerToggle.onConfigurationChanged(null);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;

        switch (id) {
            case R.id.menu_settings:
                startActivity(new Intent(this, PreferenceActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                break;
            case R.id.menu_profile:
                if (Utils.identity == Identity.STUDENT)
                    intent = new Intent(this, MyinfoActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                else { // todo 선생님용 MyInfo
                    intent = new Intent(this, MyinfoActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                }
                startActivity(intent);
                break;
            case R.id.menu_song_apply:
                if (Utils.identity == Identity.STUDENT)
                    intent = new Intent(this, SongListActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                else {
                    notSupportToast();
                    break;
                }
                startActivity(intent);
                break;
            case R.id.menu_location_apply:
                if (Utils.identity == Identity.STUDENT)
                    intent = new Intent(this, LocationApplyActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                else {
                    // todo 선생님일시 랩실 신청 -> 랩실 체크
                    intent = new Intent(this, LocationCheckActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                }
                startActivity(intent);
                break;
            case R.id.menu_offbase:
                if (Utils.identity == Identity.STUDENT)
                    intent = new Intent(this, OffbaseActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                else {
                    notSupportToast();
                    break;
                }
                startActivity(intent);
                break;
            case R.id.menu_bus_apply:
                if (Utils.identity == Identity.STUDENT)
                    intent = new Intent(this, BusApplyActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                else {
                    notSupportToast();
                    break;
                }
                startActivity(intent);
                break;
            case R.id.menu_counsel_apply:
                if (Utils.identity == Identity.STUDENT)
                    intent = new Intent(this, CounselActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                else {
                    notSupportToast();
                    break;
                }
                startActivity(intent);
                break;
            case R.id.menu_lost_found:
                if (Utils.identity == Identity.STUDENT)
                    intent = new Intent(this, LostFoundActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                else {
                    notSupportToast();
                    break;
                }
                startActivity(intent);
                break;
            default:
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