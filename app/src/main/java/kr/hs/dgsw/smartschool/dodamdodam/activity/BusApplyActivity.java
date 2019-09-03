package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Type;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Types;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.BusApplyActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.BusRequest;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.BusViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.BusAdapter;

public class BusApplyActivity extends BaseActivity<BusApplyActivityBinding> {

    BusViewModel busViewModel;
    BusAdapter busAdapter;
    List<Type> busList = new ArrayList<>();
    Context context;

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
        observableViewModel();

    }

    private void initViewModel() {
        busViewModel = ViewModelProviders.of(this).get(BusViewModel.class);

        setRecyclerview();
        busViewModel.getCurrentBus();
        busViewModel.getMyBusApply();
    }

    private void observableViewModel() {
        busViewModel.getResponseAllTypeList().observe(this, responseTypeList -> {
            busList = responseTypeList;
            busAdapter.setBusList(responseTypeList);
            binding.busRecyclerview.smoothScrollToPosition(0);
            setRecyclerview();
        });

        busViewModel.getResponseTypeList().observe(this, responseTypes -> {
            if (responseTypes.size() == 0) {
                Toast.makeText(this, "신청된 버스가 없습니다.", Toast.LENGTH_SHORT).show();
            } else {
                busAdapter.setBusMyApply(responseTypes);
            }
        });

        busViewModel.getErrorMessage().observe(this, message -> Log.e("err", message));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setRecyclerview() {
        busAdapter = new BusAdapter(busList, busViewModel, this);
        binding.busRecyclerview.setAdapter(busAdapter);

        binding.busRecyclerview.setLayoutManager(new GridLayoutManager(this, 2));
    }
}
