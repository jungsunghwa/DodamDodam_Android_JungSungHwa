package kr.hs.dgsw.smartschool.dodamdodam.activity.offbase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Locale;

import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Leave;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.OffbaseItem;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Pass;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.activity.BaseActivity;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.OffbaseDetailActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.widget.ViewUtils;

public class OffbaseDetailActivity extends BaseActivity<OffbaseDetailActivityBinding> {

    public static final String EXTRA_OFFBASE = "offbase";
    private SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm", Locale.getDefault());

    @Override
    protected int layoutId() {
        return R.layout.offbase_detail_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(binding.toolbar);
        ViewUtils.marginTopSystemWindow(binding.toolbar);

        Intent intent = getIntent();
        OffbaseItem item = intent.getParcelableExtra(EXTRA_OFFBASE);
        if (item != null) {
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
    }


    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        binding.collapsingLayout.setTitle(title);
    }
}
