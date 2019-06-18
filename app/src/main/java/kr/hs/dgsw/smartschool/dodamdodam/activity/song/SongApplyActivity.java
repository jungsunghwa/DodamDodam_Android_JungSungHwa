package kr.hs.dgsw.smartschool.dodamdodam.activity.song;

import android.Manifest;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.youtube.YouTubeScopes;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kr.hs.dgsw.smartschool.dodamdodam.Model.song.YoutubeData;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.activity.BaseActivity;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.SongApplyActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.SongRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.task.OnTaskListener;
import kr.hs.dgsw.smartschool.dodamdodam.network.task.YoutubeMusicSearchTask;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.SongViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.SoftKeyboardManager;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.OnItemSelectedListener;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.SongSearchAdapter;

public class SongApplyActivity extends BaseActivity<SongApplyActivityBinding> implements OnTaskListener<List<YoutubeData>>, OnItemSelectedListener<YoutubeData> {

    private static final int REQUEST_ACCOUNT_PICKER = 0;
    private static final int REQUEST_AUTHORIZATION = 1;
    private static final int REQUEST_PERMISSION_GET_ACCOUNTS = 2;

    private static final String BUNDLE_KEY_RESULTS = "results";
    private static final String PREF_ACCOUNT_NAME = "account";

    private SongViewModel viewModel;
    private SoftKeyboardManager keyboardManager;
    private List<YoutubeData> results;

    private SongSearchAdapter adapter;
    private GoogleAccountCredential credential;
    private SharedPreferences preferences;

    private CircularProgressDrawable progressDrawable;

    @Override
    protected int layoutId() {
        return R.layout.song_apply_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keyboardManager = new SoftKeyboardManager(this);

        viewModel = new SongViewModel(this);
        viewModel.getSuccessMessage().observe(this, message -> {
            hideProgress();
            setResult(RESULT_OK, new Intent().putExtra(SongListActivity.REQ_SONG_APPLY_RESULT_MESSAGE, message));
            finish();
        });
        viewModel.getSelectedYoutubeData().observe(this, youtubeData -> {
            if (youtubeData.getVideoId() == null)
                binding.songApplyFab.hide();
            else
                binding.songApplyFab.show();
            binding.songApplyFab.setTag(youtubeData);
        });

        progressDrawable = new CircularProgressDrawable(this);
        progressDrawable.setStyle(CircularProgressDrawable.LARGE);
        progressDrawable.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TooltipCompat.setTooltipText(binding.searchButton, getString(R.string.text_song_search));
        binding.searchList.setAdapter(adapter = new SongSearchAdapter(this, this));
        if (savedInstanceState != null) {
            List<YoutubeData> list = savedInstanceState.getParcelableArrayList(BUNDLE_KEY_RESULTS);
            results = list;
            adapter.setList(list);
        }

        binding.searchText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search();
                return true;
            }
            return false;
        });
        binding.searchTextLayout.setEndIconDrawable(progressDrawable);
        binding.searchButton.setOnClickListener(v -> search());
        binding.songApplyFab.setOnClickListener(v -> {
            YoutubeData data = (YoutubeData) v.getTag();
            if (data != null) {
                showProgress();
                viewModel.apply(new SongRequest(data.getVideoUrl()));
            }
        });
        binding.songApplyFab.hide();

        preferences = getPreferences(Context.MODE_PRIVATE);
        credential = GoogleAccountCredential.usingOAuth2(
                this, Collections.singletonList(YouTubeScopes.YOUTUBE_READONLY))
                .setBackOff(new ExponentialBackOff());

        checkAccount();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        adapter.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(BUNDLE_KEY_RESULTS, (ArrayList<? extends Parcelable>) results);
        adapter.onSaveInstanceState(outState);
    }

    private void showProgress() {
        progressDrawable.start();
    }

    private void hideProgress() {
        progressDrawable.stop();
    }

    private void search() {
        if (!TextUtils.isEmpty(binding.searchText.getText())) {
            getResultsFromApi(binding.searchText.getText().toString());
            keyboardManager.closeSoftKeyboard();
        }
    }

    private void getResultsFromApi(String query) {
        if (credential.getSelectedAccountName() != null) {
            adapter.setSearch(true);
            showProgress();
            new YoutubeMusicSearchTask(this, credential, this).execute(query);
        }
    }

    public void checkAccount() {
        if (credential.getSelectedAccountName() == null) {
            chooseAccount();
        }
    }

    private void chooseAccount() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {
            String accountName = preferences.getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {
                credential.setSelectedAccountName(accountName);

                //설정해도 null 인 경우 계정 재등록 (공장초기화 후 앱 데이터 자동 복원 되었을 경우)
                if (credential.getSelectedAccountName() == null) {
                    preferences.edit().remove(PREF_ACCOUNT_NAME).apply();
                    chooseAccount();
                }
            } else {
                startActivityForResult(credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.GET_ACCOUNTS}, REQUEST_PERMISSION_GET_ACCOUNTS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean isOK = resultCode == RESULT_OK;

        switch (requestCode) {
            case REQUEST_ACCOUNT_PICKER:
                if (isOK && data != null && data.getExtras() != null) {
                    String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        credential.setSelectedAccountName(accountName);
                    }
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (isOK)
                    getResultsFromApi(binding.searchText.getText().toString());
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_GET_ACCOUNTS) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {
                checkAccount();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }

    @Override
    public void onFinished(List<YoutubeData> data) {
        results = data;
        adapter.setList(data);
        adapter.notifyDataSetChanged();
        hideProgress();
    }

    @Override
    public void onCancelled() {
        hideProgress();
    }

    @Override
    public <E extends Exception> void onFailed(E exception) {
        if (exception instanceof UserRecoverableAuthIOException) {
            startActivityForResult(((UserRecoverableAuthIOException) exception).getIntent(), REQUEST_AUTHORIZATION);
        } else if (exception instanceof GoogleJsonResponseException) {
            if (((GoogleJsonResponseException) exception).getDetails().getCode() == HttpURLConnection.HTTP_FORBIDDEN) {
                adapter.setHasQuotaError(true);
            }
        } else {
            exception.printStackTrace();
        }

        hideProgress();
    }

    @Override
    public void onItemSelected(YoutubeData youtubeData, boolean selected) {
        viewModel.select(youtubeData, selected);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        keyboardManager.unregisterSoftKeyboardCallback();
    }
}
