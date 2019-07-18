package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.PointListActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.widget.viewpager.PointPagerAdapter;

public class PointListActivity extends BaseActivity<PointListActivityBinding> {

    private ViewPager viewPager;

    @Override
    protected int layoutId() {
        return R.layout.point_list_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPager = binding.viewPager;
        initToolbar();
    }

    private void initToolbar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setSubtitle(R.string.subtitle_point_list);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        TabLayout tabLayout = binding.appbarLayout.toolbarTab;
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new PointPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
