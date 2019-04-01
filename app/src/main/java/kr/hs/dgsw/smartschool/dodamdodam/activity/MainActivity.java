package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import kr.hs.dgsw.b1nd.bottomsheet.B1ndBottomSheetDialogFragment;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.MainActivityBinding;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private BottomAppBar bottomAppBar;

    MainActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);

        bottomAppBar = binding.bottomAppbar;
        (binding.appbarLayout.toolbar).setTitle(getTitle());
        binding.appbarLayout.getRoot().setStateListAnimator(null);
        setSupportActionBar(bottomAppBar);
        floatingActionButton = binding.floatingActionButton;
        floatingActionButton.setOnClickListener(v -> startActivity(new Intent(this, PointListActivity.class)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            B1ndBottomSheetDialogFragment bottomSheetDialogFragment = new B1ndBottomSheetDialogFragment();
            bottomSheetDialogFragment.setProfileImageResource(android.R.drawable.sym_def_app_icon, getResources());
            bottomSheetDialogFragment.setSubIconImageResource(android.R.drawable.ic_lock_power_off, getResources());
            bottomSheetDialogFragment.setName("지오");
            bottomSheetDialogFragment.setEmail("kimjioh0927@gmail.com");
            bottomSheetDialogFragment.setTemper("ANDROID");
            bottomSheetDialogFragment.show(getSupportFragmentManager(), "bottom");
        }

        return false;
    }
}
