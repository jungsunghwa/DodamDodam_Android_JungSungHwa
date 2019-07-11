package kr.hs.dgsw.smartschool.dodamdodam.activity.offbase;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.OffbaseItem;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.activity.BaseActivity;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.OffbaseActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.OffbaseViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.ViewUtils;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.OffbaseAdapter;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.OnItemClickListener;

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
        // 음영처리를 위해 R.id.container 를 사용하지 않음
        ViewUtils.marginBottomSystemWindow(binding.fabOffbaseApply);
        ViewUtils.marginBottomSystemWindow(binding.fabMenu.getRoot());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        viewModel = new OffbaseViewModel(this);

        viewModel.getData().observe(this, offbase -> {
            adapter.setOffbaseItems(offbase);
            if (!adapter.isEmpty())
            binding.listOffbase.smoothScrollToPosition(adapter.getItemCount() - 1);
        });

        viewModel.getMessage().observe(this, message -> {
            //FIXME Margin Bottom
            Snackbar snackbar = Snackbar.make(binding.swipeRefreshLayout, message, Snackbar.LENGTH_SHORT);
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) snackbar.getView().getLayoutParams();
            params.setMargins(100, 0, 100, ((ViewGroup.MarginLayoutParams) binding.rootLayout.getLayoutParams()).bottomMargin);
            snackbar.getView().setLayoutParams(params);
            snackbar.show();
        });

        viewModel.getLoading().observe(this, loading -> new Handler().postDelayed(() -> binding.swipeRefreshLayout.setRefreshing(loading), 500));

        adapter = new OffbaseAdapter(viewModel, this);

        viewModel.list();

        binding.listOffbase.setAdapter(adapter);
        binding.fabOffbaseApply.setOnClickListener(v -> ((FloatingActionButton) v).setExpanded(true));
        binding.scrim.setOnClickListener(v -> binding.fabOffbaseApply.setExpanded(false));
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorSecondary);
        binding.swipeRefreshLayout.setOnRefreshListener(this);
        binding.fabMenu.fabMenuLeave.setOnClickListener(v -> {
            startActivityForResult(new Intent(this, OffbaseApplyActivity.class).putExtra(OffbaseApplyActivity.EXTRA_OFFBASE_TYPE, OffbaseApplyActivity.TYPE_LEAVE), REQ_APPLY);
        });
        binding.fabMenu.fabMenuPass.setOnClickListener(v -> {
            startActivityForResult(new Intent(this, OffbaseApplyActivity.class).putExtra(OffbaseApplyActivity.EXTRA_OFFBASE_TYPE, OffbaseApplyActivity.TYPE_PASS), REQ_APPLY);
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
        startActivity(new Intent(this, OffbaseDetailActivity.class).putExtra(OffbaseDetailActivity.EXTRA_OFFBASE, offbaseItem));
    }
}
