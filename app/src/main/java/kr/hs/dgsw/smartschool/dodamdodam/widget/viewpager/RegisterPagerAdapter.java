package kr.hs.dgsw.smartschool.dodamdodam.widget.viewpager;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import kr.hs.dgsw.smartschool.dodamdodam.fragment.RegisterAccountFragment;
import kr.hs.dgsw.smartschool.dodamdodam.fragment.RegisterProfileFragement;

public class RegisterPagerAdapter extends FragmentPagerAdapter {

    private final int PAGE_COUNT = 2;

    public RegisterPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        switch (position) {
            case 0:
                fragment = new RegisterAccountFragment();
                break;

            case 1:
                fragment = new RegisterProfileFragement();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
