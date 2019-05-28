package kr.hs.dgsw.smartschool.dodamdodam.widget.viewpager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import kr.hs.dgsw.smartschool.dodamdodam.fragment.PointDemeritFragment;
import kr.hs.dgsw.smartschool.dodamdodam.fragment.PointPrizeFragment;

public class PointPagerAdapter extends FragmentPagerAdapter {

    public PointPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position) {
            case 0:
                title = "상점";
                break;

            case 1:
                title = "벌점";
                break;
        }
        return title;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        switch (position) {
            case 0:
                fragment = new PointPrizeFragment();
                break;

            case 1:
                fragment = new PointDemeritFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
