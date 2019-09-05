package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Bus;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.BusResponse;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.BusApplyActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.PostBusRequest;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.BusViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.BusAdapter;

public class BusApplyActivity extends BaseActivity<BusApplyActivityBinding> {

    BusViewModel busViewModel;
    BusAdapter busAdapter = new BusAdapter(null, null, null);
    List<BusResponse> busList = new ArrayList<>();

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
        
        busViewModel.getCurrentBus();
        busViewModel.getMyBusApply();
    }

    private void observableViewModel() {
        busViewModel.getResponseAllBusList().observe(this, responseAllBusList -> {
            busList = responseAllBusList;
            busAdapter.setBusList(responseAllBusList);
            binding.busRecyclerview.smoothScrollToPosition(0);
        });

        busViewModel.getResponseMyBusList().observe(this, responseMyBusList -> {
            if (responseMyBusList.size() == 0) {
                Snackbar.make(binding.rootLayout, "신청된 버스가 없습니다.", Snackbar.LENGTH_SHORT).show();
            } else {
                busAdapter.setBusMyApply(responseMyBusList);
            }
            setRecyclerview();
        });

        busViewModel.getErrorMessage().observe(this, message -> {
            Log.e("err", message);
            Snackbar.make(binding.rootLayout, message, Snackbar.LENGTH_SHORT).show();
        });
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

        busAdapter.getPostBus().observe(this, bus_idx -> {
            busViewModel.postBusApply(new PostBusRequest(bus_idx.toString()));
            Toast.makeText(this, "버스 신청에 성공하였습니다.", Toast.LENGTH_SHORT).show();
        });

        busAdapter.getDeleteBus().observe(this, busIdx -> {
            busViewModel.deleteBusApply(busIdx);
            Toast.makeText(this, "버스 삭제에 성공하였습니다.", Toast.LENGTH_SHORT).show();
        });

        busAdapter.getPutBus().observe(this, busRequest -> {
            busViewModel.putBusApply(busRequest);
            Toast.makeText(this, "버스 수정에 성공하였습니다.", Toast.LENGTH_SHORT).show();
        });
    }
}
