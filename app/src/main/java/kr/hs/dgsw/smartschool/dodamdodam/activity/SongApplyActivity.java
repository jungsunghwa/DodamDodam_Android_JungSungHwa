package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.Manifest;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.youtube.YouTubeScopes;

import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.List;

import kr.hs.dgsw.smartschool.dodamdodam.Model.song.YoutubeData;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.SongApplyActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.network.task.OnTaskListener;
import kr.hs.dgsw.smartschool.dodamdodam.network.task.YoutubeMusicSearchTask;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.OnItemClickListener;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.SongSearchAdapter;

public class SongApplyActivity extends BaseActivity<SongApplyActivityBinding> implements OnTaskListener<List<YoutubeData>>, OnItemClickListener<YoutubeData> {

    private static final int REQUEST_ACCOUNT_PICKER = 0;
    private static final int REQUEST_AUTHORIZATION = 1;
    private static final int REQUEST_PERMISSION_GET_ACCOUNTS = 2;

    private static final String TAG = "SongApplyActivity";

    private SongSearchAdapter adapter;
    private GoogleAccountCredential credential;
    private SharedPreferences preferences;
    private String PREF_ACCOUNT_NAME = "account";

    @Override
    protected int layoutId() {
        return R.layout.song_apply_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TooltipCompat.setTooltipText(binding.searchButton, getString(R.string.search));
        binding.searchList.setAdapter(adapter = new SongSearchAdapter(this, this));
        binding.searchList.setLayoutManager(new LinearLayoutManager(this));
        binding.searchButton.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(binding.searchText.getText()))
                getResultsFromApi(binding.searchText.getText().toString());
        });
        preferences = getPreferences(Context.MODE_PRIVATE);
        credential = GoogleAccountCredential.usingOAuth2(
                this, Collections.singletonList(YouTubeScopes.YOUTUBE_READONLY))
                .setBackOff(new ExponentialBackOff());

        checkAccount();
    }

    private void goneProgress() {
        binding.progressBar.setVisibility(View.GONE);
    }

    private void getResultsFromApi(String query) {
        if (credential.getSelectedAccountName() != null) {
            adapter.setSearch(true);
            binding.progressBar.setVisibility(View.VISIBLE);
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
        adapter.setYoutubeDataList(data);
        adapter.notifyDataSetChanged();
        goneProgress();
    }

    @Override
    public void onCancelled() {
        goneProgress();
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

        goneProgress();
    }

    @Override
    public void onItemClick(YoutubeData youtubeData) {
        Log.d(TAG, "Clicked: " + youtubeData.getVideoId() + " | " + youtubeData.getVideoTitle());
    }
}
