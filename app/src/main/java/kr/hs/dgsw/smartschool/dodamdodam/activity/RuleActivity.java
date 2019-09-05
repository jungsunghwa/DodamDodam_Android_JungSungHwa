package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import kr.hs.dgsw.smartschool.dodamdodam.Model.rule.Rule;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.RuleActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.RuleViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.RuleAdapter;

public class RuleActivity extends BaseActivity<RuleActivityBinding> {

    RuleViewModel ruleViewModel;
    RuleAdapter ruleAdapter;
    List<Rule> ruleList = new ArrayList<>();
    int index;

    @Override
    protected int layoutId() {
        return R.layout.rule_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(getApplicationContext(), "123s", Toast.LENGTH_SHORT);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initViewModel();
        setRecyclerView();

        if(ruleList == null)
        {
            Toast.makeText(getApplicationContext(), "값이 존재하지 않습니다.", Toast.LENGTH_SHORT);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "123", Toast.LENGTH_SHORT);
        }

        ruleViewModel.getData().observe(this, rule -> {
            for(int i = 0; i < rule.getRules().size(); i++)
            {
                ruleList.add(index, rule.getRules().get(i));
                ruleAdapter.notifyItemInserted(index);
                index++;
            }

            if(ruleList == null)
            {
                Toast.makeText(getApplicationContext(), "값이 존재하지 않습니다.", Toast.LENGTH_SHORT);
            }
        });
    }

    private void setRecyclerView() {
        this.ruleAdapter = new RuleAdapter(this, ruleList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.ruleList.setAdapter(ruleAdapter);
        binding.ruleList.setLayoutManager(linearLayoutManager);
    }

    private void initViewModel() {
        ruleViewModel = ViewModelProviders.of(this).get(RuleViewModel.class);

        ruleViewModel.getRule();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }

}
