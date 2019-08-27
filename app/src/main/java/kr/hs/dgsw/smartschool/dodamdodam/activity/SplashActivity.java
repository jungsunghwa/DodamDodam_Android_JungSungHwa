package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProviders;

import com.annimon.stream.Optional;

import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.SplashActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.StudentViewModel;

public class SplashActivity extends BaseActivity<SplashActivityBinding> {

    private static final String TAG = "SplashActivity";

    private static final String SHORTCUT_BUS = "short_bus";

    private StudentViewModel studentViewModel;
    private TokenManager manager;
    private Handler handler = new Handler();

    private Runnable runnableLogin = () -> startActivityWithFinish(LoginActivity.class);
    private Runnable runnableMain = () -> startActivityWithFinish(MainActivity.class);

    @Override
    protected int layoutId() {
        return R.layout.splash_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lightNavMode();
        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        manager = TokenManager.getInstance(this);
        Token token = manager.getToken();
        if (!token.isEmpty()) {
            if (manager.isValidate()) {
                if (token.getRefreshToken() != null) {
                    if (manager.isValidateRefresh())
                        Log.d(TAG, "Refresh Token: " + token.getRefreshToken());
                }

                studentViewModel.getClasses();
                studentViewModel.getStudent();

                String shortcut = getIntent().getStringExtra("shortcut");
                if (shortcut != null) {
                    switch (shortcut) {
                        case SHORTCUT_BUS:
                            startActivitiesWithFinish(MainActivity.class);
                            break;
                    }
                    return;
                }
                handler.postDelayed(runnableMain, 2000);
                return;
            }
        }
        handler.postDelayed(runnableLogin, 2000);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(runnableLogin);
        handler.removeCallbacks(runnableMain);
    }
}
