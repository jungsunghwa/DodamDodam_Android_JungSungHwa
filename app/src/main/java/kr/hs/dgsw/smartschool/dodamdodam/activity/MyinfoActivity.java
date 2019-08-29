package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import kr.hs.dgsw.smartschool.dodamdodam.Model.location.LocationInfo;
import kr.hs.dgsw.smartschool.dodamdodam.Model.member.Student;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Leave;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Offbase;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.OffbaseItem;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Pass;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.MyinfoActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.BusViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.LocationViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.OffbaseViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.MyinfoOffBaseAdapter;
import kr.hs.dgsw.smartschool.dodamdodam.widget.viewpager.MyinfoPagerAdapter;

public class MyinfoActivity extends BaseActivity<MyinfoActivityBinding> {

    @Override
    protected int layoutId() {
        return R.layout.myinfo_activity;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        MyinfoPagerAdapter pagerAdapter = new MyinfoPagerAdapter(getSupportFragmentManager());

        binding.viewPager.setAdapter(pagerAdapter);
        binding.viewPager.setOffscreenPageLimit(2);

        viewPagerEvent();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }

    private void viewPagerEvent() {
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(position == 0) {
                    Glide.with(getApplicationContext()).load(R.drawable.ic_myinfo_page_ok).into(binding.pageProfile);
                    Glide.with(getApplicationContext()).load(R.drawable.ic_myinfo_page_no).into(binding.pageStatus);
                }
                else if(position == 1) {
                    Glide.with(getApplicationContext()).load(R.drawable.ic_myinfo_page_no).into(binding.pageProfile);
                    Glide.with(getApplicationContext()).load(R.drawable.ic_myinfo_page_ok).into(binding.pageStatus);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
