package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Bus;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.BusResponse;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.BusApplyActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.PostBusRequest;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.BusViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.BusAdapter;

public class BusApplyActivity extends BaseActivity<BusApplyActivityBinding> {

    private BusViewModel busViewModel;
    private BusAdapter busAdapter = new BusAdapter();
    private List<Bus> busList = new ArrayList<>();
    private List<Bus> busMyApply = new ArrayList<>();
    private int choiceDate = 0;

    @Override
    protected int layoutId() {
        return R.layout.bus_apply_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initViewModel();
        initData();

        observeBusViewModel();
        observeBusAdapter();
    }

    private void observeBusViewModel() {
        busViewModel.getResponseAllBusList().observe(this, responseAllBusList -> {
            setRecyclerview();
            setBusList(sortBusList(responseAllBusList));
            busAdapter.setBusList(busList);
            busViewModel.getMyBusApply();
        });

        busViewModel.getResponseMyBusList().observe(this, responseMyBusList -> {
            setMyBusList(responseMyBusList);
            busAdapter.setBusMyApply(busMyApply);
        });

        busViewModel.getErrorMessage().observe(this, message -> {
            Log.e("err", message);
            Snackbar.make(binding.rootLayout, message, Snackbar.LENGTH_SHORT).show();
        });

        busViewModel.getSuccessMessage().observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
    }

    private void observeBusAdapter() {
        busAdapter.getPostBus().observe(this, bus_idx -> {
            busViewModel.postBusApply(new PostBusRequest(bus_idx.toString()));
        });

        busAdapter.getDeleteBus().observe(this, busIdx -> {
            busViewModel.deleteBusApply(busIdx);
        });

        busAdapter.getPutBus().observe(this, busRequest -> {
            busViewModel.putBusApply(busRequest);
        });
    }

    private List<BusResponse> sortBusList(List<BusResponse> responseAllBusList) {
        Collections.sort(responseAllBusList, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        return responseAllBusList;
    }

    private void setBusList(List<BusResponse> responseAllBusList) {
        binding.tablayout.removeAllTabs();
        binding.tablayout.clearOnTabSelectedListeners();
        for (int i = 0; i < responseAllBusList.size(); i++) {
            setTabLayout(responseAllBusList.get(i));
            if (choiceDate == i) {
                busList = responseAllBusList.get(i).getBues();
            }
        }
        binding.tablayout.getTabAt(choiceDate).select();
        setTabLayoutListener();
    }

    private void setTabLayout(BusResponse busResponse) {
        binding.tablayout.addTab(binding.tablayout.newTab().setText(busResponse.getDate().split("-")[1] + "월 " + busResponse.getDate().split("-")[2] + "일"));
    }

    private void setMyBusList(List<Bus> responseMyBusList) {
        if (responseMyBusList.size() == 0) {
            Snackbar.make(binding.rootLayout, "신청된 버스가 없습니다.", Snackbar.LENGTH_SHORT).show();
        } else {
            busMyApply = responseMyBusList;
        }
    }

    private void setRecyclerview() {
        busAdapter.setContext(this);
        binding.busRecyclerview.setAdapter(busAdapter);

        binding.busRecyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        binding.busRecyclerview.smoothScrollToPosition(0);
    }

    private void setTabLayoutListener() {
        binding.tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                choiceDate = tab.getPosition();
                busViewModel.getCurrentBus();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void initData() {
        busViewModel.getCurrentBus();
    }

    private void initViewModel() {
        busViewModel = ViewModelProviders.of(this).get(BusViewModel.class);
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
