package kr.hs.dgsw.smartschool.dodamdodam.activity.offbase;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Leave;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Offbase;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.OffbaseItem;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Pass;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.activity.BaseActivity;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.OffbaseActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.OffbaseViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.OffbaseAdapter;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.OnItemClickListener;

/**
 * TODO Scroll to Hide FAB
 */
public class OffbaseActivity extends BaseActivity<OffbaseActivityBinding> implements SwipeRefreshLayout.OnRefreshListener, OnItemClickListener<OffbaseItem> {

    private static final int REQ_APPLY = 1000;

    private OffbaseViewModel viewModel;

    private OffbaseAdapter adapter;

    @Override
    protected int layoutId() {
        return R.layout.offbase_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutNoLimits(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        viewModel = ViewModelProviders.of(this).get(OffbaseViewModel.class);

        viewModel.getData().observe(this, offbase -> {
            List<OffbaseItem> offbaseItems = new ArrayList<>();

            for (OffbaseItem offbaseItem: offbase.getAll()) {
                if (offbaseItem.getEndTime().after(new Date())) {
                    offbaseItems.add(offbaseItem);
                }
            }
            adapter.setOffbaseItems(offbaseItems);
            if (!adapter.isEmpty())
                binding.listOffbase.smoothScrollToPosition(adapter.getItemCount() - 1);
        });

        viewModel.getMessage().observe(this, message -> {
            Snackbar.make(binding.rootLayout, message, Snackbar.LENGTH_SHORT).show();
        });

        viewModel.getErrorMessage().observe(this, message -> {
            Snackbar.make(binding.rootLayout, message, Snackbar.LENGTH_SHORT).show();
        });

        viewModel.getLoading().observe(this, loading -> new Handler().postDelayed(() -> {
            try {
                binding.swipeRefreshLayout.setRefreshing(loading);
            }
            catch (NullPointerException e) {
            }
        }, 500));

        adapter = new OffbaseAdapter(viewModel, this);

        viewModel.list();

        binding.listOffbase.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) binding.fabOffbaseApply.hide();
                else binding.fabOffbaseApply.show();
            }
        });
        binding.listOffbase.setAdapter(adapter);
        binding.fabOffbaseApply.setOnClickListener(v -> {
            ((FloatingActionButton) v).setExpanded(true);
            getWindow().setNavigationBarColor(Color.RED);
        });
        binding.scrim.setOnClickListener(v -> binding.fabOffbaseApply.setExpanded(false));
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorSecondary);
        binding.swipeRefreshLayout.setOnRefreshListener(this);
        binding.fabMenu.fabMenuLeave.setOnClickListener(v -> {
            startActivityForResult(new Intent(this, OffbaseApplyActivity.class).putExtra(OffbaseApplyActivity.EXTRA_OFFBASE_TYPE, OffbaseApplyActivity.TYPE_LEAVE).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), REQ_APPLY);
            finish();
        });
        binding.fabMenu.fabMenuPass.setOnClickListener(v -> {
            startActivityForResult(new Intent(this, OffbaseApplyActivity.class).putExtra(OffbaseApplyActivity.EXTRA_OFFBASE_TYPE, OffbaseApplyActivity.TYPE_PASS).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), REQ_APPLY);
            finish();
        });
    }

    @Override
    public void onRefresh() {
        viewModel.list();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_APPLY) {
            if (resultCode == RESULT_OK)
                viewModel.list();
            binding.fabOffbaseApply.setExpanded(false);
        }
    }

    @Override
    public void onBackPressed() {
        if (binding.fabOffbaseApply.isExpanded())
            binding.fabOffbaseApply.setExpanded(false);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(OffbaseItem offbaseItem) {
        startActivity(new Intent(this, OffbaseDetailActivity.class).putExtra(OffbaseDetailActivity.EXTRA_OFFBASE, offbaseItem).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        finish();
    }
}
