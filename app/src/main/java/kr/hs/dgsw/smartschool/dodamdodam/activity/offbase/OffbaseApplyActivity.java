package kr.hs.dgsw.smartschool.dodamdodam.activity.offbase;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.annimon.stream.Optional;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Leave;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.OffbaseItem;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Pass;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.activity.BaseActivity;
import kr.hs.dgsw.smartschool.dodamdodam.activity.MainActivity;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.OffbaseApplyActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.OffbaseRequest;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.OffbaseViewModel;

public class OffbaseApplyActivity extends BaseActivity<OffbaseApplyActivityBinding> {

    public static final String EXTRA_OFFBASE_TYPE = "offbase_type";
    public static final String EXTRA_OFFBASE = "offbase";

    public static final int TYPE_LEAVE = 0;
    public static final int TYPE_PASS = 1;

    private OffbaseViewModel viewModel;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    private SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private SimpleDateFormat formatTimeHour = new SimpleDateFormat("HH", Locale.getDefault());
    private SimpleDateFormat formatTimeMinute = new SimpleDateFormat("mm", Locale.getDefault());
    private int type;
    private SparseArray<TextWatcher> watcherSparseArray;

    @Override
    protected int layoutId() {
        return R.layout.offbase_apply_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutNoLimits(false);

        viewModel = ViewModelProviders.of(this).get(OffbaseViewModel.class);

        viewModel.getMessage().observe(this, msg -> {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            startActivitiesWithFinish(OffbaseActivity.class);
        });

        viewModel.getError().observe(this, error -> Toast.makeText(this, R.string.text_offbase_error_message, Toast.LENGTH_SHORT).show());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Calendar calendar = Calendar.getInstance();
        Intent intent = getIntent();
        OffbaseItem item = intent.getParcelableExtra(EXTRA_OFFBASE);
        type = intent.getIntExtra(EXTRA_OFFBASE_TYPE, -1);
        if (type != -1 || item != null) {
            if (type == TYPE_LEAVE || item instanceof Leave) {
                type = TYPE_LEAVE;
                setTitle(R.string.title_offbase_apply_leave);
                if (item != null)
                    binding.inputDateEnd.setText(formatDate.format(item.getEndTime()));
                else
                    binding.inputDateEnd.setText(String.format(Locale.getDefault(), "%04d-%02d-%02d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH) + 1));

            } else if (type == TYPE_PASS || item instanceof Pass) {
                type = TYPE_PASS;
                setTitle(R.string.title_offbase_apply_pass);
                binding.layoutDateEnd.setVisibility(View.GONE);
                binding.textDate.setText(R.string.text_offbase_pass_date);
                binding.textTime.setText(R.string.text_offbase_pass_time);
            }

            if (item != null) {
                binding.inputTimeHour.setText(formatTimeHour.format(item.getStartTime()));
                binding.inputTimeMinute.setText(formatTimeMinute.format(item.getStartTime()));
                binding.inputTimeHourEnd.setText(formatTimeHour.format(item.getEndTime()));
                binding.inputTimeMinuteEnd.setText(formatTimeMinute.format(item.getEndTime()));
                binding.inputReason.setText(item.getReason());
                binding.btnApply.setText("수정");
            }
        }

        binding.inputDate.setText(Optional
                .ofNullable(item)
                .map(offbaseItem -> formatDate.format(offbaseItem.getStartTime()))
                .orElse(String.format(Locale.getDefault(), "%04d-%02d-%02d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH))));
        binding.inputDate.setOnClickListener(v -> showDatePicker((EditText) v));
        binding.inputDateEnd.setOnClickListener(v -> showDatePicker((EditText) v));
        binding.inputTimeHour.setOnClickListener(v -> showTimePicker((EditText) v, binding.inputTimeMinute));
        binding.inputTimeHourEnd.setOnClickListener(v -> showTimePicker((EditText) v, binding.inputTimeMinuteEnd));
        binding.inputTimeMinute.setOnClickListener(v -> showTimePicker(binding.inputTimeHour, (EditText) v));
        binding.inputTimeMinuteEnd.setOnClickListener(v -> showTimePicker(binding.inputTimeHourEnd, (EditText) v));

        binding.btnApply.setOnClickListener(v -> {
            CharSequence date, dateEnd, timeHour, timeHourEnd, timeMinute, timeMinuteEnd;

            date = binding.inputDate.getText();
            timeHour = binding.inputTimeHour.getText();
            timeMinute = binding.inputTimeMinute.getText();

            dateEnd = binding.inputDateEnd.getText();
            timeHourEnd = binding.inputTimeHourEnd.getText();
            timeMinuteEnd = binding.inputTimeMinuteEnd.getText();

            if (type == TYPE_LEAVE) {
                if (theyEmptyToError(binding.inputLayoutDate, binding.inputLayoutTimeHour, binding.inputLayoutTimeMinute, binding.inputLayoutDateEnd, binding.inputLayoutTimeHourEnd, binding.inputLayoutTimeMinuteEnd, binding.inputLayoutReason))
                    return;
            } else if (type == TYPE_PASS) {
                if (theyEmptyToError(binding.inputLayoutDate, binding.inputLayoutTimeHour, binding.inputLayoutTimeMinute, binding.inputLayoutTimeHourEnd, binding.inputLayoutTimeMinuteEnd, binding.inputLayoutReason))
                    return;
            }

            try {
                Date startDate = format.parse(String.format(Locale.getDefault(), "%s %02d:%02d", date, Integer.parseInt(timeHour.toString()), Integer.parseInt(timeMinute.toString())));

                if (startDate.before(new Date())) {
                    Toast.makeText(this, R.string.text_offbase_check_message, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (item == null) {
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
                } else {
                    if (type == TYPE_LEAVE)
                        viewModel.updateLeave(
                                item.getIdx(),
                                new OffbaseRequest(
                                        startDate,
                                        format.parse(String.format(Locale.getDefault(), "%s %02d:%02d", dateEnd, Integer.parseInt(timeHourEnd.toString()), Integer.parseInt(timeMinuteEnd.toString()))),
                                        binding.inputReason.getText().toString()));
                    else if (type == TYPE_PASS)
                        viewModel.updatePass(
                                item.getIdx(),
                                new OffbaseRequest(
                                        startDate,
                                        format.parse(String.format(Locale.getDefault(), "%s %02d:%02d", date, Integer.parseInt(timeHourEnd.toString()), Integer.parseInt(timeMinuteEnd.toString()))),
                                        binding.inputReason.getText().toString()));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    private void showDatePicker(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog pickerDialog = new DatePickerDialog(this, 0, (view, year, month, dayOfMonth) -> {
            editText.setText(String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        pickerDialog.getDatePicker().setMinDate(new Date().getTime());
        pickerDialog.show();
    }

    private void showTimePicker(EditText editHour, EditText editMinute) {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog pickerDialog = new TimePickerDialog(this, 0, (view, hourOfDay, minute) -> {
            editHour.setText(String.format(Locale.getDefault(), "%d", hourOfDay));
            editMinute.setText(String.format(Locale.getDefault(), "%02d", minute));
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        pickerDialog.show();
    }

    private boolean theyEmptyToError(TextInputLayout... layouts) {
        boolean result = false;
        for (TextInputLayout layout : layouts) {
            EditText editText = layout.getEditText();
            if (TextUtils.isEmpty(editText.getText())) {
                result = true;
                layout.setError("비울 수 없습니다");
//                if (watcherSparseArray.get(editText.getId()) != null) {
//                    TextWatcher watcher = new TextWatcher() {
//                        @Override
//                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                        }
//
//                        @Override
//                        public void onTextChanged(CharSequence s, int start, int before, int count) {
//                            if (layout.isErrorEnabled() && !TextUtils.isEmpty(s)) {
//                                layout.setError(null);
//                            }
//                        }
//
//                        @Override
//                        public void afterTextChanged(Editable s) {
//
//                        }
//                    };
//                    editText.addTextChangedListener(watcher);
//                    watcherSparseArray.put(editText.getId(), watcher);
//                }
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
