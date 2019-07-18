package kr.hs.dgsw.smartschool.dodamdodam.activity.offbase;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import java.text.SimpleDateFormat;
import java.util.Locale;

import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Leave;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.OffbaseItem;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Pass;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.activity.BaseActivity;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.OffbaseDetailActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.OffbaseViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.ViewUtils;

public class OffbaseDetailActivity extends BaseActivity<OffbaseDetailActivityBinding> {

    public static final String EXTRA_OFFBASE = "offbase";

    private static final int REQ_EDIT = 1000;
    private SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm", Locale.getDefault());

    private OffbaseViewModel viewModel;

    private static final int TYPE_LEAVE = 0;
    private static final int TYPE_PASS = 1;

    @IntDef({TYPE_LEAVE, TYPE_PASS})
    private @interface Type {
    }

    private int id = -1;
    @Type
    private int type;

    @Override
    protected int layoutId() {
        return R.layout.offbase_detail_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ViewUtils.marginTopSystemWindow(binding.toolbar);

        viewModel = ViewModelProviders.of(this).get(OffbaseViewModel.class);

        viewModel.getLeave().observe(this, leave -> {
            binding.textDate.setText(formatDate.format(leave.getStartTime()));
            binding.textReason.setText(leave.getReason());
            binding.textTime.setText(formatTime.format(leave.getStartTime()));
            binding.textDateEnd.setText(formatDate.format(leave.getEndTime()));
            binding.textTimeEnd.setText(formatTime.format(leave.getEndTime()));
        });

        viewModel.getPass().observe(this, pass -> {
            binding.textDate.setText(formatDate.format(pass.getStartTime()));
            binding.textReason.setText(pass.getReason());
            binding.textTime.setText(String.format(Locale.getDefault(), "%s - %s", formatTime.format(pass.getStartTime()), formatTime.format(pass.getEndTime())));
            binding.textDateEnd.setText(null);
            binding.textTimeEnd.setText(null);
        });

        Intent intent = getIntent();
        OffbaseItem item = intent.getParcelableExtra(EXTRA_OFFBASE);

        if (item != null) {
            id = item.getIdx();

            binding.textDate.setText(formatDate.format(item.getStartTime()));
            binding.textReason.setText(item.getReason());

            if (item instanceof Leave) {
                binding.textLabelDate.setText(R.string.text_offbase_leave_date);
                binding.textLabelTime.setText(R.string.text_offbase_leave_time);
                binding.cardDateEnd.setVisibility(View.VISIBLE);
                binding.cardTimeEnd.setVisibility(View.VISIBLE);

                binding.textTime.setText(formatTime.format(item.getStartTime()));
                binding.textDateEnd.setText(formatDate.format(item.getEndTime()));
                binding.textTimeEnd.setText(formatTime.format(item.getEndTime()));
            } else if (item instanceof Pass) {
                binding.textLabelDate.setText(R.string.text_offbase_pass_date);
                binding.textLabelTime.setText(R.string.text_offbase_pass_time);
                binding.cardDateEnd.setVisibility(View.GONE);
                binding.cardTimeEnd.setVisibility(View.GONE);

                binding.textTime.setText(String.format(Locale.getDefault(), "%s - %s", formatTime.format(item.getStartTime()), formatTime.format(item.getEndTime())));
                binding.textDateEnd.setText(null);
                binding.textTimeEnd.setText(null);
            }
        }

        binding.fabOffbaseEdit.setOnClickListener(v -> {
            if (item != null) {
                startActivityForResult(new Intent(this, OffbaseApplyActivity.class).putExtra(OffbaseApplyActivity.EXTRA_OFFBASE, item), REQ_EDIT);
            }
        });
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        binding.collapsingLayout.setTitle(title);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_EDIT && resultCode == RESULT_OK && id != -1) {
            switch (type) {
                case TYPE_LEAVE:
                    viewModel.leaveById(id);
                    break;

                case TYPE_PASS:
                    viewModel.passById(id);
                    break;
            }
        }
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
