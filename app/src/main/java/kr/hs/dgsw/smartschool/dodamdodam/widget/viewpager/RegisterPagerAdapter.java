package kr.hs.dgsw.smartschool.dodamdodam.widget.viewpager;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import kr.hs.dgsw.smartschool.dodamdodam.fragment.RegisterAccountFragment;
import kr.hs.dgsw.smartschool.dodamdodam.fragment.RegisterProfileFragement;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.RegisterViewModel;

public class RegisterPagerAdapter extends FragmentPagerAdapter {

    private final int PAGE_COUNT = 2;

    public final RegisterAccountFragment registerAccountFragment = new RegisterAccountFragment();
    public final RegisterProfileFragement registerProfileFragement = new RegisterProfileFragement();

    public RegisterPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return registerAccountFragment;
            case 1:
                return registerProfileFragement;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
