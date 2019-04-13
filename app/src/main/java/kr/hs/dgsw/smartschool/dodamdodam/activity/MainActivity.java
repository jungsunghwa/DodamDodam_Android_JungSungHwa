package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import kr.hs.dgsw.b1nd.bottomsheet.B1ndBottomSheetDialogFragment;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.MainActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.LoginViewModel;

public class MainActivity extends BaseActivity<MainActivityBinding> {

    @Override
    protected int layoutId() {
        return R.layout.main_activity;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.appbarLayout.toolbar.setTitle(getTitle());
        binding.appbarLayout.getRoot().setStateListAnimator(null);
        setSupportActionBar(binding.bottomAppbar);
        binding.floatingActionButton.setOnClickListener(v -> startActivity(new Intent(this, PointListActivity.class)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            B1ndBottomSheetDialogFragment bottomSheetDialogFragment = new B1ndBottomSheetDialogFragment()
                    .setProfileImageResource(android.R.drawable.sym_def_app_icon, getResources())
                    .setSubIconImageResource(android.R.drawable.ic_lock_power_off, getResources())
                    .setName("지오")
                    .setEmail("kimjioh0927@gmail.com")
                    .setTemper("ANDROID");
            bottomSheetDialogFragment.menuInflate(R.menu.menu_main);
            bottomSheetDialogFragment.show(getSupportFragmentManager(), "bottom");
        }

        return false;
    }
}
