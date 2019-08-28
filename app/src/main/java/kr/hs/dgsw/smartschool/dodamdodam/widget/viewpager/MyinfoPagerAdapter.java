package kr.hs.dgsw.smartschool.dodamdodam.widget.viewpager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import kr.hs.dgsw.smartschool.dodamdodam.fragment.BaseFragment;
import kr.hs.dgsw.smartschool.dodamdodam.fragment.MyinfoProfileFragment;
import kr.hs.dgsw.smartschool.dodamdodam.fragment.MyinfoStatusFragment;


public class MyinfoPagerAdapter extends FragmentPagerAdapter {

    private static int PAGE_NUMBER = 2;

    public MyinfoPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {


            switch (i) {
                case 0:
                    return BaseFragment.newInstance(new MyinfoProfileFragment());
                case 1:
                    return BaseFragment.newInstance(new MyinfoStatusFragment());
                default:
                    return null;
            }

    }

    @Override
    public int getCount() {
        return PAGE_NUMBER;
    }
}
