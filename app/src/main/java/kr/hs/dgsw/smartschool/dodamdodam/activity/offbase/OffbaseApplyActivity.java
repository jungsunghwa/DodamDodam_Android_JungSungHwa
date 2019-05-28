package kr.hs.dgsw.smartschool.dodamdodam.activity.offbase;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.activity.BaseActivity;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.OffbaseApplyActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.OffbaseRequest;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.OffbaseViewModel;

public class OffbaseApplyActivity extends BaseActivity<OffbaseApplyActivityBinding> {

    public static final String EXTRA_OFFBASE_TYPE = "offbase_type";

    public static final int TYPE_LEAVE = 0;
    public static final int TYPE_PASS = 1;

    private static final String TAG = "OffbaseApplyActivity";

    private OffbaseViewModel viewModel;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    private SparseArray<TextWatcher> watcherSparseArray;

    @Override
    protected int layoutId() {
        return R.layout.offbase_apply_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new OffbaseViewModel(this);

        viewModel.getMessage().observe(this, msg -> {
            setResult(RESULT_OK);
            finish();
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        int type;
        if ((type = intent.getIntExtra(EXTRA_OFFBASE_TYPE, -1)) != -1) {
            switch (type) {
                case TYPE_LEAVE:
                    setTitle(R.string.title_offbase_apply_leave);

                    break;
                case TYPE_PASS:
                    setTitle(R.string.title_offbase_apply_pass);
                    binding.layoutDateEnd.setVisibility(View.GONE);
                    binding.textDate.setText(R.string.text_offbase_pass_date);
                    binding.textTime.setText(R.string.text_offbase_pass_time);
                    break;
            }
        }

        binding.btnApply.setOnClickListener(v -> {
            CharSequence date, dateEnd, timeHour, timeHourEnd, timeMinute, timeMinuteEnd;

            date = binding.inputDate.getText();
            timeHour = binding.inputTimeHour.getText();
            timeMinute = binding.inputTimeMinute.getText();

            dateEnd = binding.inputDateEnd.getText();
            timeHourEnd = binding.inputTimeHourEnd.getText();
            timeMinuteEnd = binding.inputTimeMinuteEnd.getText();

            if (type == TYPE_LEAVE) {
                if (theyEmptyToError(binding.inputLayoutDate, binding.inputLayoutTimeHour, binding.inputLayoutTimeMinute, binding.inputLayoutDateEnd, binding.inputLayoutTimeHourEnd, binding.inputLayoutTimeMinuteEnd))
                    return;
            } else if (type == TYPE_PASS) {
                if (theyEmptyToError(binding.inputLayoutDate, binding.inputLayoutTimeHour, binding.inputLayoutTimeMinute, binding.inputLayoutTimeHourEnd, binding.inputLayoutTimeMinuteEnd))
                    return;
            }

            try {
                Date startDate = format.parse(String.format(Locale.getDefault(), "%s %02d:%02d", date, Integer.parseInt(timeHour.toString()), Integer.parseInt(timeMinute.toString())));

                if (type == TYPE_LEAVE)
                    viewModel.postLeave(
                            new OffbaseRequest(
                                    startDate,
                                    format.parse(String.format(Locale.getDefault(), "%s %02d:%02d", dateEnd, Integer.parseInt(timeHourEnd.toString()), Integer.parseInt(timeMinuteEnd.toString()))),
                                    binding.inputReason.getText().toString()));
                else if (type == TYPE_PASS)
                    viewModel.postPass(
                            new OffbaseRequest(
                                    startDate,
                                    format.parse(String.format(Locale.getDefault(), "%s %02d:%02d", date, Integer.parseInt(timeHourEnd.toString()), Integer.parseInt(timeMinuteEnd.toString()))),
                                    binding.inputReason.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    private boolean theyEmptyToError(TextInputLayout... layouts) {
        boolean result = false;
        for (TextInputLayout layout : layouts) {
            EditText editText = layout.getEditText();
            if (TextUtils.isEmpty(editText.getText())) {
                result = true;
                layout.setError("비울 수 없습니다");
                if (watcherSparseArray.get(editText.getId()) != null) {
                    TextWatcher watcher = new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (layout.isErrorEnabled() && !TextUtils.isEmpty(s)) {
                                layout.setError(null);
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    };
                    editText.addTextChangedListener(watcher);
                    watcherSparseArray.put(editText.getId(), watcher);
                }
            }
        }
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
